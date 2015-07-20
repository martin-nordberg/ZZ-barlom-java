//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * Model element for the abstract attributes of a package.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface IJavaAbstractPackage
    extends IJavaNamedModelElement {

    /**
     * Creates a package within this one (empty description, not implicitly imported).
     *
     * @param name the name of the new package.
     *
     * @return the newly created package.
     */
    default IJavaPackage addPackage( String name ) {
        return this.addPackage( name, Optional.empty(), false );
    }

    /**
     * Creates a package within this one (empty description, not implicitly imported).
     *
     * @param name        the name of the new package.
     * @param description a description of the new package.
     *
     * @return the newly created package.
     */
    default IJavaPackage addPackage( String name, String description ) {
        return this.addPackage( name, Optional.of( description ), false );
    }

    /**
     * Creates a package within this one.
     *
     * @param name                 the name of the new package.
     * @param description          a description of the new package.
     * @param isImplicitlyImported true if the package needs no import (like java.lang).
     *
     * @return the newly created package.
     */
    @SuppressWarnings( "BooleanParameter" )
    IJavaPackage addPackage( String name, Optional<String> description, boolean isImplicitlyImported );

    /**
     * Given a qualified name relative to this package, find the needed annotation interface.
     *
     * @param relativeQualifiedName the relative name for the annotation interface to find.
     *
     * @return the found annotation interface.
     */
    Optional<IJavaAnnotationInterface> findAnnotationInterface( String relativeQualifiedName );

    /**
     * Given a qualified name relative to this package, find the needed component.
     *
     * @param relativeQualifiedName the relative name for the component within this package.
     *
     * @return the component found.
     */
    Optional<IJavaComponent> findComponent( String relativeQualifiedName );

    /**
     * Given a qualified name relative to this package, find or create the needed subpackages.
     *
     * @param relativeQualifiedName the relative name of the package to find or create if it does not exist.
     *
     * @return the found or created package.
     */
    IJavaPackage findOrCreatePackage( String relativeQualifiedName );

    /** @return the fully qualified name of this package. */
    String getFullyQualifiedJavaName();

    /** @return the packages within this package. */
    IIndexable<IJavaPackage> getPackages();

    @Override
    IJavaAbstractPackage getParent();

}
