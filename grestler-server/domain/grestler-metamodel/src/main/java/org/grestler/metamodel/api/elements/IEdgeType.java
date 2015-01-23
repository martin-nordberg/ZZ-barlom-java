//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.List;
import java.util.Optional;

/**
 * Top level interface to an edge type.
 */
public interface IEdgeType
    extends IElement {

    /**
     * @return the defined attributes of this edge type.
     */
    List<IEdgeAttributeDecl> getAttributes();

    /**
     * @return the destination vertex type for edges of this type.
     */
    IVertexType getHeadVertexType();

    /**
     * @return the super type of this edge type.
     */
    Optional<IEdgeType> getSuperType();

    /**
     * @return the origin vertex type for edges of this type.
     */
    IVertexType getTailVertexType();

    /**
     * Determines whether this edge type is a direct or indirect subtype of the given edge type.
     *
     * @param edgeType the potential super type
     *
     * @return true if this edge type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    boolean isSubTypeOf( IEdgeType edgeType );

}
