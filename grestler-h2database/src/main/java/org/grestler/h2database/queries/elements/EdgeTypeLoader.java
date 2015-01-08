//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.dbutilities.IDataSource;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;

import java.util.List;
import java.util.Optional;
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

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry().registerInstantiator( EdgeTypeData.class, new EdgeTypeInstantiator() );

        // Perform the raw query.
        List<EdgeTypeData> records = database.findAll(
            EdgeTypeData.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME, TO_CHAR(SUPER_TYPE_ID), TO_CHAR(FROM_VERTEX_TYPE_ID), TO_CHAR(TO_VERTEX_TYPE_ID) FROM GRESTLER_EDGE_TYPE"
        );

        // Copy the results into the repository.
        for ( EdgeTypeData record : records ) {
            this.findOrCreateEdgeType( record, records, repository );
        }

    }

    /**
     * Finds a edge type in the metamodel repository or creates it if not yet there.
     *
     * @param record  the attributes of the edge type.
     * @param records the attributes of all edge types.
     *
     * @return the found or newly created edge type.
     */
    private IEdgeType findOrCreateEdgeType(
        EdgeTypeData record, List<EdgeTypeData> records, IMetamodelRepositorySpi repository
    ) {

        Optional<IEdgeType> result = repository.findEdgeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        Optional<IVertexType> fromVertexType = repository.findVertexTypeById( record.fromVertexTypeId );
        Optional<IVertexType> toVertexType = repository.findVertexTypeById( record.toVertexTypeId );

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return IEdgeType.BASE_EDGE_TYPE;
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        // Find an existing edge super type by UUID.
        Optional<IEdgeType> superType = repository.findEdgeTypeById( record.superTypeId );

        // If supertype not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the supertype.
            for ( EdgeTypeData srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateEdgeType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadEdgeType(
            record.id, parentPackage.get(), record.name, superType.get(), fromVertexType.get(), toVertexType.get()
        );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for edge type records.
     */
    private static class EdgeTypeData {

        EdgeTypeData(
            UUID id, UUID parentPackageId, String name, UUID superTypeId, UUID fromVertexTypeId, UUID toVertexTypeId
        ) {
            this.id = id;
            this.parentPackageId = parentPackageId;
            this.name = name;
            this.superTypeId = superTypeId;
            this.fromVertexTypeId = fromVertexTypeId;
            this.toVertexTypeId = toVertexTypeId;
        }

        final UUID fromVertexTypeId;

        final UUID id;

        final String name;

        final UUID parentPackageId;

        final UUID superTypeId;

        final UUID toVertexTypeId;

    }

    /**
     * Custom instantiator for edge types.
     */
    private static class EdgeTypeInstantiator
        implements Instantiator<EdgeTypeData> {

        /**
         * Instantiates a edgeType either by finding it in the registry or else creating it and adding it to the
         * registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new edgeType.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public EdgeTypeData instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new EdgeTypeData(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                UUID.fromString( (String) fields.getValues().get( 3 ) ),
                UUID.fromString( (String) fields.getValues().get( 4 ) ),
                UUID.fromString( (String) fields.getValues().get( 5 ) )
            );

        }

    }

}
