//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A Java field.
 */
public interface IJavaField
    extends IJavaMember {

    /** Returns the initialValueCode. */
    String getInitialValueCode();

    @Override
    IJavaConcreteComponent getParent();

}
