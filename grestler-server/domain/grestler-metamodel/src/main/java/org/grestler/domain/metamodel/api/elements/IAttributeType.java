//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.UUID;

/**
 * Base interface for an abstract attribute type.
 */
public interface IAttributeType
    extends IPackagedElement {

    /**
     * @return the fundamental data type for attributes of this type.
     */
    EDataType getDataType();

    /**
     * Data structure for the attributes of an attribute type.
     */
    class Record
        extends IPackagedElement.Record {

        protected Record( UUID id, UUID parentPackageId, String name, EDataType dataType ) {
            super( id, parentPackageId, name );
            this.dataType = dataType;
        }

        public final EDataType dataType;

    }
}
