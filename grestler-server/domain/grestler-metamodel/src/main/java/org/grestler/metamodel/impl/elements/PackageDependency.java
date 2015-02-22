//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for package dependencies.
 */
public final class PackageDependency
    extends DocumentedElement
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

        super( id );

        this.clientPackage = clientPackage;
        this.supplierPackage = supplierPackage;

        // Register both ends.
        ( (IPackageUnderAssembly) clientPackage ).addPackageDependency( this );
        ( (IPackageUnderAssembly) supplierPackage ).addPackageDependency( this );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "clientPackageId", this.clientPackage.getId().toString() )
            .write( "supplierPackageId", this.supplierPackage.getId().toString() );

    }

    @Override
    public IPackage getClientPackage() {
        return this.clientPackage;
    }

    @Override
    public IPackage getSupplierPackage() {
        return this.supplierPackage;
    }

    /** The package that makes use of the supplier. */
    private final IPackage clientPackage;

    /** The package depended upon. */
    private final IPackage supplierPackage;

}
