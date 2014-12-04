//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import java.util.Optional;
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
        this.name = new V<>( name );
        this.superType = new V<>( this );
    }

    /**
     * Constructs a new vertex type.
     * @param id the unique ID of the type.
     * @param name the name of the type.
     * @param superType the super type.
     */
    public VertexType( UUID id, String name, IVertexType superType ) {
        this.id = id;
        this.name = new V<>( name );
        this.superType = new V<>( superType );
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        if ( this.superType.get() == this ) {
            return Optional.empty();
        }
        return Optional.of( this.superType.get() );
    }

    @Override
    public void setName( String name ) {
        this.name.set( name );
    }

    @Override
    public void setSuperType( IVertexType superType ) {
        this.superType.set( superType );
    }

    private final UUID id;

    private final V<String> name;

    private final V<IVertexType> superType;

}
