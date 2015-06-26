//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.OptionalDouble;
import java.util.UUID;

/**
 * Interface to 64-bit floating point attribute types.
 */
public interface IFloat64AttributeType
    extends IAttributeType {

    class Record
        extends IAttributeType.Record {

        public Record(
            UUID id,
            UUID parentPackageId,
            String name,
            OptionalDouble defaultValue,
            OptionalDouble minValue,
            OptionalDouble maxValue
        ) {
            super( id, parentPackageId, name, EDataType.FLOAT64 );

            this.defaultValue = defaultValue;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public final OptionalDouble defaultValue;

        public final OptionalDouble maxValue;

        public final OptionalDouble minValue;

    }

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
