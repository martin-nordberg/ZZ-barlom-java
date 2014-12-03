//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IVertexType;

import java.util.UUID;

/**
 * Implementation class for vertex types.
 */
public class VertexType
    implements IVertexType {

    /**
     * Constructs a new top level vertex type.
     * @param id the unique ID of the type.
     * @param name the name of the type.
     */
    public VertexType( UUID id, String name ) {
        this.id = id;
        this.name = name;
        this.superType = this;
    }

    /**
     * Constructs a new vertex type.
     * @param id the unique ID of the type.
     * @param name the name of the type.
     * @param superType the super type.
     */
    public VertexType( UUID id, String name, IVertexType superType ) {
        this.id = id;
        this.name = name;
        this.superType = superType;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IVertexType getSuperType() {
        return this.superType;
    }

    private final UUID id;

    private final String name;

    private final IVertexType superType;

}
