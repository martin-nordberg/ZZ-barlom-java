//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IBooleanAttributeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of a boolean attribute type.
 */
public final class BooleanAttributeType
    extends AttributeType
    implements IBooleanAttributeType {

    /**
     * Constructs a new boolean attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param defaultValue  the default value for attributes of this type.
     */
    public BooleanAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Boolean> defaultValue
    ) {
        super( id, parentPackage, name );
        this.defaultValue = new V<>( defaultValue );
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
