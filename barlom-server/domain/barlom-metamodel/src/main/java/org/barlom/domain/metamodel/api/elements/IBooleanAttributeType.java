//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface to a boolean attribute type.
 */
public interface IBooleanAttributeType
    extends IAttributeType {

    /**
     * Data structure for the attributes of a boolean attribute type.
     */
    class Record
        extends IAttributeType.Record {

        public Record(
            UUID id, UUID parentPackageId, String name, Optional<Boolean> defaultValue
        ) {
            super( id, parentPackageId, name, EDataType.BOOLEAN );
            this.defaultValue = defaultValue;
        }

        public final Optional<Boolean> defaultValue;

    }

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
