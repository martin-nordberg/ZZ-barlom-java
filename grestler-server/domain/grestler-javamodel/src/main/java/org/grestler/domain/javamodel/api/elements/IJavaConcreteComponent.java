//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;

/**
 * A concrete component.
 */
public interface IJavaConcreteComponent
    extends IJavaComponent {

    /** Creates a constructor within this class. */
    IJavaConstructor addConstructor(
        String description, EJavaAccessibility accessibility, String code
    );

    /** Creates a field within this class. */
    @SuppressWarnings( "BooleanParameter" )
    IJavaField addField(
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinalField,
        IJavaType type,
        String code
    );

    /** Creates a static initialization within this class. */
    IJavaStaticInitialization addStaticInitialization( String description, String code );

    /** @return the constructors within this class. */
    List<IJavaConstructor> getConstructors();

    /** @return the fields within this class. */
    List<IJavaField> getFields();

    /** @return the static initializations within this class. */
    List<IJavaStaticInitialization> getStaticInitializations();

}
