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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

        this.childPackages = new ArrayList<>();
        this.edgeTypes = new ArrayList<>();
        this.vertexTypes = new ArrayList<>();

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
    public List<IPackage> getChildPackages() {
        return this.childPackages;
    }

    @Override
    public Collection<IPackage> getClientPackages( EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.getClientPackages( dependencyDepth );
    }

    @Override
    public List<IEdgeType> getEdgeTypes() {
        return this.edgeTypes;
    }

    @Override
    public Collection<IPackage> getSupplierPackages( EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.getSupplierPackages( dependencyDepth );
    }

    @Override
    public List<IVertexType> getVertexTypes() {
        return this.vertexTypes;
    }

    @Override
    public boolean hasSupplierPackage( IPackage pkg, EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    /** The sub-packages of this package. */
    private final List<IPackage> childPackages;

    /** The edge types within this package. */
    private final List<IEdgeType> edgeTypes;

    /** Helper class that manages this package's dependencies. */
    private final PackageDependencies packageDependencies;

    /** The vertex types within this package. */
    private final List<IVertexType> vertexTypes;

}
