//
// (C) Copyright 2015 Martin E. Nordberg III
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

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation of the top level root package.
 */
public class RootPackage
    implements IPackage, IPackageUnderAssembly {

    /**
     * Constructs a new root package.
     *
     * @param id the unique ID of the root package.
     */
    public RootPackage( UUID id ) {

        this.id = id;

        this.childPackages = new VArray<>();
        this.edgeTypes = new VArray<>();
        this.vertexTypes = new VArray<>();

        this.packageDependencies = new PackageDependencies( this );

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
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() ).writeNull( "parentPackageId" ).write( "name", "$" ).write( "path", "" );
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
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return "$";
    }

    @Override
    public IPackage getParentPackage() {
        return this;
    }

    @Override
    public String getPath() {
        return "";
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

    @Override
    public boolean isChildOf( IPackage parentPackage ) {
        return false;
    }

    /** The sub-packages of this package. */
    private final VArray<IPackage> childPackages;

    /** The edge types within this package. */
    private final VArray<IEdgeType> edgeTypes;

    /** The unique ID of this root package. */
    private final UUID id;

    /** Helper class that manages this package's dependencies. */
    private final PackageDependencies packageDependencies;

    /** The vertex types within this package. */
    private final VArray<IVertexType> vertexTypes;

}
