//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaMethod;
import org.grestler.domain.javamodel.api.elements.IJavaType;

/**
 * A method.
 */
public final class JavaMethod
    extends JavaFunction
    implements IJavaMethod {

    /**
     * Constructs a new method.
     */
    JavaMethod(
        JavaComponent parent,
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isAbstract,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType,
        String code
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, returnType, code );

        this.isAbstract = isAbstract;

        parent.onAddChild( this );
    }

    /** @return whether this is an abstract method. */
    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    private final boolean isAbstract;

}
