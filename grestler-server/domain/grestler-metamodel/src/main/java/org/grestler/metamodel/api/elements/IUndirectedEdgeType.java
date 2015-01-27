//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.OptionalInt;

/**
 * Top level interface to an undirected edge type.
 */
public interface IUndirectedEdgeType
    extends IEdgeType {

    /**
     * The maximum number of edges connected to any vertex for an edge of this type.
     *
     * @return the maximum degree (optional).
     */
    OptionalInt getMaxDegree();

    /**
     * The minimum number of edges connected to any vertex for an edge of this type.
     *
     * @return the minimum degree (optional).
     */
    OptionalInt getMinDegree();

    /**
     * @return the vertex type for edges of this type.
     */
    IVertexType getVertexType();

}
