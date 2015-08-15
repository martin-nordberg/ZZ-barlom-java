//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.Objects;
import java.util.UUID;

/**
 * Shared general interface for metadata elements that are direct children of packages.
 */
public interface IPackagedElement
    extends INamedElement {

    /**
     * Data structure for packaged element records.
     */
    class Record
        extends INamedElement.Record {

        protected Record( UUID id, UUID parentPackageId, String name ) {
            super( id, name );
            Objects.requireNonNull( parentPackageId );
            this.parentPackageId = parentPackageId;
        }

        public final UUID parentPackageId;

    }

    @Override
    default INamedElement getParent() {
        return this.getParentPackage();
    }

    /**
     * @return the parent package of this packaged element.
     */
    @SuppressWarnings( "ClassReferencesSubclass" )
    IPackage getParentPackage();

    /**
     * Determines whether this package is a direct or indirect child of the given package.
     *
     * @param parentPackage the potential parent.
     *
     * @return true if this package is a child or grandchild of the given parent package.
     */
    @SuppressWarnings( "ClassReferencesSubclass" )
    default boolean isChildOf( IPackage parentPackage ) {

        IPackage parentPkg = this.getParentPackage();

        return parentPkg == parentPackage || parentPkg.isChildOf( parentPackage );

    }

}
