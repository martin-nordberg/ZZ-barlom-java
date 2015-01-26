//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;

/**
 * Enumeration of possible constraints for multi edges in an edge type.
 */
public enum EMultiEdgedness {

    /** An edge type does not constrain the multi-edgedness of its edges. */
    UNCONSTRAINED,

    /** An edge type allows multiple edges between two given vertexes. */
    MULTI_EDGES_ALLOWED,

    /** An edge type disallows multiple edges between two given vertexes. */
    MULTI_EDGES_NOT_ALLOWED;

    /**
     * Converts a boolen os-multi-edge-allowed value to an enum value.
     * @param isMultiEdgeAllowed whether multi-edges are allowed.
     * @return the corresponding enum value.
     */
    public static EMultiEdgedness fromBoolean( Optional<Boolean> isMultiEdgeAllowed ) {

        if ( isMultiEdgeAllowed.isPresent() ) {
            return isMultiEdgeAllowed.get() ? EMultiEdgedness.MULTI_EDGES_ALLOWED : EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED;
        }

        return EMultiEdgedness.UNCONSTRAINED;

    }

}
