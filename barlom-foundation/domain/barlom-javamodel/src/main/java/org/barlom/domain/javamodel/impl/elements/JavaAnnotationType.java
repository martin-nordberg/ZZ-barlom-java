//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.barlom.domain.javamodel.api.elements.IJavaAnnotationType;

/**
 * An annotation type.
 */
public final class JavaAnnotationType
    extends JavaType
    implements IJavaAnnotationType {

    /**
     * Constructs a new type reference.
     *
     * @param annotationInterface The referenced annotation interface defining this type
     */
    JavaAnnotationType( IJavaAnnotationInterface annotationInterface ) {
        super();

        this.annotationInterface = annotationInterface;
    }

    @Override
    public String getFullyQualifiedJavaName() {
        return this.annotationInterface.getFullyQualifiedJavaName();
    }

    @Override
    public String getJavaName() {
        return this.annotationInterface.getJavaName();
    }

    @Override
    public boolean isImplicitlyImported() {
        return this.annotationInterface.getParent().isImplicitlyImported();
    }

    private final IJavaAnnotationInterface annotationInterface;

}
