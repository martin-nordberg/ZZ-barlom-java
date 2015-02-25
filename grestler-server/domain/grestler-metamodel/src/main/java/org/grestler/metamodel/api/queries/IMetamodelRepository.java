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
     * @return a list of all attribute types in the repository.
     */
    IIndexable<IAttributeType> findAllAttributeTypes();

    /**
     * @return a list of all directed edge types in the repository.
     */
    IIndexable<IDirectedEdgeType> findAllDirectedEdgeTypes();

    /**
     * @return a list of all edge types in the repository.
     */
    IIndexable<IEdgeType> findAllEdgeTypes();

    /**
     * @return a list of all packages in the repository.
     */
    IIndexable<IPackage> findAllPackages();

    /**
     * @return a list of all undirected edge types in the repository.
     */
    IIndexable<IUndirectedEdgeType> findAllUndirectedEdgeTypes();

    /**
     * @return a list of all vertex types in the repository.
     */
    IIndexable<IVertexType> findAllVertexTypes();

    /**
     * Finds the attribute type with given ID.
     *
     * @param id the UUID of the attribute type to find.
     *
     * @return the attribute type found.
     *
     * @throws org.grestler.metamodel.api.exceptions.MetamodelException if the package is not found.
     */
    IAttributeType findAttributeTypeById( UUID id );

    /**
     * Finds the base directed edge type.
     *
     * @return the base edge type or empty if not loaded yet.
     */
    IDirectedEdgeType findDirectedEdgeTypeBase();

    /**
     * Finds the directed edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    IDirectedEdgeType findDirectedEdgeTypeById( UUID id );

    /**
     * Finds the edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    IEdgeType findEdgeTypeById( UUID id );

    /**
     * Finds the attribute type with given ID.
     *
     * @param id the UUID of the attribute type to find.
     *
     * @return the attribute type found.
     */
    Optional<IAttributeType> findOptionalAttributeTypeById( UUID id );

    /**
     * Finds the directed edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    Optional<IDirectedEdgeType> findOptionalDirectedEdgeTypeById( UUID id );

    /**
     * Finds the edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    Optional<IEdgeType> findOptionalEdgeTypeById( UUID id );

    /**
     * Finds the package with given ID.
     *
     * @param id the UUID of the package to find.
     *
     * @return the package found.
     */
    Optional<IPackage> findOptionalPackageById( UUID id );

    /**
     * Finds the undirected edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    Optional<IUndirectedEdgeType> findOptionalUndirectedEdgeTypeById( UUID id );

    /**
     * Finds the vertex type with given ID.
     *
     * @param id the UUID of the vertex type to find.
     *
     * @return the vertex type found.
     */
    Optional<IVertexType> findOptionalVertexTypeById( UUID id );

    /**
     * Finds the package with given ID.
     *
     * @param id the UUID of the package to find.
     *
     * @return the package found.
     *
     * @throws org.grestler.metamodel.api.exceptions.MetamodelException if the package is not found.
     */
    IPackage findPackageById( UUID id );

    /**
     * Finds the root package.
     *
     * @return the root package or empty if not loaded yet.
     */
    IPackage findRootPackage();

    /**
     * Finds the base undirected edge type.
     *
     * @return the base edge type or empty if not loaded yet.
     */
    IUndirectedEdgeType findUndirectedEdgeTypeBase();

    /**
     * Finds the undirected edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    IUndirectedEdgeType findUndirectedEdgeTypeById( UUID id );

    /**
     * Finds the base vertex type.
     *
     * @return the base vertex type or empty if not loaded yet.
     */
    IVertexType findVertexTypeBase();

    /**
     * Finds the vertex type with given ID.
     *
     * @param id the UUID of the vertex type to find.
     *
     * @return the vertex type found.
     */
    IVertexType findVertexTypeById( UUID id );

}
