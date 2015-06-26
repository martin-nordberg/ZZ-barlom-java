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

    /**
     * @return The description of this model element.
     */
    String getDescription();

    /**
     * @return The parent of this model element.
     */
    IJavaNamedModelElement getParent();

    /**
     * @return The highest root package containing this model element.
     */
    IJavaRootPackage getRootPackage();

}
