//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Top level interface to a directed edge type.
 */
public interface IDirectedEdgeType
    extends IEdgeType {

    /**
     * @return the name of the role for the vertex at the head of edges of this type.
     */
    Optional<String> getHeadRoleName();

    /**
     * @return the destination vertex type for edges of this type.
     */
    IVertexType getHeadVertexType();

    /**
     * The maximum number of edges into the head vertex for an edge of this type.
     *
     * @return the maximum head in degree (optional).
     */
    OptionalInt getMaxHeadInDegree();

    /**
     * The maximum number of edges out of the tail vertex for an edge of this type.
     *
     * @return the maximum tail out degree (optional).
     */
    OptionalInt getMaxTailOutDegree();

    /**
     * The minimum number of edges into the head vertex for an edge of this type.
     *
     * @return the minimum head in degree (optional).
     */
    OptionalInt getMinHeadInDegree();

    /**
     * The minimum number of edges out of the tail vertex for an edge of this type.
     *
     * @return the minimum tail out degree (optional).
     */
    OptionalInt getMinTailOutDegree();

    /**
     * @return the super type of this edge type.
     */
    Optional<IDirectedEdgeType> getSuperType();

    /**
     * @return the name of the role for the vertex at the tail of edges of this type.
     */
    Optional<String> getTailRoleName();

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
    boolean isSubTypeOf( IDirectedEdgeType edgeType );

}
