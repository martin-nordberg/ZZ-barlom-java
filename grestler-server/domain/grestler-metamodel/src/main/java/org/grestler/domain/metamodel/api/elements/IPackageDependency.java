//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.UUID;

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

    class Record
        extends IDocumentedElement.Record {

        public Record( UUID id, UUID clientPackageId, UUID supplierPackageId ) {
            super( id );

            this.clientPackageId = clientPackageId;
            this.supplierPackageId = supplierPackageId;
        }

        public final UUID clientPackageId;

        public final UUID supplierPackageId;

    }
}
