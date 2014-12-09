//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api;

import org.grestler.metamodel.api.elements.IVertexType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Central store of metamodel elements.
 */
public interface IMetamodelRepository {

    /**
     * Finds the vertex type with given ID.
     * @param id the UUID of the vertex type to find.
     * @return the vertex type found.
     */
    Optional<IVertexType> findVertexTypeById( UUID id );

    /**
     * Finds the vertex type with given name.
     * @param name the name of the vertex type to find.
     * @return the vertex type found.
     */
    Optional<IVertexType> findVertexTypeByName( String name );

    /**
     * @return a list of all vertex types in the repository.
     */
    List<IVertexType> findVertexTypesAll();

}
