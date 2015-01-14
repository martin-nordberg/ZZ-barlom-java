//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IInteger32AttributeType;
import org.grestler.metamodel.api.elements.IPackage;

import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation for 32-bit integer attribute types.
 */
public class Integer32AttributeType
    extends AttributeType
    implements IInteger32AttributeType {

    /**
     * Constructs a new integer attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes of this type.
     * @param maxValue      the minimum value for attributes of this type.
     */
    protected Integer32AttributeType(
        UUID id, IPackage parentPackage, String name, OptionalInt minValue, OptionalInt maxValue
    ) {
        super( id, parentPackage, name );

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public OptionalInt getMinValue() {
        return this.minValue;
    }

    @Override
    public OptionalInt getMaxValue() {
        return this.maxValue;
    }

    private final OptionalInt maxValue;

    private final OptionalInt minValue;
}
