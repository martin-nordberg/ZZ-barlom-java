//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaConcreteComponent;
import org.grestler.domain.javamodel.api.elements.IJavaField;
import org.grestler.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

/**
 * A Java field.
 */
public final class JavaField
    extends JavaMember
    implements IJavaField {

    /**
     * Constructs a new field.
     */
    JavaField(
        JavaConcreteComponent parent,
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinal,
        IJavaType type,
        Optional<String> initialValueCode
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, type );

        this.initialValueCode = initialValueCode;

        parent.onAddChild( this );
    }

    /** Returns the initialValueCode. */
    @Override
    public Optional<String> getInitialValueCode() {
        return this.initialValueCode;
    }

    @Override
    public IJavaConcreteComponent getParent() {
        return (IJavaConcreteComponent) super.getParent();
    }

    private final Optional<String> initialValueCode;

}
