//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.EDependencyDepth;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IPackageDependency;
import org.barlom.infrastructure.utilities.collections.ISizedIterable;
import org.barlom.infrastructure.utilities.collections.ReadOnlyCollectionAdapter;
import org.barlom.infrastructure.utilities.revisions.VArray;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Implementation class for Barlom package dependency graph management.
 */
final class PackageDependencies {

    /**
     * Constructs a new package dependencies helper.
     */
    PackageDependencies( IPackage ownerPkg ) {

        this.ownerPkg = ownerPkg;

        this.clientPackages = new VArray<>();
        this.supplierPackages = new VArray<>();

    }

    /**
     * Adds a package dependency related to the owner package.
     *
     * @param packageDependency the added dependency.
     */
    void addPackageDependency( IPackageDependency packageDependency ) {
        if ( this.ownerPkg == packageDependency.getClientPackage() ) {
            this.supplierPackages.add( packageDependency.getSupplierPackage() );
        }
        else {
            assert this.ownerPkg == packageDependency.getSupplierPackage();
            this.clientPackages.add( packageDependency.getClientPackage() );
        }
    }

    // TODO: removePackageDependency

    /**
     * Gets the packages that are clients of this package.
     *
     * @param dependencyDepth whether to include transitive clients.
     *
     * @return the collection of packages from the dependency graph.
     */
    ISizedIterable<IPackage> getClientPackages( EDependencyDepth dependencyDepth ) {

        if ( dependencyDepth.isTransitive() ) {
            Collection<IPackage> result = new TreeSet<>( ( p1, p2 ) -> p1.getId().compareTo( p2.getId() ) );
            this.clientPackages.forEach(
                p -> {
                    if ( result.add( p ) ) {
                        p.getClientPackages( dependencyDepth ).forEach( result::add );
                    }
                }
            );
            return new ReadOnlyCollectionAdapter<>( result );
        }

        return this.clientPackages;

    }

    /**
     * Gets the packages that are suppliers of the owner package.
     *
     * @param dependencyDepth whether to include transitive dependencies.
     *
     * @return the collection of packages found.
     */
    ISizedIterable<IPackage> getSupplierPackages( EDependencyDepth dependencyDepth ) {

        if ( dependencyDepth.isTransitive() ) {
            Collection<IPackage> result = new TreeSet<>( ( p1, p2 ) -> p1.getId().compareTo( p2.getId() ) );
            this.supplierPackages.forEach(
                p -> {
                    if ( result.add( p ) ) {
                        p.getClientPackages( dependencyDepth ).forEach( result::add );
                    }
                }
            );
            return new ReadOnlyCollectionAdapter<>( result );
        }

        return this.supplierPackages;

    }

    /**
     * Whether the package that owns this helper instance depends on the given package.
     *
     * @param pkg             the package to look for.
     * @param dependencyDepth whether to include transitive dependencies.
     *
     * @return true if the given package is a supplier.
     */
    boolean hasSupplierPackage( IPackage pkg, EDependencyDepth dependencyDepth ) {

        if ( dependencyDepth.isTransitive() ) {
            for ( IPackage pkg2 : this.supplierPackages ) {
                if ( pkg == pkg2 ) {
                    return true;
                }
                if ( pkg2 != this.ownerPkg && pkg2.hasSupplierPackage( pkg, dependencyDepth ) ) {
                    return true;
                }
            }
        }
        else {
            for ( IPackage pkg2 : this.supplierPackages ) {
                if ( pkg == pkg2 ) {
                    return true;
                }
            }
        }

        return false;

    }

    /** The packages that depend upon this one. */
    private final VArray<IPackage> clientPackages;

    /** The package that delegates to this helper class. */
    private final IPackage ownerPkg;

    /** The packages that this one depends upon. */
    private final VArray<IPackage> supplierPackages;

}
