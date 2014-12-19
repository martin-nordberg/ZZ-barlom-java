//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;
import java.util.UUID;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType {

    /**
     * @return the unique ID of this vertex type.
     */
    UUID getId();

    /**
     * @return the name of this vertex type.
     */
    String getName();

    /**
     * @return the super type of this vertex type.
     */
    Optional<IVertexType> getSuperType();

    /**
     * Determines whether this vertex type is a direct or indirect subtype of the given vertex type.
     *
     * @param vertexType the potential super type
     *
     * @return true if this vertex type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    default boolean isSubTypeOf( IVertexType vertexType ) {

        IVertexType v = this;
        while ( true ) {
            if ( v == vertexType ) {
                // success if we encounter the given type somewhere along the chain
                return true;
            }
            if ( !v.getSuperType().isPresent() ) {
                // root vertex type has no super type
                return false;
            }
            v = v.getSuperType().get();
        }

    }

    /**
     * Top level base vertex type (constant).
     */
    static final IVertexType BASE_VERTEX_TYPE = new IVertexType() {
        @Override
        public UUID getId() {
            return UUID.fromString( "00000000-7a26-11e4-a545-08002741a702" );
        }

        @Override
        public String getName() {
            return "Vertex";
        }

        @Override
        public Optional<IVertexType> getSuperType() {
            return Optional.empty();
        }
    };

}
