//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A member.
 */
public interface IJavaMember
    extends IJavaTypedModelElement {

    /** @return the accessibility of this member. */
    EJavaAccessibility getAccessibility();

    /** @return the parent of this member. */
    @Override
    IJavaComponent getParent();

    /** @return whether this is a final member. */
    boolean isFinal();

    /** @return whether this is a static member. */
    boolean isStatic();

}
