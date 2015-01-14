//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IFloat64AttributeType;
import org.grestler.metamodel.api.elements.IPackage;

import java.util.OptionalDouble;
import java.util.UUID;

/**
 * Implementation for 64-bit floating point attribute types.
 */
public final class Float64AttributeType
    extends AttributeType
    implements IFloat64AttributeType {

    /**
     * Constructs a new floating point attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes of this type.
     * @param maxValue      the minimum value for attributes of this type.
     */
    protected Float64AttributeType(
        UUID id, IPackage parentPackage, String name, OptionalDouble minValue, OptionalDouble maxValue
    ) {
        super( id, parentPackage, name );

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public OptionalDouble getMaxValue() {
        return this.maxValue;
    }

    @Override
    public OptionalDouble getMinValue() {
        return this.minValue;
    }

    /** The minimum allowed value for attributes with this type. */
    private final OptionalDouble maxValue;

    /** The maximum allowed value for attributes with this type. */
    private final OptionalDouble minValue;

}
