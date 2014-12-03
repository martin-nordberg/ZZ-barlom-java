//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.UUID;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType {

    /**
     * @return the unique ID of the vertex type.
     */
    UUID getId();

    /**
     * @return the name of this vertex type.
     */
    String getName();

    /**
     * @return the super type of this vertex type.
     */
    IVertexType getSuperType();

}
