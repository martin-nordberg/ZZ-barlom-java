//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.queries;

import org.grestler.domain.metamodel.api.elements.EElementSortOrder;
import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ISizedIterable;

import java.util.Optional;
import java.util.UUID;

/**
 * Central store of metamodel elements.
 */
@SuppressWarnings( "ClassWithTooManyMethods" )
public interface IMetamodelRepository {

    /**
     * Finds all attribute types in unspecified order.
     *
     * @return a list of all attribute types in the repository.
     */
    ISizedIterable<IAttributeType> findAllAttributeTypes();

    /**
     * Finds all attribute types in specified order.
     *
     * @param sortOrder the order in which to sort the results.
     *
     * @return a list of all attribute types in the repository.
     */
    IIndexable<IAttributeType> findAllAttributeTypesSorted( EElementSortOrder sortOrder );

    /**
     * Finds all directed edge types in unspecified order.
     *
     * @return a list of all directed edge types in the repository.
     */
    ISizedIterable<IDirectedEdgeType> findAllDirectedEdgeTypes();

    /**
     * Finds all directed edge types in specified order.
     *
     * @param sortOrder the order in which to sort the results.
     *
     * @return a list of all directed edge types in the repository.
     */
    IIndexable<IDirectedEdgeType> findAllDirectedEdgeTypesSorted( EElementSortOrder sortOrder );

    /**
     * Finds all edge types in unspecified order.
     *
     * @return a list of all edge types in the repository.
     */
    ISizedIterable<IEdgeType> findAllEdgeTypes();

    /**
     * Finds all edge types in specified order.
     *
     * @param sortOrder the order in which to sort the results.
     *
     * @return a list of all edge types in the repository.
     */
    IIndexable<IEdgeType> findAllEdgeTypesSorted( EElementSortOrder sortOrder );

    /**
     * Finds all packages sorted in unspecified order.
     *
     * @return a list of all packages in the repository.
     */
    ISizedIterable<IPackage> findAllPackages();

    /**
     * Finds all packages sorted in specified order.
     *
     * @param sortOrder the order in which to sort the results.
     *
     * @return a list of all packages in the repository.
     */
    IIndexable<IPackage> findAllPackagesSorted( EElementSortOrder sortOrder );

    /**
     * Finds all undirected edge types in unspecified order.
     *
     * @return a list of all undirected edge types in the repository.
     */
    ISizedIterable<IUndirectedEdgeType> findAllUndirectedEdgeTypes();

    /**
     * Finds all undirected edge types in specified order.
     *
     * @param sortOrder the order in which to sort the results.
     *
     * @return a list of all undirected edge types in the repository.
     */
    IIndexable<IUndirectedEdgeType> findAllUndirectedEdgeTypesSorted( EElementSortOrder sortOrder );

    /**
     * Finds all vertex types in unspecified order.
     *
     * @return a list of all vertex types in the repository.
     */
    ISizedIterable<IVertexType> findAllVertexTypes();

    /**
     * Finds all vertex types in specified order.
     *
     * @param sortOrder the order in which to sort the results.
     *
     * @return a list of all vertex types in the repository.
     */
    IIndexable<IVertexType> findAllVertexTypesSorted( EElementSortOrder sortOrder );

    /**
     * Finds the attribute type with given ID.
     *
     * @param id the UUID of the attribute type to find.
     *
     * @return the attribute type found.
     *
     * @throws org.grestler.domain.metamodel.api.exceptions.MetamodelException if the package is not found.
     */
    IAttributeType findAttributeTypeById( UUID id );

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
     * @throws org.grestler.domain.metamodel.api.exceptions.MetamodelException if the package is not found.
     */
    IPackage findPackageById( UUID id );

    /**
     * Finds the root directed edge type.
     *
     * @return the root edge type or empty if not loaded yet.
     */
    IDirectedEdgeType findRootDirectedEdgeType();

    /**
     * Finds the root package.
     *
     * @return the root package or empty if not loaded yet.
     */
    IPackage findRootPackage();

    /**
     * Finds the root undirected edge type.
     *
     * @return the root edge type or empty if not loaded yet.
     */
    IUndirectedEdgeType findRootUndirectedEdgeType();

    /**
     * Finds the root vertex type.
     *
     * @return the root vertex type or empty if not loaded yet.
     */
    IVertexType findRootVertexType();

    /**
     * Finds the undirected edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    IUndirectedEdgeType findUndirectedEdgeTypeById( UUID id );

    /**
     * Finds the vertex type with given ID.
     *
     * @param id the UUID of the vertex type to find.
     *
     * @return the vertex type found.
     */
    IVertexType findVertexTypeById( UUID id );

}
