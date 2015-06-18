//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A method.
 */
public interface IJavaMethod
    extends IJavaFunction {

    /** @return whether this is an abstract method. */
    boolean isAbstract();

}
