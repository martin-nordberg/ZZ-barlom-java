//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.UUID;

/**
 * Interface to a package dependency.
 */
public interface IPackageDependency {

    /**
     * @return the package that makes use of the supplier package.
     */
    IPackage getClientPackage();

    /**
     * @return the unique ID of this dependency.
     */
    UUID getId();

    /**
     * @return the package that is depended upon.
     */
    IPackage getSupplierPackage();

}
