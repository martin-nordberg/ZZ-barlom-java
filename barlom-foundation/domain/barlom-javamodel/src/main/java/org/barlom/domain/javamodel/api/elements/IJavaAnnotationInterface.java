//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

/**
 * An annotation definition
 */
public interface IJavaAnnotationInterface
    extends IJavaAnnotatableModelElement, IJavaTyped {

    /** @return the fully qualified name of this component. */
    String getFullyQualifiedJavaName();

    @Override
    IJavaPackage getParent();

}
