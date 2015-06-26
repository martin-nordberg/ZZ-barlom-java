//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.grestler.domain.javamodel.api.elements.IJavaClass;
import org.grestler.domain.javamodel.api.elements.IJavaComponent;
import org.grestler.domain.javamodel.api.elements.IJavaEnumeration;
import org.grestler.domain.javamodel.api.elements.IJavaInterface;
import org.grestler.domain.javamodel.api.elements.IJavaPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A package.
 */
public final class JavaPackage
    extends JavaAbstractPackage
    implements IJavaPackage {

    /**
     * Constructs a new Java package.
     */
    JavaPackage(
        JavaAbstractPackage parent, String name, String description, boolean isImplicitlyImported
    ) {
        super( parent, name, description );

        this.isImplicitlyImported = isImplicitlyImported;

        this.classes = new ArrayList<>();
        this.components = new ArrayList<>();
        this.enumerations = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.annotationInterfaces = new ArrayList<>();

        parent.onAddChild( this );
    }

    /** Creates an annotation interface within this package. */
    @Override
    public IJavaAnnotationInterface addAnnotationInterface( String name, String description ) {
        return new JavaAnnotationInterface( this, name, description );
    }

    /** Creates a class within this package. */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaClass addClass(
        String name, String description, boolean isAbstract, boolean isFinal, IJavaClass baseClass, boolean isTestCode
    ) {
        return new JavaClass(
            this, name, description, false, isAbstract, isFinal, baseClass, isTestCode
        );
    }

    /** Creates an enumeration within this package. */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaEnumeration addEnumeration( String name, String description, boolean isExternal ) {
        return new JavaEnumeration( this, name, description, isExternal );
    }

    /** Creates a class within this package only for reference by other classes. */
    @Override
    public IJavaClass addExternalClass( String name ) {
        return new JavaClass( this, name, "", true, false, false, null, false );
    }

    /** Creates an interface within this package. */
    @Override
    public IJavaInterface addExternalInterface( String name ) {
        return new JavaInterface( this, name, "", true );
    }

    /** Creates an interface within this package. */
    @Override
    public IJavaInterface addInterface( String name, String description ) {
        return new JavaInterface( this, name, description, false );
    }

    /** Given a qualified name relative to this package, find the needed component. */
    @Override
    public Optional<IJavaAnnotationInterface> findAnnotationInterface( String relativeQualifiedName ) {

        // split the relative path into first and rest
        if ( relativeQualifiedName.indexOf( '.' ) > 0 ) {
            return super.findAnnotationInterface( relativeQualifiedName );
        }

        // look for an existing component
        for ( IJavaAnnotationInterface annotationInterface : this.annotationInterfaces ) {
            if ( annotationInterface.getJavaName().equals( relativeQualifiedName ) ) {
                return Optional.of( annotationInterface );
            }
        }

        return Optional.empty();
    }

    /** Given a qualified name relative to this package, find the needed component. */
    @Override
    public Optional<IJavaComponent> findComponent( String relativeQualifiedName ) {

        // split the relative path into first and rest
        if ( relativeQualifiedName.indexOf( '.' ) > 0 ) {
            return super.findComponent( relativeQualifiedName );
        }

        // look for an existing component
        for ( IJavaComponent component : this.components ) {
            if ( component.getJavaName().equals( relativeQualifiedName ) ) {
                return Optional.of( component );
            }
        }

        return Optional.empty();
    }

    /** Returns the annotation interfaces within this package. */
    @Override
    public List<IJavaAnnotationInterface> getAnnotationInterfaces() {
        return this.annotationInterfaces;
    }

    /** Returns the classes within this package. */
    @Override
    public List<IJavaClass> getClasses() {
        return this.classes;
    }

    /** Returns the components within this package. */
    @Override
    public List<IJavaComponent> getComponents() {
        return this.components;
    }

    /** Returns the enumerations within this package. */
    @Override
    public List<IJavaEnumeration> getEnumerations() {
        return this.enumerations;
    }

    /** Returns the interfaces within this package. */
    @Override
    public List<IJavaInterface> getInterfaces() {
        return this.interfaces;
    }

    /** Returns the isImplicitlyImported. */
    @Override
    public boolean isImplicitlyImported() {
        return this.isImplicitlyImported;
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaAnnotationInterface child ) {
        super.onAddChild( child );
        this.annotationInterfaces.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaClass child ) {
        this.onAddChild( (IJavaComponent) child );
        this.classes.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaComponent child ) {
        super.onAddChild( child );
        this.components.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaEnumeration child ) {
        this.onAddChild( (IJavaComponent) child );
        this.enumerations.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaInterface child ) {
        this.onAddChild( (IJavaComponent) child );
        this.interfaces.add( child );
    }

    private final List<IJavaAnnotationInterface> annotationInterfaces;

    private final List<IJavaClass> classes;

    private final List<IJavaComponent> components;

    private final List<IJavaEnumeration> enumerations;

    private final List<IJavaInterface> interfaces;

    private final boolean isImplicitlyImported;

}
