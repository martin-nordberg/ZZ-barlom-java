//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.Optional;

/**
 * Enumeration of possible constraints for self loops in an edge type.
 */
public enum ESelfLooping {

    /** Self loops are unconstrained by an edge type. */
    UNCONSTRAINED,

    /** Self loops are allowed by an edge type. */
    SELF_LOOPS_ALLOWED,

    /** Self loops are disallowed by an edge type. */
    SELF_LOOPS_NOT_ALLOWED;

    /**
     * Converts a boolean is-self-loop-allowed value to a enum value.
     *
     * @param isSelfLoopAllowed whether self loops are allowed.
     *
     * @return the corresponding enum value.
     */
    public static ESelfLooping fromBoolean( Optional<Boolean> isSelfLoopAllowed ) {

        if ( isSelfLoopAllowed.isPresent() ) {
            return isSelfLoopAllowed.get() ? ESelfLooping.SELF_LOOPS_ALLOWED : ESelfLooping.SELF_LOOPS_NOT_ALLOWED;
        }

        return ESelfLooping.UNCONSTRAINED;

    }

}
