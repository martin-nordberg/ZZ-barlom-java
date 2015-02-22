//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IInteger32AttributeType;
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

    private final V<OptionalInt> defaultValue;

    private final V<OptionalInt> maxValue;

    private final V<OptionalInt> minValue;
}
