//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;
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
     * @return the super type of this edge type.
     */
    Optional<IUndirectedEdgeType> getSuperType();

    /**
     * @return the vertex type for edges of this type.
     */
    IVertexType getVertexType();

    /**
     * Determines whether this edge type is a direct or indirect subtype of the given edge type.
     *
     * @param edgeType the potential super type
     *
     * @return true if this edge type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    boolean isSubTypeOf( IUndirectedEdgeType edgeType );

}
