//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.EJavaAccessibility;
import org.barlom.domain.javamodel.api.elements.IJavaConcreteComponent;
import org.barlom.domain.javamodel.api.elements.IJavaConstructor;

import java.util.Optional;

/**
 * A constructor
 */
public class JavaConstructor
    extends JavaFunction
    implements IJavaConstructor {

    /**
     * Constructs a new constructor.
     */
    JavaConstructor(
        JavaConcreteComponent parent, Optional<String> description, EJavaAccessibility accessibility
    ) {
        super(
            parent,
            parent.getName(),
            description,
            accessibility,
            false,
            false,
            parent.getRootPackage().getBuiltinVoid()
        );

        parent.onAddChild( this );
    }

    @Override
    public IJavaConcreteComponent getParent() {
        return (IJavaConcreteComponent) super.getParent();
    }

}
