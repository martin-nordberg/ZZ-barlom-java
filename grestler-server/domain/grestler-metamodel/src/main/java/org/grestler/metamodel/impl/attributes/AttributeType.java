//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.impl.elements.PackagedElement;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Attribute type implementation.
 */
public abstract class AttributeType
    extends PackagedElement
    implements IAttributeType {

    /**
     * Constructs a new attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     */
    protected AttributeType( UUID id, IPackage parentPackage, String name ) {
        super( id, parentPackage, name );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "dataType", this.getDataType().name() );

    }
}
