//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * Top level Java element.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface IJavaModelElement {

    /** Returns the description. */
    String getDescription();

    /** Returns the parent. */
    IJavaNamedModelElement getParent();

    /**
     * @return The highest root package containing this model element.
     */
    IJavaRootPackage getRootPackage();

}
