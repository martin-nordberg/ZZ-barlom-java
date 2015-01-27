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
     * @return the abstractness of this edge type
     */
    EAbstractness getAbstractness();

    /**
     * @return the defined attributes of this edge type.
     */
    List<IEdgeAttributeDecl> getAttributes();

    /**
     * @return whether edges of this type can form a cyclic graph.
     */
    ECyclicity getCyclicity();

    /**
     * @return whether edges of this type must be unique between any two given vertexes.
     */
    EMultiEdgedness getMultiEdgedness();

    /**
     * @return whether edges of this type can connect a vertex to itself.
     */
    ESelfEdgedness getSelfEdgedness();

    /**
     * @return the super type of this edge type.
     */
    Optional<IEdgeType> getSuperType();

    /**
     * @return whether this edge type is abstract, i.e has no concrete instances. Note that a super type must be
     * abstract.
     */
    default boolean isAbstract() {
        return this.getAbstractness() == EAbstractness.ABSTRACT;
    }

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
