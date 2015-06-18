//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A static initialization block.
 */
public interface IJavaStaticInitialization
    extends IJavaModelElement {

    /** Returns the code. */
    String getCode();

    @Override
    IJavaConcreteComponent getParent();

}
