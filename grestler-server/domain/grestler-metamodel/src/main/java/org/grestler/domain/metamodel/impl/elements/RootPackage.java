//
// (C) Copyright 2015 Martin E. Nordberg III
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
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() ).writeNull( "parentPackageId" ).write( "name", "$" ).write( "path", "" );
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
        return this.packageContents.getVertexTypes();
    }

    @Override
    public boolean hasSupplierPackage( IPackage pkg, EDependencyDepth dependencyDepth ) {
        return this.packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    @Override
    public boolean isChildOf( IPackage parentPackage ) {
        return false;
    }

    @Override
    public void removeChildElement( IPackagedElement packagedElement ) {
        this.packageContents.removeChildElement( packagedElement );
    }

    /** The unique ID of this root package. */
    private final UUID id;

    /** Helper object manages the contents of this package. */
    private final PackageContents packageContents;

    /** Helper object that manages this package's dependencies. */
    private final PackageDependencies packageDependencies;

}
