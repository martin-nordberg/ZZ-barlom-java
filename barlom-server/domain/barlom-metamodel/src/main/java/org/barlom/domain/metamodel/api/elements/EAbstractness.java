//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

/**
 * Enumeration of possible values for whether something is abstract.
 */
public enum EAbstractness {

    /** An element is abstract. */
    ABSTRACT,

    /** An element is concrete. */
    CONCRETE;

    /**
     * Determines the abstractness corresponding to a boolean value for is-abstract.
     *
     * @param isAbstract whether the item is abstract.
     *
     * @return the corresponding enum value.
     */
    @SuppressWarnings( "BooleanParameter" )
    public static EAbstractness fromBoolean( boolean isAbstract ) {
        return isAbstract ? EAbstractness.ABSTRACT : EAbstractness.CONCRETE;
    }

    /**
     * Converts this enum value to a boolean equivalent.
     *
     * @return true if this is abstract.
     */
    public boolean isAbstract() {
        return this == EAbstractness.ABSTRACT;
    }

}
