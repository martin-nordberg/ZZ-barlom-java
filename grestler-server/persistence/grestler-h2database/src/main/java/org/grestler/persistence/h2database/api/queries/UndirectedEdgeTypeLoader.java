//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.domain.metamodel.spi.queries.IUndirectedEdgeTypeLoader;
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
public class UndirectedEdgeTypeLoader
    implements IUndirectedEdgeTypeLoader {

    /**
     * Constructs a new DAO for edge types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public UndirectedEdgeTypeLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllUndirectedEdgeTypes( IMetamodelRepositorySpi repository ) {

        Configuration config = new Configuration( H2DatabaseModule.class );

        List<IUndirectedEdgeType.Record> etRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> etRecords.add(
                    new IUndirectedEdgeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getUuid( "SUPER_TYPE_ID" ),
                        rs.getString( "NAME" ),
                        EAbstractness.fromBoolean( rs.getBoolean( "IS_ABSTRACT" ) ),
                        ECyclicity.fromBoolean( rs.getOptionalBoolean( "IS_ACYCLIC" ) ),
                        EMultiEdgedness.fromBoolean( rs.getOptionalBoolean( "IS_MULTI_EDGE_ALLOWED" ) ),
                        ESelfLooping.fromBoolean( rs.getOptionalBoolean( "IS_SELF_LOOP_ALLOWED" ) ),
                        rs.getOptionalInt( "MIN_DEGREE" ),
                        rs.getOptionalInt( "MAX_DEGREE" ),
                        rs.getUuid( "VERTEX_TYPE_ID" )
                    )
                ), config.readString( "UndirectedEdgeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IUndirectedEdgeType.Record etRecord : etRecords ) {
            this.findOrCreateUndirectedEdgeType( etRecord, etRecords, repository );
        }

    }

    /**
     * Finds an undirected edge type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the edge type.
     * @param records    the attributes of all edge types.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created edge type.
     */
    private IUndirectedEdgeType findOrCreateUndirectedEdgeType(
        IUndirectedEdgeType.Record record, List<IUndirectedEdgeType.Record> records, IMetamodelRepositorySpi repository
    ) {

        // Look for the edge type already in the repository.
        Optional<IUndirectedEdgeType> result = repository.findOptionalUndirectedEdgeTypeById( record.id );

        // If already registered, use the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package.
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return repository.loadRootUndirectedEdgeType( record.id, parentPackage );
        }

        // Find the vertex types.
        IVertexType vertexType = repository.findOptionalVertexTypeById( record.vertexTypeId ).get();

        // Find an existing edge super type by UUID.
        Optional<IUndirectedEdgeType> superType = repository.findOptionalUndirectedEdgeTypeById( record.superTypeId );

        // If super type not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the super type.
            for ( IUndirectedEdgeType.Record srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateUndirectedEdgeType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadUndirectedEdgeType( record, parentPackage, superType.get(), vertexType );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

}
