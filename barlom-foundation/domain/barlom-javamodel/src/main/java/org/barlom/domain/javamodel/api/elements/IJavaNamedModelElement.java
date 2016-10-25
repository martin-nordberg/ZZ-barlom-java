//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

import org.barlom.infrastructure.utilities.collections.IIndexable;

/**
 * A Java model element with a name.
 */
public interface IJavaNamedModelElement
    extends IJavaModelElement, IJavaNamed, Comparable<IJavaNamedModelElement> {

    /**
     * @return The children of this model element.
     */
    IIndexable<IJavaModelElement> getChildren();

    @Override
    IJavaNamedModelElement getParent();

}
