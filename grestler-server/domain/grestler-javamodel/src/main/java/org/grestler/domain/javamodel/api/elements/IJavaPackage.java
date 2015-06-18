//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;

/**
 * A package.
 */
public interface IJavaPackage
    extends IJavaAbstractPackage {

    /** Creates an annotation interface within this package. */
    IJavaAnnotationInterface addAnnotationInterface( String name, String description );

    /** Creates a class within this package. */
    @SuppressWarnings( "BooleanParameter" )
    IJavaClass addClass(
        String name, String description, boolean isAbstract, boolean isFinal, IJavaClass baseClass, boolean isTestCode
    );

    /** Creates an enumeration within this package. */
    @SuppressWarnings( "BooleanParameter" )
    IJavaEnumeration addEnumeration( String name, String description, boolean isExternal );

    /** Creates a class within this package only for reference by other classes. */
    IJavaClass addExternalClass( String name );

    /** Creates an interface within this package. */
    IJavaInterface addExternalInterface( String name );

    /** Creates an interface within this package. */
    IJavaInterface addInterface( String name, String description );

    /** Returns the annotation interfaces within this package. */
    List<IJavaAnnotationInterface> getAnnotationInterfaces();

    /** Returns the classes within this package. */
    List<IJavaClass> getClasses();

    /** Returns the components within this package. */
    List<IJavaComponent> getComponents();

    /** Returns the enumerations within this package. */
    List<IJavaEnumeration> getEnumerations();

    /** Returns the interfaces within this package. */
    List<IJavaInterface> getInterfaces();

    /** Returns the isImplicitlyImported. */
    boolean isImplicitlyImported();

}
