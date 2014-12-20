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
     * @return the parent of this vertex type.
     */
    IPackage getParentPackage();

    /**
     * @return the fully qualified path to this vertex type.
     */
    default String getPath() {

        String result = this.getParentPackage().getPath();

        if ( !result.isEmpty() ) {
            return result + "." + this.getName();
        }

        return this.getName();

    }

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
        return this == vertexType || this.getSuperType().get().isSubTypeOf( vertexType );
    }

    /**
     * Top level base vertex type (constant).
     */
    static final IVertexType BASE_VERTEX_TYPE = new IVertexType() {
        @Override
        public UUID getId() {
            return UUID.fromString( "00000010-7a26-11e4-a545-08002741a702" );
        }

        @Override
        public String getName() {
            return "Vertex";
        }

        @Override
        public IPackage getParentPackage() {
            return IPackage.ROOT_PACKAGE;
        }

        @Override
        public Optional<IVertexType> getSuperType() {
            return Optional.empty();
        }

        @Override
        public boolean isSubTypeOf( IVertexType vertexType ) {
            return this == vertexType;
        }
    };

}
