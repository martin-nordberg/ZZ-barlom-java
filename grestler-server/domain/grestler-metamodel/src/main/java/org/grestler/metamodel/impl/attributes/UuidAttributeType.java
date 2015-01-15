//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes;

import org.grestler.metamodel.api.attributes.IUuidAttributeType;
import org.grestler.metamodel.api.elements.IPackage;

import java.util.UUID;

/**
 * Implementation of a UUID attribute type.
 */
public final class UuidAttributeType
    extends AttributeType
    implements IUuidAttributeType {

    /**
     * Constructs a new UUID attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     */
    public UuidAttributeType(
        UUID id, IPackage parentPackage, String name
    ) {
        super( id, parentPackage, name );
    }

}
