//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * Mixin interface for elements that have a Java type.
 */
@SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
public interface IJavaTyped {

    /** @return the type of this element. */
    IJavaType getType();

}
