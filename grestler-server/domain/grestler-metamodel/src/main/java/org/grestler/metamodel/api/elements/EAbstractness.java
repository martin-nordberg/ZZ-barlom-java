//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

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
     * @param isAbstract whetehr the item is abstract.
     * @return the corresponding enum value.
     */
    @SuppressWarnings( "BooleanParameter" )
    public static EAbstractness fromBoolean( boolean isAbstract ) {
        return isAbstract ? EAbstractness.ABSTRACT : EAbstractness.CONCRETE;
    }

}
