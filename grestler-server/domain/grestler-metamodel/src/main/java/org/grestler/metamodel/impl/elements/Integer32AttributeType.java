//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IInteger32AttributeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
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
     * @param defaultValue  the default value for attributes of this type.
     */
    public Integer32AttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalInt minValue,
        OptionalInt maxValue,
        OptionalInt defaultValue
    ) {
        super( id, parentPackage, name );

        this.minValue = new V<>( minValue );
        this.maxValue = new V<>( maxValue );
        this.defaultValue = new V<>( defaultValue );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        this.minValue.get().ifPresent( minValue -> json.write( "minValue", minValue ) );
        this.maxValue.get().ifPresent( maxValue -> json.write( "maxValue", maxValue ) );
        this.defaultValue.get().ifPresent( defaultValue -> json.write( "defaultValue", defaultValue ) );

    }

    @Override
    public OptionalInt getDefaultValue() {
        return this.defaultValue.get();
    }

    @Override
    public OptionalInt getMaxValue() {
        return this.maxValue.get();
    }

    @Override
    public OptionalInt getMinValue() {
        return this.minValue.get();
    }

    /**
     * Changes the default value for attributes of this type.
     *
     * @param defaultValue the new default value.
     */
    public void setDefaultValue( OptionalInt defaultValue ) {
        this.defaultValue.set( defaultValue );
    }

    /**
     * Changes the maximum value for attributes of this type.
     *
     * @param maxValue the new maximum value.
     */
    public void setMaxValue( OptionalInt maxValue ) {
        this.maxValue.set( maxValue );
    }

    /**
     * Changes the minimum value for attributes of this type.
     *
     * @param minValue the new minimum value.
     */
    public void setMinValue( OptionalInt minValue ) {
        this.minValue.set( minValue );
    }

    /** The default value for attributes of this type. */
    private final V<OptionalInt> defaultValue;

    /** The minimum allowed value for attributes with this type. */
    private final V<OptionalInt> maxValue;

    /** The maximum allowed value for attributes with this type. */
    private final V<OptionalInt> minValue;

}
