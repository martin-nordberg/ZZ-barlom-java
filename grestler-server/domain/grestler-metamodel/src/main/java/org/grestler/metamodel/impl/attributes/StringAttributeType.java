//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IStringAttributeType;
import org.grestler.metamodel.api.elements.IPackage;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Implementation for string attribute types.
 */
public final class StringAttributeType
    extends AttributeType
    implements IStringAttributeType {

    /**
     * Constructs a new integer attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minLength     the minimum length for values with this attribute type.
     * @param maxLength     the maximum length for values with this attribute type.
     * @param regexPattern  a regular expression that must be matched by values with this attribute type.
     */
    public StringAttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalInt minLength,
        int maxLength,
        Optional<String> regexPattern
    ) {
        super( id, parentPackage, name );

        this.minLength = minLength;
        this.maxLength = maxLength;

        if ( regexPattern.isPresent() ) {
            this.regexPattern = Optional.of( Pattern.compile( regexPattern.get() ) );
        }
        else {
            this.regexPattern = Optional.empty();
        }
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        this.minLength.ifPresent( minLength -> json.write( "minLength", minLength ) );
        json.write( "maxLength", this.maxLength );
        this.regexPattern.ifPresent( regexPattern -> json.write( "regexPattern", regexPattern.toString() ) );

    }

    @Override
    public int getMaxLength() {
        return this.maxLength;
    }

    @Override
    public OptionalInt getMinLength() {
        return this.minLength;
    }

    @Override
    public Optional<Pattern> getRegexPattern() {
        return this.regexPattern;
    }

    /** The maximum length for values with this attribute type. */
    private final int maxLength;

    /** The minimum length for attributes of this type. */
    private final OptionalInt minLength;

    /** The regular expression that must be matched by values with this attribute type. */
    private final Optional<Pattern> regexPattern;

}
