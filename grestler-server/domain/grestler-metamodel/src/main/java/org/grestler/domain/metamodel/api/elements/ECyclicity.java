//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.Optional;

/**
 * Enumeration of possible values for cyclic/acyclic.
 */
public enum ECyclicity {

    /** Cyclicity of an edge type is unconstrained by an edge type. */
    UNCONSTRAINED,

    /** Edges of a given edge type are constrained to be acyclic. */
    ACYCLIC,

    /** Edges of a given edge type are expected to be cyclic. */
    POTENTIALLY_CYCLIC;

    /**
     * Converts a boolean value for is-acyclic into a cyclicity value.
     *
     * @param isAcyclic true if an edge type constrains its edges to be acyclic.
     *
     * @return the corresponding enum value.
     */
    public static ECyclicity fromBoolean( Optional<Boolean> isAcyclic ) {

        if ( isAcyclic.isPresent() ) {
            return isAcyclic.get() ? ECyclicity.ACYCLIC : ECyclicity.POTENTIALLY_CYCLIC;
        }

        return ECyclicity.UNCONSTRAINED;

    }

    /**
     * Converts this enum value to a boolean equivalent.
     *
     * @return true if this is acyclic.
     */
    public static Optional<Boolean> isAcyclic( ECyclicity cyclicity ) {
        if ( cyclicity == null ) {
            return Optional.empty();
        }
        return Optional.of( cyclicity == ECyclicity.ACYCLIC );
    }

}
