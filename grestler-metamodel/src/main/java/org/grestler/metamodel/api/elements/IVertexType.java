//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType {

    /**
     * @return the name of this vertex type.
     */
    String name();

    /**
     * @return the super type of this vertex type.
     */
    IVertexType superType();

}
