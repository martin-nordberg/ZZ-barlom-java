//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.EJavaAccessibility;
import org.barlom.domain.javamodel.api.elements.IJavaMethod;
import org.barlom.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

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
        Optional<String> description,
        EJavaAccessibility accessibility,
        boolean isAbstract,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, returnType );

        this.isAbstract = isAbstract;

        parent.onAddChild( this );
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    private final boolean isAbstract;

}
