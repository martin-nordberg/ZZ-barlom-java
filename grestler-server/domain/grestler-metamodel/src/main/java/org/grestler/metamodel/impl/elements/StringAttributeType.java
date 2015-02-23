//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IStringAttributeType;
import org.grestler.utilities.revisions.V;
import org.grestler.utilities.revisions.VInt;

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

        this.minLength = new V<>( minLength );
        this.maxLength = new VInt( maxLength );

        if ( regexPattern.isPresent() ) {
            this.regexPattern = new V<>( Optional.of( Pattern.compile( regexPattern.get() ) ) );
        }
        else {
            this.regexPattern = new V<>( Optional.empty() );
        }
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        this.minLength.get().ifPresent( minLength -> json.write( "minLength", minLength ) );
        json.write( "maxLength", this.maxLength.get() );
        this.regexPattern.get().ifPresent( regexPattern -> json.write( "regexPattern", regexPattern.toString() ) );

    }

    @Override
    public int getMaxLength() {
        return this.maxLength.get();
    }

    @Override
    public OptionalInt getMinLength() {
        return this.minLength.get();
    }

    @Override
    public Optional<Pattern> getRegexPattern() {
        return this.regexPattern.get();
    }

    /**
     * Changes the maximum length for attributes of this type.
     *
     * @param maxLength the new maximum.
     */
    public void setMaxLength( int maxLength ) {
        this.maxLength.set( maxLength );
    }

    /**
     * Changes the minimum length for attributes of this type.
     *
     * @param minLength the new minimum.
     */
    public void setMinLength( OptionalInt minLength ) {
        this.minLength.set( minLength );
    }

    /**
     * Changes the regular expression that must be matched by attributes of this type.
     *
     * @param regexPattern the new regular expression.
     */
    public void setRegexPattern( Optional<String> regexPattern ) {
        if ( regexPattern.isPresent() ) {
            this.regexPattern.set( Optional.of( Pattern.compile( regexPattern.get() ) ) );
        }
        else {
            this.regexPattern.set( Optional.empty() );
        }
    }

    /** The maximum length for values with this attribute type. */
    private final VInt maxLength;

    /** The minimum length for attributes of this type. */
    private final V<OptionalInt> minLength;

    /** The regular expression that must be matched by values with this attribute type. */
    private final V<Optional<Pattern>> regexPattern;

}
