//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.OptionalDouble;

/**
 * Interface to 64-bit floating point attribute types.
 */
public interface IFloat64AttributeType
    extends IAttributeType {

    @Override
    default EDataType getDataType() {
        return EDataType.FLOAT64;
    }

    /**
     * @return the default value for attributes of this type.
     */
    OptionalDouble getDefaultValue();

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    OptionalDouble getMaxValue();

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    OptionalDouble getMinValue();

}
