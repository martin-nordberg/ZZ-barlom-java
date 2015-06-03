//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
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

    class Record
        extends IAttributeType.Record {

        public Record(
            UUID id,
            UUID parentPackageId,
            String name,
            int maxLength,
            OptionalInt minLength,
            Optional<String> regexPattern
        ) {
            super( id, parentPackageId, name, EDataType.STRING );

            this.maxLength = maxLength;
            this.minLength = minLength;
            this.regexPattern = regexPattern;
        }

        public final int maxLength;

        public final OptionalInt minLength;

        public final Optional<String> regexPattern;

    }

}
