//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

import java.util.OptionalInt;
import java.util.UUID;

/**
 * Interface to 32-bit integer attribute types.
 */
public interface IInteger32AttributeType
    extends IAttributeType {

    class Record
        extends IAttributeType.Record {

        public Record(
            UUID id,
            UUID parentPackageId,
            String name,
            OptionalInt defaultValue,
            OptionalInt minValue,
            OptionalInt maxValue
        ) {
            super( id, parentPackageId, name, EDataType.INTEGER32 );

            this.defaultValue = defaultValue;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public final OptionalInt defaultValue;

        public final OptionalInt maxValue;

        public final OptionalInt minValue;

    }

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
