//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.List;
import java.util.Optional;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType
    extends IPackagedElement {

    /**
     * @return the abstractness of this vertex type
     */
    EAbstractness getAbstractness();

    /**
     * @return the defined attributes of this vertex type.
     */
    List<IVertexAttributeDecl> getAttributes();

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
    boolean isSubTypeOf( IVertexType vertexType );

}
