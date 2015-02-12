//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;

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

}
