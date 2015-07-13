//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaConcreteComponent;
import org.grestler.domain.javamodel.api.elements.IJavaConstructor;
import org.grestler.domain.javamodel.api.elements.IJavaField;
import org.grestler.domain.javamodel.api.elements.IJavaMember;
import org.grestler.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

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
    JavaConcreteComponent( JavaPackage parent, String name, String description, boolean isExternal ) {
        super( parent, name, description, isExternal );

        this.constructors = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.staticInitializations = new ArrayList<>();

        parent.onAddChild( this );
    }

    /** Creates a constructor within this class. */
    @Override
    public IJavaConstructor addConstructor(
        String description, EJavaAccessibility accessibility, String code
    ) {
        return new JavaConstructor( this, description, accessibility, code );
    }

    /** Creates a field within this class. */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaField addField(
        String name,
        String description,
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

    /** Creates a static initialization within this class. */
    @Override
    public IJavaStaticInitialization addStaticInitialization( String description, String code ) {
        return new JavaStaticInitialization( this, description, code );
    }

    /** @return the constructors within this class. */
    @Override
    public IIndexable<IJavaConstructor> getConstructors() {
        return new ReadOnlyListAdapter<>( this.constructors );
    }

    /** @return the fields within this class. */
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

    /** @return the static initializations within this class. */
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
