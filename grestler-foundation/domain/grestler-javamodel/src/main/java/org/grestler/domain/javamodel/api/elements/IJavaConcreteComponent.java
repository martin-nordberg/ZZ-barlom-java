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
    default IJavaConstructor addConstructor(
        String description, EJavaAccessibility accessibility
    ) {
        return this.addConstructor( Optional.of( description ), accessibility );
    }

    /** Creates a constructor within this class. */
    IJavaConstructor addConstructor(
        Optional<String> description, EJavaAccessibility accessibility
    );

    /**
     * Creates a field within this class.
     *
     * @param name the field name.
     * @param type the type of the field.
     *
     * @return the newly created field
     */
    @SuppressWarnings( "BooleanParameter" )
    default IJavaField addField(
        String name,
        IJavaType type
    ) {
        return this.addField( name, Optional.empty(), EJavaAccessibility.PRIVATE, false, true, type, Optional.empty() );
    }

    /**
     * Creates a field within this class.
     *
     * @param name             the field name.
     * @param description      an optional description of the field.
     * @param accessibility    the public/protected/private accessibility of the field.
     * @param isStatic         whether the field is static.
     * @param isFinalField     whether the field is final.
     * @param type             the type of the field.
     * @param initialValueCode code for the initial value of the field.
     *
     * @return the newly created field
     */
    @SuppressWarnings( "BooleanParameter" )
    IJavaField addField(
        String name,
        Optional<String> description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinalField,
        IJavaType type,
        Optional<String> initialValueCode
    );

    /** Creates a static initialization within this class. */
    IJavaStaticInitialization addStaticInitialization( Optional<String> description );

    /** @return the constructors within this class. */
    IIndexable<IJavaConstructor> getConstructors();

    /** @return the fields within this class. */
    IIndexable<IJavaField> getFields();

    /** @return the static initializations within this class. */
    IIndexable<IJavaStaticInitialization> getStaticInitializations();

}
