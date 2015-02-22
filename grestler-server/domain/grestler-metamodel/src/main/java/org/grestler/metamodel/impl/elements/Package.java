//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EDependencyDepth;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.collections.ISizedIterable;
import org.grestler.utilities.revisions.VArray;

import java.util.UUID;

/**
 * Implementation class for Grestler packages.
 */
public final class Package
    extends PackagedElement
    implements IPackage, IPackageUnderAssembly {

    /**
     * Constructs a new package.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package.
     * @param name          the name of the package.
     */
    public Package( UUID id, IPackage parentPackage, String name ) {

        super( id, parentPackage, name );

        this.childPackages = new VArray<>();
        this.edgeTypes = new VArray<>();
        this.vertexTypes = new VArray<>();

        this.packageDependencies = new PackageDependencies( this );

        ( (IPackageUnderAssembly) parentPackage ).addChildPackage( this );

    }

    @Override
    public void addChildPackage( IPackage pkg ) {
        this.childPackages.add( pkg );
    }

    @Override
    public void addEdgeType( IEdgeType edgeType ) {
        this.edgeTypes.add( edgeType );
    }

    @Override
    public void addPackageDependency( IPackageDependency packageDependency ) {
        this.packageDependencies.addPackageDependency( packageDependency );
    }

    @Override
    public void addVertexType( IVertexType vertexType ) {
        this.vertexTypes.add( vertexType );
    }

    @Override
    public ISizedIterable<IPackage> getChildPackages() {
        return this.childPackages;
    }

    @Override
    public ISizedIterable<IPackage> getClientPackages( EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.getClientPackages( dependencyDepth );
    }

    @Override
    public ISizedIterable<IEdgeType> getEdgeTypes() {
        return this.edgeTypes;
    }

    @Override
    public ISizedIterable<IPackage> getSupplierPackages( EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.getSupplierPackages( dependencyDepth );
    }

    @Override
    public ISizedIterable<IVertexType> getVertexTypes() {
        return this.vertexTypes;
    }

    @Override
    public boolean hasSupplierPackage( IPackage pkg, EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    /** The sub-packages of this package. */
    private final VArray<IPackage> childPackages;

    /** The edge types within this package. */
    private final VArray<IEdgeType> edgeTypes;

    /** Helper class that manages this package's dependencies. */
    private final PackageDependencies packageDependencies;

    /** The vertex types within this package. */
    private final VArray<IVertexType> vertexTypes;

}
