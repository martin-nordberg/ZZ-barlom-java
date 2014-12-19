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
     * Constructs a new edge type.
     *
     * @param id        the unique ID of the type.
     * @param name      the name of the type.
     * @param superType the super type.
     */
    public EdgeType(
        UUID id, String name, IEdgeType superType, IVertexType fromVertexType, IVertexType toVertexType
    ) {

        Objects.requireNonNull( id, "Missing ID" );
        Objects.requireNonNull( name, "Missing name" );
        Objects.requireNonNull( fromVertexType, "Missing fromVertexType" );
        Objects.requireNonNull( toVertexType, "Missing toVertexType" );

        this.id = id;
        this.name = new V<>( name );
        this.superType = new V<>( superType );
        this.fromVertexType = new V<>( fromVertexType );
        this.toVertexType = new V<>( toVertexType );

    }

    @Override
    public IVertexType getFromVertexType() {
        return this.fromVertexType.get();
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
    public Optional<IEdgeType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    @Override
    public IVertexType getToVertexType() {
        return this.toVertexType.get();
    }

    /**
     * Changes the name of this edge type.
     *
     * @param name the new name (required).
     */
    public void setName( String name ) {

        Objects.requireNonNull( name, "Missing name" );

        this.name.set( name );

    }

    /**
     * Changes the super type of this edge type.
     *
     * @param superType the new super type.
     */
    public void setSuperType( IEdgeType superType ) {
        this.superType.set( superType );
    }

    private final V<IVertexType> fromVertexType;

    private final UUID id;

    private final V<String> name;

    private final V<IEdgeType> superType;

    private final V<IVertexType> toVertexType;

}
