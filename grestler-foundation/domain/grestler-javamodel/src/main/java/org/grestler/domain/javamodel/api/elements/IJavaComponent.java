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

    /**
     * Creates a method within this component.
     * @param name the name of the method.
     * @param description a description of the method.
     * @param accessibility the accessibility of the method.
     * @param isAbstract whether the new method is abstract.
     * @param isStatic whether the new method is static.
     * @param isFinal whether the new method is final.
     * @param returnType the return type of the method.
     * @param code the textual code of the method.
     * @return the newly created method.
     */
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

    /** @return whether this component is external (i.e. referenced but not generated by this model). */
    boolean isExternal();

    /**
     * Constructs a generic Java type with one type argument.
     * @param typeArg1 the type argument for the new type.
     * @return the newly created type.
     */
    IJavaType makeGenericType( IJavaComponent typeArg1 );

}
