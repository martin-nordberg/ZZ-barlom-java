//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IPackage;

import javax.json.stream.JsonGenerator;

/**
 * Attribute type implementation.
 */
public abstract class AttributeType
    extends PackagedElement
    implements IAttributeType {

    /**
     * Constructs a new attribute type.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent attribute type.
     */
    protected AttributeType( IAttributeType.Record record, IPackage parentPackage ) {
        super( record, parentPackage );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "dataType", this.getDataType().name() );

    }

}
