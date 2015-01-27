//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.dbutilities.api.IResultSet;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfEdgedness;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;
import org.grestler.utilities.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Service for loading all edge types into a metamodel repository.
 */
public class EdgeTypeLoader
    implements IEdgeTypeLoader {

    /**
     * Constructs a new DAO for edge types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public EdgeTypeLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllEdgeTypes( IMetamodelRepositorySpi repository ) {

        this.loadAllDirectedEdgeTypes( repository );

    }

    /**
     * Finds a directed edge type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the edge type.
     * @param records    the attributes of all edge types.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created edge type.
     */
    private IEdgeType findOrCreateDirectedEdgeType(
        DirectedEdgeTypeRecord record, List<DirectedEdgeTypeRecord> records, IMetamodelRepositorySpi repository
    ) {

        // Look for the edge type already in the repository.
        Optional<IEdgeType> result = repository.findEdgeTypeById( record.id );

        // If already registered, use the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package.
        IPackage parentPackage = repository.findPackageById( record.parentPackageId ).get();

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return repository.loadRootEdgeType( record.id, parentPackage );
        }

        // Find the vertex types.
        IVertexType headVertexType = repository.findVertexTypeById( record.headVertexTypeId ).get();
        IVertexType tailVertexType = repository.findVertexTypeById( record.tailVertexTypeId ).get();

        // Find an existing edge super type by UUID.
        Optional<IEdgeType> superType = repository.findEdgeTypeById( record.superTypeId );

        // If supertype not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the supertype.
            for ( DirectedEdgeTypeRecord srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateDirectedEdgeType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadDirectedEdgeType(
            record.id,
            parentPackage,
            record.name,
            superType.get(),
            record.abstractness,
            record.cyclicity,
            record.multiEdgedness,
            record.selfEdgedness,
            tailVertexType,
            headVertexType,
            record.tailRoleName,
            record.headRoleName,
            record.minTailOutDegree,
            record.maxTailOutDegree,
            record.minHeadInDegree,
            record.maxHeadInDegree
        );

    }

    private void loadAllDirectedEdgeTypes( IMetamodelRepositorySpi repository ) {

        Configuration config = new Configuration( H2DatabaseModule.class );

        List<DirectedEdgeTypeRecord> etRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> etRecords.add( new DirectedEdgeTypeRecord( rs ) ), config.readString( "DirectedEdgeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( DirectedEdgeTypeRecord etRecord : etRecords ) {
            this.findOrCreateDirectedEdgeType( etRecord, etRecords, repository );
        }

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for directed edge type records.
     */
    private static class DirectedEdgeTypeRecord {

        DirectedEdgeTypeRecord( IResultSet resultSet ) {
            this.id = resultSet.getUuid( "ID" );
            this.parentPackageId = resultSet.getUuid( "PARENT_PACKAGE_ID" );
            this.name = resultSet.getString( "NAME" );
            this.superTypeId = resultSet.getUuid( "SUPER_TYPE_ID" );
            this.abstractness = EAbstractness.fromBoolean( resultSet.getBoolean( "IS_ABSTRACT" ) );
            this.cyclicity = ECyclicity.fromBoolean( resultSet.getOptionalBoolean( "IS_ACYCLIC" ) );
            this.multiEdgedness = EMultiEdgedness.fromBoolean( resultSet.getOptionalBoolean( "IS_MULTI_EDGE_ALLOWED" ) );
            this.selfEdgedness = ESelfEdgedness.fromBoolean( resultSet.getOptionalBoolean( "IS_SELF_EDGE_ALLOWED" ) );
            this.tailVertexTypeId = resultSet.getUuid( "TAIL_VERTEX_TYPE_ID" );
            this.headVertexTypeId = resultSet.getUuid( "HEAD_VERTEX_TYPE_ID" );
            this.tailRoleName = resultSet.getOptionalString( "TAIL_ROLE_NAME" );
            this.headRoleName = resultSet.getOptionalString( "HEAD_ROLE_NAME" );
            this.minTailOutDegree = resultSet.getOptionalInt( "MIN_TAIL_OUT_DEGREE" );
            this.maxTailOutDegree = resultSet.getOptionalInt( "MAX_TAIL_OUT_DEGREE" );
            this.minHeadInDegree = resultSet.getOptionalInt( "MIN_HEAD_IN_DEGREE" );
            this.maxHeadInDegree = resultSet.getOptionalInt( "MAX_HEAD_IN_DEGREE" );
        }

        public final EAbstractness abstractness;

        public final ECyclicity cyclicity;

        public final Optional<String> headRoleName;

        public final UUID headVertexTypeId;

        public final UUID id;

        public final OptionalInt maxHeadInDegree;

        public final OptionalInt maxTailOutDegree;

        public final OptionalInt minHeadInDegree;

        public final OptionalInt minTailOutDegree;

        public final EMultiEdgedness multiEdgedness;

        public final String name;

        public final UUID parentPackageId;

        public final ESelfEdgedness selfEdgedness;

        public final UUID superTypeId;

        public final Optional<String> tailRoleName;

        public final UUID tailVertexTypeId;
    }

}
