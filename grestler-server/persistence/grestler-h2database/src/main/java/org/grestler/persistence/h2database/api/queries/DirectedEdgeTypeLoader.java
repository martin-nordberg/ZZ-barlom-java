//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.spi.queries.IDirectedEdgeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for loading all edge types into a metamodel repository.
 */
public class DirectedEdgeTypeLoader
    implements IDirectedEdgeTypeLoader {

    /**
     * Constructs a new DAO for edge types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public DirectedEdgeTypeLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllDirectedEdgeTypes( IMetamodelRepositorySpi repository ) {

        Configuration config = new Configuration( H2DatabaseModule.class );

        List<IDirectedEdgeType.Record> etRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> etRecords.add(
                    new IDirectedEdgeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getUuid( "SUPER_TYPE_ID" ),
                        rs.getString( "NAME" ),
                        EAbstractness.fromBoolean( rs.getBoolean( "IS_ABSTRACT" ) ),
                        ECyclicity.fromBoolean( rs.getOptionalBoolean( "IS_ACYCLIC" ) ),
                        EMultiEdgedness.fromBoolean( rs.getOptionalBoolean( "IS_MULTI_EDGE_ALLOWED" ) ),
                        ESelfLooping.fromBoolean( rs.getOptionalBoolean( "IS_SELF_LOOP_ALLOWED" ) ),
                        rs.getOptionalString( "HEAD_ROLE_NAME" ),
                        rs.getUuid( "HEAD_VERTEX_TYPE_ID" ),
                        rs.getOptionalInt( "MAX_HEAD_IN_DEGREE" ),
                        rs.getOptionalInt( "MAX_TAIL_OUT_DEGREE" ),
                        rs.getOptionalInt( "MIN_HEAD_IN_DEGREE" ),
                        rs.getOptionalInt( "MIN_TAIL_OUT_DEGREE" ),
                        rs.getOptionalString( "TAIL_ROLE_NAME" ),
                        rs.getUuid( "TAIL_VERTEX_TYPE_ID" )
                    )
                ), config.readString( "DirectedEdgeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IDirectedEdgeType.Record etRecord : etRecords ) {
            this.findOrCreateDirectedEdgeType( etRecord, etRecords, repository );
        }

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
    private IDirectedEdgeType findOrCreateDirectedEdgeType(
        IDirectedEdgeType.Record record, List<IDirectedEdgeType.Record> records, IMetamodelRepositorySpi repository
    ) {

        // Look for the edge type already in the repository.
        Optional<IDirectedEdgeType> result = repository.findOptionalDirectedEdgeTypeById( record.id );

        // If already registered, use the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package.
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return repository.loadRootDirectedEdgeType( record.id, parentPackage );
        }

        // Find the vertex types.
        IVertexType headVertexType = repository.findOptionalVertexTypeById( record.headVertexTypeId ).get();
        IVertexType tailVertexType = repository.findOptionalVertexTypeById( record.tailVertexTypeId ).get();

        // Find an existing edge super type by UUID.
        Optional<IDirectedEdgeType> superType = repository.findOptionalDirectedEdgeTypeById( record.superTypeId );

        // If supertype not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the supertype.
            for ( IDirectedEdgeType.Record srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateDirectedEdgeType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadDirectedEdgeType(
            record,
            parentPackage,
            superType.get(),
            tailVertexType,
            headVertexType
        );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

}
