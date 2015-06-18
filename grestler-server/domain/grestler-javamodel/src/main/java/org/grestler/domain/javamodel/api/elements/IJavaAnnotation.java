//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * An annotation.
 */
public interface IJavaAnnotation
    extends IJavaModelElement {

    /** @return the annotationInterface. */
    IJavaAnnotationInterface getAnnotationInterface();

    /** @return the parametersCode. */
    String getParametersCode();

    @Override
    IJavaAnnotatableModelElement getParent();

}
