//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IBooleanAttributeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.Optional;

/**
 * Implementation of a boolean attribute type.
 */
public final class BooleanAttributeType
    extends AttributeType
    implements IBooleanAttributeType {

    /**
     * Constructs a new boolean attribute type.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent attribute type.
     */
    public BooleanAttributeType(
        IBooleanAttributeType.Record record, IPackage parentPackage
    ) {
        super( record, parentPackage );
        this.defaultValue = new V<>( record.defaultValue );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        this.defaultValue.get().ifPresent( defaultValue -> json.write( "defaultValue", defaultValue ) );

    }

    @Override
    public Optional<Boolean> getDefaultValue() {
        return this.defaultValue.get();
    }

    /**
     * Changes the default value for this attribute type.
     *
     * @param defaultValue the new default.
     */
    public void setDefaultValue( Optional<Boolean> defaultValue ) {
        this.defaultValue.set( defaultValue );
    }

    /** The default value for attributes of this type. */
    private final V<Optional<Boolean>> defaultValue;

}
