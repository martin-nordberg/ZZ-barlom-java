//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.Optional;

/**
 * Interface to a boolean attribute type.
 */
public interface IBooleanAttributeType
    extends IAttributeType {

    @Override
    default EDataType getDataType() {
        return EDataType.BOOLEAN;
    }

    /**
     * The default value for attributes of this type.
     *
     * @return the default value.
     */
    Optional<Boolean> getDefaultValue();

}
