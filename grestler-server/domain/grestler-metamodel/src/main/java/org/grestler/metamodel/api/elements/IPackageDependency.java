//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

/**
 * Interface to a package dependency.
 */
public interface IPackageDependency
    extends IDocumentedElement {

    /**
     * @return the package that makes use of the supplier package.
     */
    IPackage getClientPackage();

    @Override
    default INamedElement getParent() {
        return this.getClientPackage();
    }

    /**
     * @return the package that is depended upon.
     */
    IPackage getSupplierPackage();

}
