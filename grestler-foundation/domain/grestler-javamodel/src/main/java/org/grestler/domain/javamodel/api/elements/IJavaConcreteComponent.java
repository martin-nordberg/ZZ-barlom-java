//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

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
        Optional<String> initialValueCode
    );

    /** Creates a static initialization within this class. */
    IJavaStaticInitialization addStaticInitialization( String description, String code );

    /** @return the constructors within this class. */
    IIndexable<IJavaConstructor> getConstructors();

    /** @return the fields within this class. */
    IIndexable<IJavaField> getFields();

    /** @return the static initializations within this class. */
    IIndexable<IJavaStaticInitialization> getStaticInitializations();

}
