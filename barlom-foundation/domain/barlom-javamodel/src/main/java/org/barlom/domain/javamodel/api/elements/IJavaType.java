//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

/**
 * A Java type.
 */
public interface IJavaType
    extends IJavaModelElement, Comparable<IJavaType> {

    /** Returns the fully qualified name of this type. */
    String getFullyQualifiedJavaName();

    /** Returns the name of this element for Java code purposes. */
    String getJavaName();

    /** Whether this type is implicitly imported. */
    boolean isImplicitlyImported();

}
