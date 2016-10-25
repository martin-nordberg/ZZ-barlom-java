//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IPackageDependency;
import org.barlom.domain.metamodel.api.elements.IPackagedElement;

/**
 * Internal interface supported by packages.
 */
interface IPackageUnderAssembly {

    /**
     * Adds a child element to this package.
     *
     * @param packagedElement the child element to add.
     */
    void addChildElement( IPackagedElement packagedElement );

    /**
     * Registers a package dependency with this package.
     *
     * @param packageDependency the added package dependency.
     */
    void addPackageDependency( IPackageDependency packageDependency );

    /**
     * Removes a child element from this package.
     *
     * @param packagedElement the child element to remove.
     */
    void removeChildElement( IPackagedElement packagedElement );

}
