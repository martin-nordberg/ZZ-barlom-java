//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.queries;

import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;
import java.util.UUID;

/**
 * Central store of metamodel elements.
 */
@SuppressWarnings( "ClassWithTooManyMethods" )
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
