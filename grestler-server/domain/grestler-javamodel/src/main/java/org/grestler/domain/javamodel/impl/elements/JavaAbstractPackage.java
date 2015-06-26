//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaAbstractPackage;
import org.grestler.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.grestler.domain.javamodel.api.elements.IJavaComponent;
import org.grestler.domain.javamodel.api.elements.IJavaPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Model element for the abstract attributes of a package.
 */
public abstract class JavaAbstractPackage
    extends JavaNamedModelElement
    implements IJavaAbstractPackage {

    /**
     * Constructs a new abstract Java package (package or root package).
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected JavaAbstractPackage( IJavaAbstractPackage parent, String name, String description ) {
        super( parent, name, description );

        this.packages = new ArrayList<>();
    }

    /** Creates a package within this one. */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaPackage addPackage( String name, String description, boolean isImplicitlyImported ) {
        return new JavaPackage( this, name, description, isImplicitlyImported );
    }

    /** Given a qualified name relative to this package, find the needed component. */
    @Override
    public Optional<IJavaAnnotationInterface> findAnnotationInterface( String relativeQualifiedName ) {

        // split the relative path into first and rest
        String[] packageNames = relativeQualifiedName.split( "\\.", 2 );

        // look for an existing sub-package
        for ( IJavaPackage jpackage : this.packages ) {
            if ( jpackage.getJavaName().equals( packageNames[0] ) ) {
                return jpackage.findAnnotationInterface( packageNames[1] );
            }
        }

        return Optional.empty();
    }

    /** Given a qualified name relative to this package, find the needed component. */
    @Override
    public Optional<IJavaComponent> findComponent( String relativeQualifiedName ) {

        // split the relative path into first and rest
        String[] packageNames = relativeQualifiedName.split( "\\.", 2 );

        // look for an existing sub-package
        for ( IJavaPackage jpackage : this.packages ) {
            if ( jpackage.getJavaName().equals( packageNames[0] ) ) {
                return jpackage.findComponent( packageNames[1] );
            }
        }

        return Optional.empty();
    }

    /** Given a qualified name relative to this package, find or create the needed subpackages. */
    @Override
    public IJavaPackage findOrCreatePackage( String relativeQualifiedName ) {

        // return this package itself if we're at the bottom of the path
        if ( relativeQualifiedName.isEmpty() ) {
            return (IJavaPackage) this;
        }

        // split the relative path into first and rest
        String[] packageNames = relativeQualifiedName.split( "\\.", 2 );

        // look for an existing sub-package
        for ( IJavaPackage jpackage : this.packages ) {
            if ( jpackage.getJavaName().equals( packageNames[0] ) ) {
                if ( packageNames.length == 1 ) {
                    return jpackage;
                }
                return jpackage.findOrCreatePackage( packageNames[1] );
            }
        }

        // not found - create a new sub-package
        IJavaPackage newPackage = this.addPackage( packageNames[0], "", false );

        if ( packageNames.length == 1 ) {
            return newPackage;
        }
        return newPackage.findOrCreatePackage( packageNames[1] );
    }

    /** Returns the fully qualified name of this package. */
    @Override
    public String getFullyQualifiedJavaName() {
        String result = this.getParent().getFullyQualifiedJavaName();
        if ( !result.isEmpty() ) {
            result += ".";
        }
        result += this.getJavaName();
        return result;
    }

    /** Returns the packages within this package. */
    @Override
    public List<IJavaPackage> getPackages() {
        return this.packages;
    }

    /** @return the parent of this package. */
    @Override
    public IJavaAbstractPackage getParent() {
        return (IJavaAbstractPackage) super.getParent();
    }

    @Override
    public String toString() {
        return this.getFullyQualifiedJavaName();
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaPackage child ) {
        super.onAddChild( child );
        this.packages.add( child );
    }

    private final List<IJavaPackage> packages;

}