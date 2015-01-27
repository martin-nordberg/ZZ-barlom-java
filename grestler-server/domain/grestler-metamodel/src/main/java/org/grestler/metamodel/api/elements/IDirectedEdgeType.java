//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Top level interface to an edge type.
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
     * @return the name of the role for the vertex at the tail of edges of this type.
     */
    Optional<String> getTailRoleName();

    /**
     * @return the origin vertex type for edges of this type.
     */
    IVertexType getTailVertexType();

    /**
     * The minimum number of edges out of the tail vertex for an edge of this type.
     * @return the minimum tail out degree (optional).
     */
    OptionalInt getMinTailOutDegree();

    /**
     * The maximum number of edges out of the tail vertex for an edge of this type.
     * @return the maximum tail out degree (optional).
     */
    OptionalInt getMaxTailOutDegree();

    /**
     * The minimum number of edges into the head vertex for an edge of this type.
     * @return the minimum head in degree (optional).
     */
    OptionalInt getMinHeadInDegree();

    /**
     * The maximum number of edges into the head vertex for an edge of this type.
     * @return the maximum head in degree (optional).
     */
    OptionalInt getMaxHeadInDegree();

}
