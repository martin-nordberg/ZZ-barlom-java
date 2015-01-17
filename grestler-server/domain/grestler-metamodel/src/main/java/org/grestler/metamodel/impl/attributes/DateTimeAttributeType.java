//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IDateTimeAttributeType;
import org.grestler.metamodel.api.elements.IPackage;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Date/time attribute type implementation.
 */
public final class DateTimeAttributeType
    extends AttributeType
    implements IDateTimeAttributeType {

    /**
     * Constructs a new date/time attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes of this type.
     * @param maxValue      the minimum value for attributes of this type.
     */
    public DateTimeAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<LocalDateTime> minValue, Optional<LocalDateTime> maxValue
    ) {
        super( id, parentPackage, name );

        this.maxValue = maxValue;
        this.minValue = minValue;

    }

    @Override
    public Optional<LocalDateTime> getMaxValue() {
        return this.maxValue;
    }

    @Override
    public Optional<LocalDateTime> getMinValue() {
        return this.minValue;
    }

    /** The maximum allowed value for attributes with this type. */
    private final Optional<LocalDateTime> maxValue;

    /** The minimum allowed value for attributes with this type. */
    private final Optional<LocalDateTime> minValue;

}
