//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

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
