//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

import java.util.Optional;

/**
 * An annotation.
 */
public interface IJavaAnnotation
    extends IJavaModelElement {

    /** @return the annotationInterface. */
    IJavaAnnotationInterface getAnnotationInterface();

    /** @return the parametersCode. */
    Optional<String> getParametersCode();

    @Override
    IJavaAnnotatableModelElement getParent();

}
