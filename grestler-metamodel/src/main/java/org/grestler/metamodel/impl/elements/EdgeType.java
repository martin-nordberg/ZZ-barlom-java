//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
public class EdgeType
    implements IEdgeType {

    /**
     * Constructs a new vertex type.
     * @param id the unique ID of the type.
     * @param name the name of the type.
     * @param superType the super type.
     */
    public EdgeType( UUID id, String name, Optional<IEdgeType> superType, IVertexType fromVertexType, IVertexType toVertexType ) {

        Objects.requireNonNull( id, "Missing ID" );
        Objects.requireNonNull( name, "Missing name" );
        Objects.requireNonNull( fromVertexType, "Missing fromVertexType" );
        Objects.requireNonNull( toVertexType, "Missing toVertexType" );

        this.id = id;
        this.name = new V<>( name );
        if ( superType.isPresent() ) {
            this.superType = new V<>( superType.get() );
        }
        else {
            this.superType = new V<>( this );
        }
        this.fromVertexType = new V<>( fromVertexType );
        this.toVertexType = new V<>( toVertexType );

    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public IVertexType getFromVertexType() {
        return this.fromVertexType.get();
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public Optional<IEdgeType> getSuperType() {

        if ( this.superType.get() == this ) {
            return Optional.empty();
        }

        return Optional.of( this.superType.get() );

    }

    @Override
    public IVertexType getToVertexType() {
        return this.toVertexType.get();
    }

    /**
     * Changes the name of this vertex type.
     * @param name the new name (required).
     */
    public void setName( String name ) {

        Objects.requireNonNull( name, "Missing name" );

        this.name.set( name );

    }

    /**
     * Changes the super type of this vertex type.
     * @param superType the new super type.
     */
    public void setSuperType( Optional<IEdgeType> superType ) {

        if ( superType.isPresent() ) {
            this.superType.set( superType.get() );
        }
        else {
            this.superType.set( this );
        }

    }

    private final UUID id;

    private final V<String> name;

    private final V<IEdgeType> superType;

    private final V<IVertexType> fromVertexType;

    private final V<IVertexType> toVertexType;

}
