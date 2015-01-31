//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EDependencyDepth;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Implementation class for Grestler package dependency graph management.
 */
final class PackageDependencies {

    /**
     * Constructs a new package dependencies helper.
     */
    PackageDependencies( IPackage ownerPkg ) {

        this.ownerPkg = ownerPkg;

        this.clientPackages = new ArrayList<>();
        this.supplierPackages = new ArrayList<>();

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

    /**
     * Gets the packages that are clients of this package.
     *
     * @param dependencyDepth whether to include transitive clients.
     *
     * @return the collection of packages from the dependency graph.
     */
    Collection<IPackage> getClientPackages( EDependencyDepth dependencyDepth ) {

        if ( dependencyDepth.isTransitive() ) {
            Collection<IPackage> result = new TreeSet<>( ( p1, p2 ) -> p1.getId().compareTo( p2.getId() ) );
            this.clientPackages.forEach(
                p -> {
                    if ( result.add( p ) ) {
                        result.addAll( p.getClientPackages( dependencyDepth ) );
                    }
                }
            );
            return result;
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
    Collection<IPackage> getSupplierPackages( EDependencyDepth dependencyDepth ) {

        if ( dependencyDepth.isTransitive() ) {
            Collection<IPackage> result = new TreeSet<>( ( p1, p2 ) -> p1.getId().compareTo( p2.getId() ) );
            this.supplierPackages.forEach(
                p -> {
                    if ( result.add( p ) ) {
                        result.addAll( p.getSupplierPackages( dependencyDepth ) );
                    }
                }
            );
            return result;
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
    private final List<IPackage> clientPackages;

    /** The package that delegates to this helper class. */
    private final IPackage ownerPkg;

    /** The packages that this one depends upon. */
    private final List<IPackage> supplierPackages;

}
