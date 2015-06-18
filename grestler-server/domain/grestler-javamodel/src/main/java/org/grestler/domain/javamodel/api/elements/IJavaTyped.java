//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * Interface for elements that have a Java type.
 */
@SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
public interface IJavaTyped {

    /** @return the type of this element. */
    IJavaType makeJavaType();

}
