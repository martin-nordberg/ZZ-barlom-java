//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IUuidAttributeType;

/**
 * Implementation of a UUID attribute type.
 */
public final class UuidAttributeType
    extends AttributeType
    implements IUuidAttributeType {

    /**
     * Constructs a new UUID attribute type.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent attribute type.
     */
    public UuidAttributeType(
        IUuidAttributeType.Record record, IPackage parentPackage
    ) {
        super( record, parentPackage );
    }

}
