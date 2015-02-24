//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.queries;

import org.grestler.metamodel.api.elements.IAttributeType;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.collections.IIndexable;

import java.util.Optional;
import java.util.UUID;

/**
 * Central store of metamodel elements.
 */
public interface IMetamodelRepository {

    /**
     * Finds the attribute type with given ID.
     *
     * @param id the UUID of the attribute type to find.
     *
     * @return the attribute type found.
     */
    Optional<IAttributeType> findAttributeTypeById( UUID id );

    /**
     * @return a list of all attribute types in the repository.
     */
    IIndexable<IAttributeType> findAttributeTypesAll();

    /**
     * Finds the base directed edge type.
     *
     * @return the base edge type or empty if not loaded yet.
     */
    Optional<IDirectedEdgeType> findDirectedEdgeTypeBase();

    /**
     * Finds the directed edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    Optional<IDirectedEdgeType> findDirectedEdgeTypeById( UUID id );

    /**
     * @return a list of all directed edge types in the repository.
     */
    IIndexable<IDirectedEdgeType> findDirectedEdgeTypesAll();

    /**
     * Finds the edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    Optional<IEdgeType> findEdgeTypeById( UUID id );

    /**
     * @return a list of all edge types in the repository.
     */
    IIndexable<IEdgeType> findEdgeTypesAll();

    /**
     * Finds the package with given ID.
     *
     * @param id the UUID of the package to find.
     *
     * @return the package found.
     */
    Optional<IPackage> findPackageById( UUID id );

    /**
     * Finds the root package.
     *
     * @return the root package or empty if not loaded yet.
     */
    Optional<IPackage> findPackageRoot();

    /**
     * @return a list of all packages in the repository.
     */
    IIndexable<IPackage> findPackagesAll();

    /**
     * Finds the base undirected edge type.
     *
     * @return the base edge type or empty if not loaded yet.
     */
    Optional<IUndirectedEdgeType> findUndirectedEdgeTypeBase();

    /**
     * Finds the undirected edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    Optional<IUndirectedEdgeType> findUndirectedEdgeTypeById( UUID id );

    /**
     * @return a list of all undirected edge types in the repository.
     */
    IIndexable<IUndirectedEdgeType> findUndirectedEdgeTypesAll();

    /**
     * Finds the base vertex type.
     *
     * @return the base vertex type or empty if not loaded yet.
     */
    Optional<IVertexType> findVertexTypeBase();

    /**
     * Finds the vertex type with given ID.
     *
     * @param id the UUID of the vertex type to find.
     *
     * @return the vertex type found.
     */
    Optional<IVertexType> findVertexTypeById( UUID id );

    /**
     * @return a list of all vertex types in the repository.
     */
    IIndexable<IVertexType> findVertexTypesAll();

}
