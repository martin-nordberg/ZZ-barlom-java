//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.UUID;

/**
 * Interface to a vertex attribute declaration.
 */
public interface IVertexAttributeDecl
    extends INamedElement {

    /**
     * @return whether this is the default label for the vertex.
     */
    ELabelDefaulting getLabelDefaulting();

    /**
     * @return whether this is a required attribute.
     */
    EAttributeOptionality getOptionality();

    @Override
    default INamedElement getParent() {
        return this.getParentVertexType();
    }

    /**
     * @return the parent of this attribute.
     */
    IVertexType getParentVertexType();

    /**
     * @return the typeof this attribute.
     */
    IAttributeType getType();

    class Record
        extends INamedElement.Record {

        public Record(
            UUID id,
            String name,
            ELabelDefaulting labelDefaulting,
            EAttributeOptionality optionality,
            UUID parentVertexTypeId,
            UUID typeId
        ) {
            super( id, name );

            this.labelDefaulting = labelDefaulting;
            this.optionality = optionality;
            this.parentVertexTypeId = parentVertexTypeId;
            this.typeId = typeId;
        }

        public final ELabelDefaulting labelDefaulting;

        public final EAttributeOptionality optionality;

        public final UUID parentVertexTypeId;

        public final UUID typeId;

    }
}
