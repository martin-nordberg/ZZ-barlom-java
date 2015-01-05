//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.UUID;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType
    extends IElement {

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
     * The fixed ID for the base vertex type.
     */
    final UUID BASE_VERTEX_TYPE_ID = UUID.fromString( "00000010-7a26-11e4-a545-08002741a702" );

    /**
     * Top level base vertex type (constant).
     */
    static final IVertexType BASE_VERTEX_TYPE = new IVertexType() {
        @Override
        public void generateJson( JsonGenerator json ) {
            json.writeStartObject()
                .write( "id", BASE_VERTEX_TYPE_ID.toString() )
                .write( "parentPackageId", IPackage.ROOT_PACKAGE_ID.toString() )
                .write( "name", "Vertex" )
                .write( "path", "Vertex" )
                .writeEnd();
        }

        @Override
        public UUID getId() {
            return BASE_VERTEX_TYPE_ID;
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
