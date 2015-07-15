//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaAnnotatableModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaAnnotation;
import org.grestler.domain.javamodel.api.elements.IJavaAnnotationInterface;

import java.util.Optional;

/**
 * An annotation
 */
public final class JavaAnnotation
    extends JavaModelElement
    implements IJavaAnnotation {

    /**
     * Constructs a new annotation (usage).
     */
    JavaAnnotation(
        JavaAnnotatableModelElement parent,
        Optional<String> description,
        IJavaAnnotationInterface annotationInterface,
        String parametersCode
    ) {
        super( parent, description );

        this.annotationInterface = annotationInterface;
        this.parametersCode = parametersCode;

        parent.onAddChild( this );
    }

    @Override
    public IJavaAnnotationInterface getAnnotationInterface() {
        return this.annotationInterface;
    }

    @Override
    public String getParametersCode() {
        return this.parametersCode;
    }

    @Override
    public IJavaAnnotatableModelElement getParent() {
        return (IJavaAnnotatableModelElement) super.getParent();
    }

    private final IJavaAnnotationInterface annotationInterface;

    private final String parametersCode;

}