//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A reference type.
 */
public interface IJavaReferenceType
    extends IJavaType {

    /** Adds a type argument to this type. */
    void addTypeArgument( IJavaComponent typeArg );

}
