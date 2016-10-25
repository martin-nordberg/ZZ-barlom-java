//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface to a date/time attribute type.
 */
public interface IDateTimeAttributeType
    extends IAttributeType {

    /**
     * Attributes of a date/time attribute type.
     */
    class Record
        extends IAttributeType.Record {

        public Record(
            UUID id, UUID parentPackageId, String name, Optional<Instant> minValue, Optional<Instant> maxValue
        ) {
            super( id, parentPackageId, name, EDataType.DATETIME );
            this.maxValue = maxValue;
            this.minValue = minValue;
        }

        public final Optional<Instant> maxValue;

        public final Optional<Instant> minValue;

    }

    @Override
    default EDataType getDataType() {
        return EDataType.DATETIME;
    }

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    Optional<Instant> getMaxValue();

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    Optional<Instant> getMinValue();
}
