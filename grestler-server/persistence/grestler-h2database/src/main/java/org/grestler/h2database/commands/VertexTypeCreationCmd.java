//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.commands;

import org.grestler.metamodel.api.elements.IVertexType;

/**
 * Command to create a vertex type.
 */
public class VertexTypeCreationCmd {

    /**
     * Constructs a new vertex type creation command.
     */
    public VertexTypeCreationCmd(){}

    /**
     * Saves a newly created vertex type to the database.
     *
     * @param vertexType the vertex type to save.
     */
    public void createVertexType( IVertexType vertexType ) {

//        this.database.withVoidTransaction(
//            tx -> {
//
//                if ( vertexType.getSuperType().isPresent() ) {
//                    this.database.update(
//                        "INSERT INTO GRESTLER_VERTEX_TYPE (ID, NAME, SUPER_TYPE_ID) VALUES (?, ?, ?)",
//                        vertexType.getId(),
//                        vertexType.getName(),
//                        vertexType.getSuperType().get().getId()
//                    );
//                }
//                else {
//                    this.database.update(
//                        "INSERT INTO GRESTLER_VERTEX_TYPE (ID, NAME, SUPER_TYPE_ID) VALUES (?, ?, ?)",
//                        vertexType.getId(),
//                        vertexType.getName(),
//                        vertexType.getId()
//                    );
//                }
//
//                // TODO: create the corresponding table
//
//                // TODO: log the action
//
//            }
//        );

    }

}
