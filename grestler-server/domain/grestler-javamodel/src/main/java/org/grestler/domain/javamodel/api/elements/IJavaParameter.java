//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A Java parameter.
 */
public interface IJavaParameter
    extends IJavaTypedModelElement {

    /** @return the parent of this parameter. */
    @Override
    IJavaFunction getParent();

}
