//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;
import java.util.UUID;

/**
 * Top level interface to an edge type.
 */
public interface IEdgeType {

    /**
     * @return the origin vertex type for edges of this type.
     */
    IVertexType getFromVertexType();

    /**
     * @return the unique ID of this edge type.
     */
    UUID getId();

    /**
     * @return the name of this edge type.
     */
    String getName();

    /**
     * @return the super type of this edge type.
     */
    Optional<IEdgeType> getSuperType();

    /**
     * @return the destination vertex type for edges of this type.
     */
    IVertexType getToVertexType();

    /**
     * Determines whether this edge type is a direct or indirect subtype of the given edge type.
     *
     * @param edgeType the potential super type
     *
     * @return true if this edge type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    default boolean isSubTypeOf( IEdgeType edgeType ) {

        IEdgeType v = this;
        while ( true ) {
            if ( v == edgeType ) {
                // success if we encounter the given type somewhere along the chain
                return true;
            }
            if ( !v.getSuperType().isPresent() ) {
                // root edge type has no super type
                return false;
            }
            v = v.getSuperType().get();
        }

    }

    /**
     * Top level base edge type (constant).
     */
    static final IEdgeType BASE_EDGE_TYPE = new IEdgeType() {
        @Override
        public IVertexType getFromVertexType() {
            return IVertexType.BASE_VERTEX_TYPE;
        }

        @Override
        public UUID getId() {
            return UUID.fromString( "00000001-7a26-11e4-a545-08002741a702" );
        }

        @Override
        public String getName() {
            return "Edge";
        }

        @Override
        public Optional<IEdgeType> getSuperType() {
            return Optional.empty();
        }

        @Override
        public IVertexType getToVertexType() {
            return IVertexType.BASE_VERTEX_TYPE;
        }
    };

}
