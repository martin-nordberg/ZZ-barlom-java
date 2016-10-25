//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

import java.util.UUID;

/**
 * Interface to a UUID attribute type.
 */
public interface IUuidAttributeType
    extends IAttributeType {

    class Record
        extends IAttributeType.Record {

        public Record(
            UUID id, UUID parentPackageId, String name
        ) {
            super( id, parentPackageId, name, EDataType.UUID );
        }

    }

    @Override
    default EDataType getDataType() {
        return EDataType.UUID;
    }
}
