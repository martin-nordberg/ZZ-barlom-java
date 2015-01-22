//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.dbutilities.IDataSource;
import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IAttributeDeclLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for loading all attribute declarations into a metamodel repository.
 */
public class AttributeDeclLoader
    implements IAttributeDeclLoader {

    /**
     * Constructs a new DAO for attribute types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public AttributeDeclLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllAttributeDecls( IMetamodelRepositorySpi repository ) {

        this.loadAllVertexAttributeDecls( repository );
        this.loadAllEdgeAttributeDecls( repository );

    }

    /**
     * Finds a UUID attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IEdgeAttributeDecl findOrCreateEdgeAttributeDecl(
        EdgeAttributeDeclRecord record, IMetamodelRepositorySpi repository
    ) {

        // Find the parent package
        Optional<IEdgeType> parentEdgeType = repository.findEdgeTypeById( record.parentEdgeTypeId );
        Optional<IAttributeType> attributeType = repository.findAttributeTypeById( record.attributeTypeId );

        return repository.loadEdgeAttributeDecl(
            record.id, parentEdgeType.get(), record.name, attributeType.get(), record.optionality
        );

    }

    /**
     * Finds a boolean attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IVertexAttributeDecl findOrCreateVertexAttributeDecl(
        VertexAttributeDeclRecord record, IMetamodelRepositorySpi repository
    ) {

        // Find the parent vertex type and the attribute type
        Optional<IVertexType> parentVertexType = repository.findVertexTypeById( record.parentVertexTypeId );
        Optional<IAttributeType> attributeType = repository.findAttributeTypeById( record.attributeTypeId );

        return repository.loadVertexAttributeDecl(
            record.id, parentVertexType.get(), record.name, attributeType.get(), record.optionality
        );

    }

    /**
     * Loads all the UUID attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllEdgeAttributeDecls( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( EdgeAttributeDeclRecord.class, new EdgeAttributeDeclInstantiator() );

        // Perform the raw query.
        List<EdgeAttributeDeclRecord> records = database.findAll(
            EdgeAttributeDeclRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_EDGE_TYPE_ID), NAME, TO_CHAR(ATTRIBUTE_TYPE_ID), IS_REQUIRED FROM GRESTLER_EDGE_ATTRIBUTE_DECL"
        );

        // Copy the results into the repository.
        for ( EdgeAttributeDeclRecord record : records ) {
            AttributeDeclLoader.findOrCreateEdgeAttributeDecl( record, repository );
        }

    }

    /**
     * Loads all the boolean attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllVertexAttributeDecls( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( VertexAttributeDeclRecord.class, new VertexAttributeDeclInstantiator() );

        // Perform the raw query.
        List<VertexAttributeDeclRecord> records = database.findAll(
            VertexAttributeDeclRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_VERTEX_TYPE_ID), NAME, TO_CHAR(ATTRIBUTE_TYPE_ID), IS_REQUIRED FROM GRESTLER_VERTEX_ATTRIBUTE_DECL"
        );

        // Copy the results into the repository.
        for ( VertexAttributeDeclRecord record : records ) {
            AttributeDeclLoader.findOrCreateVertexAttributeDecl( record, repository );
        }

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Custom instantiator for boolean attribute types.
     */
    private static class EdgeAttributeDeclInstantiator
        implements Instantiator<EdgeAttributeDeclRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public EdgeAttributeDeclRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new EdgeAttributeDeclRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                UUID.fromString( (String) fields.getValues().get( 3 ) ),
                (Boolean) fields.getValues().get( 4 ) ? EAttributeOptionality.REQUIRED : EAttributeOptionality.OPTIONAL
            );

        }

    }

    /**
     * Data structure for boolean attribute type records.
     */
    private static class EdgeAttributeDeclRecord {

        EdgeAttributeDeclRecord(
            UUID id, UUID parentEdgeTypeId, String name, UUID attributeTypeId, EAttributeOptionality optionality
        ) {
            this.id = id;
            this.parentEdgeTypeId = parentEdgeTypeId;
            this.name = name;
            this.attributeTypeId = attributeTypeId;
            this.optionality = optionality;
        }

        public final UUID id;

        public final String name;

        public final UUID parentEdgeTypeId;

        private final UUID attributeTypeId;

        private final EAttributeOptionality optionality;

    }

    /**
     * Custom instantiator for UUID attribute types.
     */
    private static class VertexAttributeDeclInstantiator
        implements Instantiator<VertexAttributeDeclRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public VertexAttributeDeclRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new VertexAttributeDeclRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                UUID.fromString( (String) fields.getValues().get( 3 ) ),
                (Boolean) fields.getValues().get( 4 ) ? EAttributeOptionality.REQUIRED : EAttributeOptionality.OPTIONAL
            );

        }

    }

    /**
     * Data structure for UUID attribute type records.
     */
    private static class VertexAttributeDeclRecord {

        VertexAttributeDeclRecord(
            UUID id, UUID parentVertexTypeId, String name, UUID attributeTypeId, EAttributeOptionality optionality
        ) {
            this.id = id;
            this.parentVertexTypeId = parentVertexTypeId;
            this.name = name;
            this.attributeTypeId = attributeTypeId;
            this.optionality = optionality;
        }

        public final UUID id;

        public final String name;

        public final UUID parentVertexTypeId;

        private final UUID attributeTypeId;

        private final EAttributeOptionality optionality;

    }

}
