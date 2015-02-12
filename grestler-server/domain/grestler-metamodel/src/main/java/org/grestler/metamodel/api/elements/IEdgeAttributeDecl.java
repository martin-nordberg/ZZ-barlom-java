//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;

/**
 * Interface to an edge attribute declaration.
 */
public interface IEdgeAttributeDecl
    extends IElement {

    /**
     * @return whether this is a required attribute.
     */
    EAttributeOptionality getOptionality();

    @Override
    default IElement getParent() {
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

}
