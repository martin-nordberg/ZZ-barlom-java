//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;
import java.util.Optional;

/**
 * Model element for the abstract attributes of a package.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface IJavaAbstractPackage
    extends IJavaNamedModelElement {

    /** Creates a package within this one. */
    @SuppressWarnings( "BooleanParameter" )
    IJavaPackage addPackage( String name, String description, boolean isImplicitlyImported );

    /** Given a qualified name relative to this package, find the needed component. */
    Optional<IJavaAnnotationInterface> findAnnotationInterface( String relativeQualifiedName );

    /** Given a qualified name relative to this package, find the needed component. */
    Optional<IJavaComponent> findComponent( String relativeQualifiedName );

    /** Given a qualified name relative to this package, find or create the needed subpackages. */
    IJavaPackage findOrCreatePackage( String relativeQualifiedName );

    /** Returns the fully qualified name of this package. */
    String getFullyQualifiedJavaName();

    /** Returns the packages within this package. */
    List<IJavaPackage> getPackages();

    @Override
    IJavaAbstractPackage getParent();

}
