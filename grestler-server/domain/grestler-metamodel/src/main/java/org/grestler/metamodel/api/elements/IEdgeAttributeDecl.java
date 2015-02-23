//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

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

}
