//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation class for Grestler packages.
 */
public final class Package
    extends Element
    implements IPackage, IPackageSpi {

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

        ( (IPackageSpi) parentPackage ).addChildPackage( this );

    }

    @Override
    public Iterable<IPackage> getChildPackages() {
        return this.childPackages;
    }

    @Override
    public Iterable<IEdgeType> getEdgeTypes() {
        return this.edgeTypes;
    }

    @Override
    public Iterable<IVertexType> getVertexTypes() {
        return this.vertexTypes;
    }

    @Override
    public void addEdgeType( IEdgeType edgeType ) {
        this.edgeTypes.add( edgeType );
    }

    @Override
    public void addVertexType( IVertexType vertexType ) {
        this.vertexTypes.add( vertexType );
    }

    @Override
    public void addChildPackage( IPackage pkg ) {
        this.childPackages.add( pkg );
    }

    /** The sub-packages of this package. */
    private final List<IPackage> childPackages;

    /** The edge types within this package. */
    private final List<IEdgeType> edgeTypes;

    /** The vertex types within this package. */
    private final List<IVertexType> vertexTypes;

}
