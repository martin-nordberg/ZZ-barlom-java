//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IDateTimeAttributeType;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Date/time attribute type implementation.
 */
public final class DateTimeAttributeType
    extends AttributeType
    implements IDateTimeAttributeType {

    /**
     * Constructs a new date/time attribute type.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent attribute type.
     */
    public DateTimeAttributeType(
        IDateTimeAttributeType.Record record, IPackage parentPackage
    ) {

        super( record, parentPackage );

        this.maxValue = new V<>( record.maxValue );
        this.minValue = new V<>( record.minValue );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        DateTimeFormatter jsonDateTimeFormatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" )
                                                                   .withZone( ZoneOffset.UTC );

        this.maxValue.get().ifPresent(
            maxValue -> json.write(
                "maxValue", jsonDateTimeFormatter.format( maxValue )
            )
        );
        this.minValue.get().ifPresent(
            minValue -> json.write(
                "minValue", jsonDateTimeFormatter.format( minValue )
            )
        );

    }

    @Override
    public Optional<Instant> getMaxValue() {
        return this.maxValue.get();
    }

    @Override
    public Optional<Instant> getMinValue() {
        return this.minValue.get();
    }

    /**
     * Updates the maximum value for this attribute type.
     *
     * @param maxValue the new maximum for attributes of this type.
     */
    public void setMaxValue( Optional<Instant> maxValue ) {
        this.maxValue.set( maxValue );
    }

    /**
     * Updates the minimum value for this attribute type.
     *
     * @param minValue the new minimum for attributes of this type.
     */
    public void setMinValue( Optional<Instant> minValue ) {
        this.minValue.set( minValue );
    }

    /** The maximum allowed value for attributes with this type. */
    private final V<Optional<Instant>> maxValue;

    /** The minimum allowed value for attributes with this type. */
    private final V<Optional<Instant>> minValue;

}
