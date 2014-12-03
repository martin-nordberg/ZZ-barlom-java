//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.dao.elements;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.impl.elements.VertexType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Data access for vertex types.
 */
public class VertexTypeDao {

    public VertexTypeDao( Database database ) {

        this.database = database;

        final VertexTypeInstantiator instantiator = new VertexTypeInstantiator();

        this.database.getInstantiatorRegistry().registerInstantiator( IVertexType.class, instantiator );

    }

    public void createVertexType( IVertexType vertexType ) {

        this.database.withVoidTransaction( tx -> {
            this.database.update( "INSERT INTO VERTEX_TYPE (ID, NAME, SUPER_TYPE_ID) VALUES (?, ?, ?)", 
                vertexType.getId(), vertexType.getName(), vertexType.getSuperType().getId() );

            // TODO: create the corresponding table
        } );

    }

    public void deleteVertexType( UUID vertexTypeId ) {

        // TODO

    }

    public Optional<IVertexType> findVertexTypeById( UUID vertexTypeId ) {

        return Optional.ofNullable( this.database.findUniqueOrNull( IVertexType.class,
            "SELECT TO_CHAR(ID), NAME, TO_CHAR(SUPER_TYPE_ID) FROM VERTEX_TYPE WHERE ID = ?", vertexTypeId ) );

    }

    public List<? extends IVertexType> findVertexTypesAll() {

        return this.database.findAll( IVertexType.class, "SELECT TO_CHAR(ID), NAME, TO_CHAR(SUPER_TYPE_ID) FROM VERTEX_TYPE" );

    }

    /**
     * Custom instantiator for vertex types.
     */
    private static class VertexTypeInstantiator
        implements Instantiator<IVertexType> {

        /**
         * Constructs a new instantiator.
         */
        private VertexTypeInstantiator() {
        }

        /**
         * Instantiates a vertexType either by finding it in the registry or else creating it and adding it to the registry.
         *
         * @param fields the fields from the database query.
         * @return the new vertexType.
         */
        @SuppressWarnings("NullableProblems")
        @Override
        public IVertexType instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            final UUID id = UUID.fromString( (String) fields.getValues().get( 0 ) );
            final String name = (String) fields.getValues().get( 1 );
            final UUID superTypeId = UUID.fromString( (String) fields.getValues().get( 2 ) );

            // Create the vertex type.
            if ( id.equals( superTypeId ) ) {
                return new VertexType( id, name );
            }
            else {
                return new VertexType( id, name );  // TODO: super type
            }

        }

    }

    private final Database database;

}
