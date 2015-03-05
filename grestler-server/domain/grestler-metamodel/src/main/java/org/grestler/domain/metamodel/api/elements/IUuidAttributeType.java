//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

/**
 * Interface to a UUID attribute type.
 */
public interface IUuidAttributeType
    extends IAttributeType {

    @Override
    default EDataType getDataType() {
        return EDataType.UUID;
    }

}
