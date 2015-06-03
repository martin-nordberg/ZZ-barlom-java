//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IStringAttributeType;
import org.grestler.infrastructure.utilities.revisions.V;
import org.grestler.infrastructure.utilities.revisions.VInt;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Pattern;

/**
 * Implementation for string attribute types.
 */
public final class StringAttributeType
    extends AttributeType
    implements IStringAttributeType {

    /**
     * Constructs a new string attribute type.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent of the attribute type.
     */
    public StringAttributeType(
        IStringAttributeType.Record record, IPackage parentPackage
    ) {
        super( record, parentPackage );

        this.minLength = new V<>( record.minLength );
        this.maxLength = new VInt( record.maxLength );

        if ( record.regexPattern.isPresent() ) {
            this.regexPattern = new V<>( Optional.of( Pattern.compile( record.regexPattern.get() ) ) );
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
