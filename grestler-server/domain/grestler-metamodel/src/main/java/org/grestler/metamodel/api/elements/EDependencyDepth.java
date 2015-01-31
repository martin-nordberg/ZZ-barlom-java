//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

/**
 * Enumeration of possible values for whether a dependency is direct or can also be indirect.
 */
public enum EDependencyDepth {

    /** The dependency is direct. */
    DIRECT,

    /** The dependency is direct or indirect. */
    TRANSITIVE;

    /**
     * Determines the dependency depth corresponding to a boolean value for is-transitive.
     *
     * @param isTransitive whether the depth is transitive.
     *
     * @return the corresponding enum value.
     */
    @SuppressWarnings( "BooleanParameter" )
    public static EDependencyDepth fromBoolean( boolean isTransitive ) {
        return isTransitive ? EDependencyDepth.TRANSITIVE : EDependencyDepth.DIRECT;
    }

    /**
     * Converts this enum value to a boolean equivalent.
     *
     * @return true if this is transitive.
     */
    public boolean isTransitive() {
        return this == EDependencyDepth.TRANSITIVE;
    }

}
