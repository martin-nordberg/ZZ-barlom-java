//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.grestler.domain.javamodel.api.elements.IJavaPackage;

/**
 * An annotation definition
 */
public class JavaAnnotationInterface
    extends JavaAnnotatableModelElement
    implements IJavaAnnotationInterface {

    /**
     * Constructs a new annotation
     */
    JavaAnnotationInterface( JavaPackage parent, String name, String description ) {
        super( parent, name, description );

        parent.onAddChild( this );
    }

    /** @return the fully qualified name of this component. */
    @Override
    public String getFullyQualifiedJavaName() {
        return this.getParent().getFullyQualifiedJavaName() + "." + this.getJavaName();
    }

    @Override
    public IJavaPackage getParent() {
        return (IJavaPackage) super.getParent();
    }

    /** @return a type referencing this component. */
    @Override
    public JavaType makeJavaType() {
        return new JavaAnnotationType( this );
    }

}
