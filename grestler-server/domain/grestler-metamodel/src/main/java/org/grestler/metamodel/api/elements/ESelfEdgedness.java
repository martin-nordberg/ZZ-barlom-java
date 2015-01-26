//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;

/**
 * Enumeration of possible constraints for self edges in an edge type.
 */
public enum ESelfEdgedness {

    /** Self edges are unconstraiend by an edge type. */
    UNCONSTRAINED,

    /** Self edges are allowed by an edge type. */
    SELF_EDGES_ALLOWED,

    /** Self edges are disallowed by an edge type. */
    SELF_EDGES_NOT_ALLOWED;

    /**
     * Converts a boolean is-self-edge-allowed value to a enum value.
     *
     * @param isSelfEdgeAllowed whether self edges are allowed.
     *
     * @return the corresponding enum value.
     */
    public static ESelfEdgedness fromBoolean( Optional<Boolean> isSelfEdgeAllowed ) {

        if ( isSelfEdgeAllowed.isPresent() ) {
            return isSelfEdgeAllowed.get() ? ESelfEdgedness.SELF_EDGES_ALLOWED : ESelfEdgedness.SELF_EDGES_NOT_ALLOWED;
        }

        return ESelfEdgedness.UNCONSTRAINED;

    }

}
