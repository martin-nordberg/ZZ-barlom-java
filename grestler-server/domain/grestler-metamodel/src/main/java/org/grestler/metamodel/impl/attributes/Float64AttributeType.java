//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IFloat64AttributeType;
import org.grestler.metamodel.api.elements.IPackage;

import javax.json.stream.JsonGenerator;
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
     * @param defaultValue  the default value for attributes of this type.
     */
    public Float64AttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalDouble minValue,
        OptionalDouble maxValue,
        OptionalDouble defaultValue
    ) {
        super( id, parentPackage, name );

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        this.minValue.ifPresent( minValue -> json.write( "minValue", minValue ) );
        this.maxValue.ifPresent( maxValue -> json.write( "maxValue", maxValue ) );
        this.defaultValue.ifPresent( defaultValue -> json.write( "defaultValue", defaultValue ) );

    }

    @Override
    public OptionalDouble getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public OptionalDouble getMaxValue() {
        return this.maxValue;
    }

    @Override
    public OptionalDouble getMinValue() {
        return this.minValue;
    }

    /** The default value for attributes of this type. */
    private final OptionalDouble defaultValue;

    /** The minimum allowed value for attributes with this type. */
    private final OptionalDouble maxValue;

    /** The maximum allowed value for attributes with this type. */
    private final OptionalDouble minValue;

}
