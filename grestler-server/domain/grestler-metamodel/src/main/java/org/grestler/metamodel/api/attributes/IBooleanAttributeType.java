//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.attributes;

/**
 * Interface to a boolean attribute type.
 */
public interface IBooleanAttributeType
    extends IAttributeType {

    @Override
    default EDataType getDataType() {
        return EDataType.BOOLEAN;
    }

}
