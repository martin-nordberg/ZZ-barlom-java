//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.time.Instant;
import java.util.Optional;

/**
 * Interface to a date/time attribute type.
 */
public interface IDateTimeAttributeType
    extends IAttributeType {

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
