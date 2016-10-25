//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

/**
 * A constructor
 */
public interface IJavaConstructor
    extends IJavaFunction {

    @Override
    IJavaConcreteComponent getParent();

}
