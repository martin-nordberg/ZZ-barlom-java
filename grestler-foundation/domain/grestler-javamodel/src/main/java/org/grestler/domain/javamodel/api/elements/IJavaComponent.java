//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;

/**
 * An interface, enum, or class.
 */
public interface IJavaComponent
    extends IJavaAnnotatableModelElement, IJavaTyped {

    /** Creates a method within this component. */
    @SuppressWarnings( "BooleanParameter" )
    IJavaMethod addMethod(
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isAbstract,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType,
        String code
    );

    /** @return the fully qualified name of this component. */
    String getFullyQualifiedJavaName();

    /** @return the interfaces implemented by this component. */
    List<IJavaImplementedInterface> getImplementedInterfaces();

    /** @return the methods within this component. */
    List<IJavaMethod> getMethods();

    /** @return the parent of this package. */
    @Override
    IJavaPackage getParent();

    /** Returns the isExternal. */
    boolean isExternal();

    /** Constructs a generic Java type with one type argument. */
    IJavaType makeGenericType( IJavaComponent typeArg1 );

}
