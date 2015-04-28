//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/api/queries
 */

import elements = require( './elements' );
import values = require( '../../../infrastructure/utilities/values' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Central store of metamodel elements.
 */
export interface IMetamodelRepository {

    /**
     * @return a list of all attribute types in the repository.
     */
    findAllAttributeTypes() : elements.IAttributeType[];

    /**
     * @return a list of all directed edge types in the repository.
     */
    findAllDirectedEdgeTypes() : elements.IDirectedEdgeType[];

    /**
     * @return a list of all edge types in the repository.
     */
    findAllEdgeTypes() : elements.IEdgeType[];

    /**
     * @return a list of all packages in the repository.
     */
    findAllPackages() : elements.IPackage[];

    /**
     * @return a list of all undirected edge types in the repository.
     */
    findAllUndirectedEdgeTypes() : elements.IUndirectedEdgeType[];

    /**
     * @return a list of all vertex types in the repository.
     */
    findAllVertexTypes() : elements.IVertexType[];

    /**
     * Finds the attribute type with given ID.
     *
     * @param id the UUID of the attribute type to find.
     *
     * @return the attribute type found.
     *
     * @throws org.grestler.domain.metamodel.api.exceptions.MetamodelException if the package is not found.
     */
    findAttributeTypeById( id : string ) : elements.IAttributeType;

    /**
     * Finds the directed edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    findDirectedEdgeTypeById( id : string ) : elements.IDirectedEdgeType;

    /**
     * Finds the edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    findEdgeTypeById( id : string ) : elements.IEdgeType;

    /**
     * Finds the attribute type with given ID.
     *
     * @param id the UUID of the attribute type to find.
     *
     * @return the attribute type found.
     */
    findOptionalAttributeTypeById( id : string ) : elements.IAttributeType;

    /**
     * Finds the directed edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    findOptionalDirectedEdgeTypeById( id : string ) : elements.IDirectedEdgeType;

    /**
     * Finds the edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    findOptionalEdgeTypeById( id : string ) : elements.IEdgeType;

    /**
     * Finds the package with given ID.
     *
     * @param id the UUID of the package to find.
     *
     * @return the package found.
     */
    findOptionalPackageById( id : string ) : elements.IPackage;

    /**
     * Finds the undirected edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    findOptionalUndirectedEdgeTypeById( id : string ) : elements.IUndirectedEdgeType;

    /**
     * Finds the vertex type with given ID.
     *
     * @param id the UUID of the vertex type to find.
     *
     * @return the vertex type found.
     */
    findOptionalVertexTypeById( id : string ) : elements.IVertexType;

    /**
     * Finds the package with given ID.
     *
     * @param id the UUID of the package to find.
     *
     * @return the package found.
     *
     * @throws org.grestler.domain.metamodel.api.exceptions.MetamodelException if the package is not found.
     */
    findPackageById( id : string ) : elements.IPackage;

    /**
     * Finds the packaged element with given ID.
     *
     * @param id the UUID of the element to find.
     *
     * @return the element found.
     *
     * @throws org.grestler.domain.metamodel.api.exceptions.MetamodelException if the element is not found.
     */
    findPackagedElementById( id : string ) : elements.IPackagedElement;

    /**
     * Finds the root directed edge type.
     *
     * @return the root edge type or empty if not loaded yet.
     */
    findRootDirectedEdgeType() : elements.IDirectedEdgeType;

    /**
     * Finds the root package.
     *
     * @return the root package or empty if not loaded yet.
     */
    findRootPackage() : elements.IPackage;

    /**
     * Finds the root undirected edge type.
     *
     * @return the root edge type or empty if not loaded yet.
     */
    findRootUndirectedEdgeType() : elements.IUndirectedEdgeType;

    /**
     * Finds the root vertex type.
     *
     * @return the root vertex type or empty if not loaded yet.
     */
    findRootVertexType() : elements.IVertexType;

    /**
     * Finds the undirected edge type with given ID.
     *
     * @param id the UUID of the edge type to find.
     *
     * @return the edge type found.
     */
    findUndirectedEdgeTypeById( id : string ) : elements.IUndirectedEdgeType;

    /**
     * Finds the vertex type with given ID.
     *
     * @param id the UUID of the vertex type to find.
     *
     * @return the vertex type found.
     */
    findVertexTypeById( id : string ) : elements.IVertexType;

    /**
     * Promise fulfilled when the repository has been fully loaded.
     */
    loaded : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

