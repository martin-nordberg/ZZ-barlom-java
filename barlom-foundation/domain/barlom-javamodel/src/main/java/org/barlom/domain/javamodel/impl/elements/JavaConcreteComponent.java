//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.EJavaAccessibility;
import org.barlom.domain.javamodel.api.elements.IJavaConcreteComponent;
import org.barlom.domain.javamodel.api.elements.IJavaConstructor;
import org.barlom.domain.javamodel.api.elements.IJavaField;
import org.barlom.domain.javamodel.api.elements.IJavaMember;
import org.barlom.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.barlom.domain.javamodel.api.elements.IJavaType;
import org.barlom.infrastructure.utilities.collections.IIndexable;
import org.barlom.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A concrete component.
 */
public abstract class JavaConcreteComponent
    extends JavaComponent
    implements IJavaConcreteComponent {

    /**
     * Constructs a new concrete component.
     */
    JavaConcreteComponent( JavaPackage parent, String name, Optional<String> description, boolean isExternal ) {
        super( parent, name, description, isExternal );

        this.constructors = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.staticInitializations = new ArrayList<>();

        parent.onAddChild( this );
    }

    @Override
    public IJavaConstructor addConstructor(
        Optional<String> description, EJavaAccessibility accessibility
    ) {
        return new JavaConstructor( this, description, accessibility );
    }

    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaField addField(
        String name,
        Optional<String> description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinalField,
        IJavaType type,
        Optional<String> initialValueCode
    ) {
        return new JavaField(
            this, name, description, accessibility, isStatic, isFinalField, type, initialValueCode
        );
    }

    @Override
    public IJavaStaticInitialization addStaticInitialization( Optional<String> description ) {
        return new JavaStaticInitialization( this, description );
    }

    @Override
    public IIndexable<IJavaConstructor> getConstructors() {
        return new ReadOnlyListAdapter<>( this.constructors );
    }

    @Override
    public IIndexable<IJavaField> getFields() {
        List<IJavaField> result = new ArrayList<>( this.fields );
        Collections.sort(
            result, ( IJavaMember f1, IJavaMember f2 ) -> {
                int cresult = Boolean.compare( f2.isStatic(), f1.isStatic() );
                if ( cresult == 0 ) {
                    cresult = f1.getAccessibility().compareTo( f2.getAccessibility() );
                }
                if ( cresult == 0 ) {
                    cresult = f1.compareTo( f2 );
                }
                return cresult;
            }
        );
        return new ReadOnlyListAdapter<>( result );
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        for ( IJavaField field : this.fields ) {
            result.addAll( field.getImports() );
        }

        return result;
    }

    @Override
    public IIndexable<IJavaStaticInitialization> getStaticInitializations() {
        return new ReadOnlyListAdapter<>( this.staticInitializations );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaConstructor child ) {
        super.onAddChild( child );
        this.constructors.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaField child ) {
        super.onAddChild( child );
        this.fields.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaStaticInitialization child ) {
        super.onAddChild( child );
        this.staticInitializations.add( child );
    }

    private final List<IJavaConstructor> constructors;

    private final List<IJavaField> fields;

    private final List<IJavaStaticInitialization> staticInitializations;

}
