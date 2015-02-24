//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import org.grestler.utilities.collections.ISizedIterable;

import java.util.Optional;

/**
 * Top level interface to an edge type.
 */
public interface IEdgeType
    extends IPackagedElement {

    /**
     * @return the abstractness of this edge type
     */
    EAbstractness getAbstractness();

    /**
     * @return the defined attributes of this edge type.
     */
    ISizedIterable<IEdgeAttributeDecl> getAttributes();

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
    ESelfLooping getSelfLooping();


    /**
     * @return the super type of this edge type.
     */
    Optional<IEdgeType> getSuperEdgeType();

    /**
     * @return whether this edge type is abstract, i.e has no concrete instances. Note that a super type must be
     * abstract.
     */
    default boolean isAbstract() {
        return this.getAbstractness() == EAbstractness.ABSTRACT;
    }

    /**
     * @return whether graphs formed by this edge type are simple, i.e. have neither self-loops not multi-edges.
     */
    default boolean isSimple() {
        return this.getMultiEdgedness() == EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED && this.getSelfLooping() == ESelfLooping.SELF_LOOPS_NOT_ALLOWED;
    }

}
