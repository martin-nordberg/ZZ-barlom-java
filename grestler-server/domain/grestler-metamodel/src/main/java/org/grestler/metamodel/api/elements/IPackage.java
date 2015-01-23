//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.List;

/**
 * Interface to a package of vertex types and edge types.
 */
public interface IPackage
    extends IElement {

    /**
     * @return the packages that are children of this one.
     */
    List<IPackage> getChildPackages();

    /**
     * @return the Edge types that are children of this package.
     */
    List<IEdgeType> getEdgeTypes();

    /**
     * @return the vertex types that are children of this package.
     */
    List<IVertexType> getVertexTypes();

}
