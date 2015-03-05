package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IPackagedElement;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.infrastructure.utilities.collections.ISizedIterable;
import org.grestler.infrastructure.utilities.revisions.VArray;

/**
 * Helper class for maintaining the contents of a package.
 */
final class PackageContents {

    /**
     * Constructs a new package elements helper instance.
     *
     * @param ownerPkg the package delegating to this helper class.
     */
    PackageContents( IPackage ownerPkg ) {

        this.ownerPkg = ownerPkg;

        this.attributeTypes = new VArray<>();
        this.childPackages = new VArray<>();
        this.edgeTypes = new VArray<>();
        this.vertexTypes = new VArray<>();

    }

    void addChildElement( IPackagedElement packagedElement ) {

        assert packagedElement.getParentPackage() == this.ownerPkg;

        if ( packagedElement instanceof IAttributeType ) {
            this.attributeTypes.add( (IAttributeType) packagedElement );
        }
        else if ( packagedElement instanceof IPackage ) {
            this.childPackages.add( (IPackage) packagedElement );
        }
        else if ( packagedElement instanceof IVertexType ) {
            this.vertexTypes.add( (IVertexType) packagedElement );
        }
        else if ( packagedElement instanceof IEdgeType ) {
            this.edgeTypes.add( (IEdgeType) packagedElement );
        }
        else {
            throw new IllegalArgumentException( "Unknown package element: " + packagedElement.getClass().getName() );
        }

    }

    ISizedIterable<IAttributeType> getAttributeTypes() {
        return this.attributeTypes;
    }

    ISizedIterable<IPackage> getChildPackages() {
        return this.childPackages;
    }

    ISizedIterable<IEdgeType> getEdgeTypes() {
        return this.edgeTypes;
    }

    ISizedIterable<IVertexType> getVertexTypes() {
        return this.vertexTypes;
    }

    void removeChildElement( IPackagedElement packagedElement ) {

        assert packagedElement.getParentPackage() == this.ownerPkg;

        if ( packagedElement instanceof IAttributeType ) {
            this.attributeTypes.remove( (IAttributeType) packagedElement );
        }
        else if ( packagedElement instanceof IPackage ) {
            this.childPackages.remove( (IPackage) packagedElement );
        }
        else if ( packagedElement instanceof IVertexType ) {
            this.vertexTypes.remove( (IVertexType) packagedElement );
        }
        else if ( packagedElement instanceof IEdgeType ) {
            this.edgeTypes.remove( (IEdgeType) packagedElement );
        }
        else {
            throw new IllegalArgumentException( "Unknown package element: " + packagedElement.getClass().getName() );
        }

    }

    /** The attribute types within this package. */
    private final VArray<IAttributeType> attributeTypes;

    /** The sub-packages of this package. */
    private final VArray<IPackage> childPackages;

    /** The edge types within this package. */
    private final VArray<IEdgeType> edgeTypes;

    /** The package using this helper object. */
    private final IPackage ownerPkg;

    /** The vertex types within this package. */
    private final VArray<IVertexType> vertexTypes;
}
