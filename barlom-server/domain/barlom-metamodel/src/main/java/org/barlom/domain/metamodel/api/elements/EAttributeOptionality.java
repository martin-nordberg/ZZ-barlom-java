//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

/**
 * Enumeration of possible values for whether an attribute is required or optional.
 */
public enum EAttributeOptionality {

    /** An attribute is optional. */
    OPTIONAL,

    /** An attribute is required. */
    REQUIRED;

    /**
     * Converts a boolean is-required value to an enum value.
     *
     * @param isRequired whether the attribute is required.
     *
     * @return the corresponding enum value.
     */
    @SuppressWarnings( "BooleanParameter" )
    public static EAttributeOptionality fromBoolean( boolean isRequired ) {
        return isRequired ? EAttributeOptionality.REQUIRED : EAttributeOptionality.OPTIONAL;
    }

}
