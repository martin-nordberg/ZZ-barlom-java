//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.h2database.datasource.H2DataSource;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for loading all vertex types into a metamodel repository.
 */
public class VertexTypeLoader
    implements IVertexTypeLoader {

    /**
     * Constructs a new DAO for vertex types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public VertexTypeLoader( H2DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllVertexTypes( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry().registerInstantiator( VertexTypeData.class, new VertexTypeInstantiator() );

        // Perform the raw query.
        List<VertexTypeData> records = database.findAll(
            VertexTypeData.class,
            "SELECT TO_CHAR(ID), NAME, TO_CHAR(SUPER_TYPE_ID) FROM GRESTLER_VERTEX_TYPE"
        );

        // Copy the results into the repository.
        for ( VertexTypeData record : records ) {
            this.findOrCreateVertexType( record, records, repository );
        }

    }

    /**
     * Finds a vertex type in the metamodel repository or creates it if not yet there.
     *
     * @param record  the attributes of the vertex type.
     * @param records the attributes of all vertex types.
     *
     * @return the found or newly created vertex type.
     */
    private IVertexType findOrCreateVertexType(
        VertexTypeData record,
        List<VertexTypeData> records,
        IMetamodelRepositorySpi repository
    ) {

        Optional<IVertexType> result = repository.findVertexTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return repository.loadVertexType( record.id, record.name, Optional.empty() );
        }

        // Find an existing vertex super type by UUID.
        Optional<IVertexType> superType = repository.findVertexTypeById( record.superTypeId );

        // If supertype not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the supertype.
            for ( VertexTypeData srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateVertexType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadVertexType( record.id, record.name, superType );

    }

    /** The data source for queries. */
    private final H2DataSource dataSource;

    /**
     * Data structure for vertex type records.
     */
    private static class VertexTypeData {

        VertexTypeData( UUID id, String name, UUID superTypeId ) {
            this.id = id;
            this.name = name;
            this.superTypeId = superTypeId;
        }

        final UUID id;

        final String name;

        final UUID superTypeId;

    }

    /**
     * Custom instantiator for vertex types.
     */
    private static class VertexTypeInstantiator
        implements Instantiator<VertexTypeData> {

        /**
         * Instantiates a vertexType either by finding it in the registry or else creating it and adding it to the
         * registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new vertexType.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public VertexTypeData instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new VertexTypeData(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                (String) fields.getValues().get( 1 ),
                UUID.fromString( (String) fields.getValues().get( 2 ) )
            );

        }

    }

}
