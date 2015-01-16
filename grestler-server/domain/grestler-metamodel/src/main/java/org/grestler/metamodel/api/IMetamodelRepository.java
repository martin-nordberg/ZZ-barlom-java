//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api;

import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import java.util.List;
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
    List<IAttributeType> findAttributeTypesAll();

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
    List<IEdgeType> findEdgeTypesAll();

    /**
     * Finds the package with given ID.
     *
     * @param id the UUID of the package to find.
     *
     * @return the package found.
     */
    Optional<IPackage> findPackageById( UUID id );

    /**
     * @return a list of all packages in the repository.
     */
    List<IPackage> findPackagesAll();

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
    List<IVertexType> findVertexTypesAll();

}
