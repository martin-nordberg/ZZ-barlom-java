//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaComponent;
import org.grestler.domain.javamodel.api.elements.IJavaMember;
import org.grestler.domain.javamodel.api.elements.IJavaType;

/**
 * A member.
 */
public abstract class JavaMember
    extends JavaTypedModelElement
    implements IJavaMember {

    /**
     * Constructs a new member.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected JavaMember(
        IJavaComponent parent,
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinal,
        IJavaType type
    ) {
        super( parent, name, description, type );

        this.accessibility = accessibility;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }

    /** @return the accessibility of this method. */
    @Override
    public EJavaAccessibility getAccessibility() {
        return this.accessibility;
    }

    /** @return the parent of this field. */
    @Override
    public IJavaComponent getParent() {
        return (IJavaComponent) super.getParent();
    }

    /** Returns the isFinal. */
    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    /** @return whether this is a static method. */
    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    private final EJavaAccessibility accessibility;

    private final boolean isFinal;

    private final boolean isStatic;

}
