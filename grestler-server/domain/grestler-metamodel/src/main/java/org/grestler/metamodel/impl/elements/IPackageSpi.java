//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;
import org.grestler.metamodel.api.elements.IVertexType;

/**
 * Internal interface supported by packages.
 */
interface IPackageSpi {

    /**
     * Adds a child package to this package.
     *
     * @param pkg the child package to add.
     */
    void addChildPackage( IPackage pkg );

    /**
     * Adds a child edge type that is part of this package.
     *
     * @param edgeType the edge type to add.
     */
    void addEdgeType( IEdgeType edgeType );

    /**
     * Registers a package dependency with this package.
     *
     * @param packageDependency the added package dependency.
     */
    void addPackageDependency( IPackageDependency packageDependency );

    /**
     * Adds a child vertex tyoe that is part of this package.
     *
     * @param vertexType the vertex type to add.
     */
    void addVertexType( IVertexType vertexType );

}
