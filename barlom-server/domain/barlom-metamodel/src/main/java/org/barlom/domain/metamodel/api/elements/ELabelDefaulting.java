//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

/**
 * Enumeration of possible values for whether an attribute is the default label for a vertex type.
 */
public enum ELabelDefaulting {

    /** An attribute is not the default label. */
    NOT_DEFAULT_LABEL,

    /** An element is concrete. */
    DEFAULT_LABEL;

    /**
     * Determines the label defaulting corresponding to a boolean value for is-default.
     *
     * @param isDefaultLabel whether the item is a default label.
     *
     * @return the corresponding enum value.
     */
    @SuppressWarnings( "BooleanParameter" )
    public static ELabelDefaulting fromBoolean( boolean isDefaultLabel ) {
        return isDefaultLabel ? ELabelDefaulting.DEFAULT_LABEL : ELabelDefaulting.NOT_DEFAULT_LABEL;
    }

    /**
     * Converts this enum value to the corresponding boolean.
     *
     * @return true if this is a default label.
     */
    public boolean isDefaultLabel() {
        return this == ELabelDefaulting.DEFAULT_LABEL;
    }

}
