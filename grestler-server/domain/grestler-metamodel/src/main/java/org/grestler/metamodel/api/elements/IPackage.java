//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

/**
 * Interface to a package of vertex types and edge types.
 */
public interface IPackage
    extends IElement {

    /**
     * @return the packages that are children of this one.
     */
    Iterable<IPackage> getChildPackages();

    /**
     * @return the Edge types that are children of this package.
     */
    Iterable<IEdgeType> getEdgeTypes();

    /**
     * @return the vertex types that are children of this package.
     */
    Iterable<IVertexType> getVertexTypes();

}
