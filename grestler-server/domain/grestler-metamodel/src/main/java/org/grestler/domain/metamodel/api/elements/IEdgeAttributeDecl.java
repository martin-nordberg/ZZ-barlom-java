//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.UUID;

/**
 * Interface to an edge attribute declaration.
 */
public interface IEdgeAttributeDecl
    extends INamedElement {

    /**
     * @return whether this is a required attribute.
     */
    EAttributeOptionality getOptionality();

    @Override
    default INamedElement getParent() {
        return this.getParentEdgeType();
    }

    /**
     * @return the parent of this attribute.
     */
    IEdgeType getParentEdgeType();

    /**
     * @return the type of this attribute.
     */
    IAttributeType getType();

    class Record
        extends INamedElement.Record {

        public Record( UUID id, String name, EAttributeOptionality optionality, UUID parentEdgeTypeId, UUID typeId ) {
            super( id, name );

            this.optionality = optionality;
            this.parentEdgeTypeId = parentEdgeTypeId;
            this.typeId = typeId;
        }

        public final EAttributeOptionality optionality;

        public final UUID parentEdgeTypeId;

        public final UUID typeId;

    }
}
