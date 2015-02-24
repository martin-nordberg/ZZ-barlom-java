//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import org.grestler.utilities.collections.ISizedIterable;

/**
 * Interface to a package of vertex types and edge types.
 */
public interface IPackage
    extends IPackagedElement {

    /**
     * @return the attribute types that are children of this package.
     */
    ISizedIterable<IAttributeType> getAttributeTypes();

    /**
     * @return the packages that are children of this one.
     */
    ISizedIterable<IPackage> getChildPackages();

    /**
     * Determines the packages that depend upon this one.
     *
     * @param dependencyDepth whether to include indirect clients.
     *
     * @return the packages that depend upon this package.
     */
    ISizedIterable<IPackage> getClientPackages( EDependencyDepth dependencyDepth );

    /**
     * @return the edge types that are children of this package.
     */
    ISizedIterable<IEdgeType> getEdgeTypes();

    /**
     * Determines the packages that this package depends upon.
     *
     * @param dependencyDepth whether to include indirect dependencies.
     *
     * @return the packages that this package depends upon.
     */
    ISizedIterable<IPackage> getSupplierPackages( EDependencyDepth dependencyDepth );

    /**
     * @return the vertex types that are children of this package.
     */
    ISizedIterable<IVertexType> getVertexTypes();

    /**
     * Whether this package depends upon the given one (directly or indirectly).
     *
     * @param pkg             the package to check.
     * @param dependencyDepth whether to consider indirect suppliers.
     *
     * @return true if this package depends upon the given one.
     */
    boolean hasSupplierPackage( IPackage pkg, EDependencyDepth dependencyDepth );

}
