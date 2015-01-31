//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;

import java.util.UUID;

/**
 * Implementation class for package dependencies.
 */
public final class PackageDependency
    implements IPackageDependency {

    /**
     * Constructs a package dependency.
     *
     * @param id              the unique ID of the package dependency.
     * @param clientPackage   the package making use of the supplier package.
     * @param supplierPackage the package that is depended upon.
     */
    public PackageDependency(
        UUID id, IPackage clientPackage, IPackage supplierPackage
    ) {
        this.id = id;
        this.clientPackage = clientPackage;
        this.supplierPackage = supplierPackage;

        // TOD: register both ends
    }

    // TODO: JSON

    @Override
    public IPackage getClientPackage() {
        return this.clientPackage;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public IPackage getSupplierPackage() {
        return this.supplierPackage;
    }

    /** The package that makes use of the supplier. */
    private final IPackage clientPackage;

    /** The unique ID of this attribute declaration. */
    private final UUID id;

    /** The package depended upon. */
    private final IPackage supplierPackage;

}
