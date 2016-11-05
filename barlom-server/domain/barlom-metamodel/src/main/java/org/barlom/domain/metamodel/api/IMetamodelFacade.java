//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api;

import org.barlom.domain.metamodel.api.exceptions.MetamodelException;

import java.util.UUID;

/**
 * Interface for creating, deleting, and querying metamodel elements.
 */
public interface IMetamodelFacade {

    /**
     * Callback called withe found vertex type records.
     */
    @FunctionalInterface
    interface IVertexTypeQueryCallback {
        void handleVertexType( UUID uuid, String name );
    }

    /**
     * Deletes a vertex type.
     *
     * @param uuid the UUID of the vertex type to delete.
     *
     * @throws MetamodelException if the deletion fails.
     */
    void deleteVertexType( UUID uuid ) throws MetamodelException;

    /**
     * Finds a vertex type with given UUID.
     *
     * @param uuid     the UUID of the vertex type to find.
     * @param callback callback reciving the record found.
     *
     * @return one if found, zero if not.
     *
     * @throws MetamodelException if the query fails.
     */
    int findVertexTypeByUuid( UUID uuid, IVertexTypeQueryCallback callback ) throws MetamodelException;

    /**
     * Inserts or updates a new vertex type.
     *
     * @param uuid the unique ID of the vertex type.
     * @param name the name of the vertex type.
     *
     * @throws MetamodelException if the update fails.
     */
    void upsertVertexType( UUID uuid, String name ) throws MetamodelException;

}
