//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Pattern;

/**
 * Interface to string attribute types.
 */
public interface IStringAttributeType
    extends IAttributeType {

    @Override
    default EDataType getDataType() {
        return EDataType.STRING;
    }

    /**
     * @return the maximum allowed length for attributes of this type.
     */
    int getMaxLength();

    /**
     * @return the minimum allowed length for attributes of this type.
     */
    OptionalInt getMinLength();

    /**
     * @return a regular expression that must be matched by values of this attribute type.
     */
    Optional<Pattern> getRegexPattern();

}
