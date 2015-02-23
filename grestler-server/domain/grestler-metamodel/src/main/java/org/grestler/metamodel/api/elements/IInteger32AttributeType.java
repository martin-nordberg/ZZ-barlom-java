//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.OptionalInt;

/**
 * Interface to 32-bit integer attribute types.
 */
public interface IInteger32AttributeType
    extends IAttributeType {

    @Override
    default EDataType getDataType() {
        return EDataType.INTEGER32;
    }

    /**
     * @return the default value for attributes of this type.
     */
    OptionalInt getDefaultValue();

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    OptionalInt getMaxValue();

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    OptionalInt getMinValue();

}
