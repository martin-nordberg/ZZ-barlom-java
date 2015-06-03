//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.EDependencyDepth;
import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IPackageDependency;
import org.grestler.domain.metamodel.api.elements.IPackagedElement;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.infrastructure.utilities.collections.ISizedIterable;

/**
 * Implementation class for Grestler packages.
 */
public final class Package
    extends PackagedElement
    implements IPackage, IPackageUnderAssembly {

    /**
     * Constructs a new package.
     *
     * @param record        the attributes of the package.
     * @param parentPackage the parent package.
     */
    public Package( IPackage.Record record, IPackage parentPackage ) {

        super( record, parentPackage );

        this.packageDependencies = new PackageDependencies( this );
        this.packageContents = new PackageContents( this );

    }

    @Override
    public void addChildElement( IPackagedElement packagedElement ) {
        this.packageContents.addChildElement( packagedElement );
    }

    @Override
    public void addPackageDependency( IPackageDependency packageDependency ) {
        this.packageDependencies.addPackageDependency( packageDependency );
    }

    @Override
    public ISizedIterable<IAttributeType> getAttributeTypes() {
        return this.packageContents.getAttributeTypes();
    }

    @Override
    public ISizedIterable<IPackage> getChildPackages() {
        return this.packageContents.getChildPackages();
    }

    @Override
    public ISizedIterable<IPackage> getClientPackages( EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.getClientPackages( dependencyDepth );
    }

    @Override
    public ISizedIterable<IEdgeType> getEdgeTypes() {
        return this.packageContents.getEdgeTypes();
    }

    @Override
    public ISizedIterable<IPackage> getSupplierPackages( EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.getSupplierPackages( dependencyDepth );
    }

    @Override
    public ISizedIterable<IVertexType> getVertexTypes() {
        return this.packageContents.getVertexTypes();
    }

    @Override
    public boolean hasSupplierPackage( IPackage pkg, EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    @Override
    public void removeChildElement( IPackagedElement packagedElement ) {
        this.packageContents.removeChildElement( packagedElement );
    }

    /** Helper object that manages this package's contents. */
    private final PackageContents packageContents;

    /** Helper object that manages this package's dependencies. */
    private final PackageDependencies packageDependencies;

}
