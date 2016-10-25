//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.barlom.domain.javamodel.api.elements.IJavaPackage;

import java.util.Optional;

/**
 * An annotation definition
 */
public class JavaAnnotationInterface
    extends JavaAnnotatableModelElement
    implements IJavaAnnotationInterface {

    /**
     * Constructs a new annotation
     */
    JavaAnnotationInterface( JavaPackage parent, String name, Optional<String> description ) {
        super( parent, name, description );

        parent.onAddChild( this );
    }

    @Override
    public String getFullyQualifiedJavaName() {
        return this.getParent().getFullyQualifiedJavaName() + "." + this.getJavaName();
    }

    @Override
    public IJavaPackage getParent() {
        return (IJavaPackage) super.getParent();
    }

    @Override
    public JavaType getType() {
        return new JavaAnnotationType( this );
    }

}
