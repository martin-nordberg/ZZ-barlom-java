//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for vertex types.
 */
public class VertexType
    implements IVertexType {

    /**
     * Constructs a new vertex type.
     *
     * @param id            the unique ID of the type.
     * @param parentPackage the package containing the edge type.
     * @param name          the name of the type.
     * @param superType     the super type.
     */
    public VertexType(
        UUID id, IPackage parentPackage, String name, IVertexType superType
    ) {

        Objects.requireNonNull( id, "Missing ID" );

        // TODO: unique name per package

        this.id = id;
        this.parentPackage = new V<>( parentPackage );
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
    public IPackage getParentPackage() {
        return this.parentPackage.get();
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    /**
     * Changes the name of this vertex type.
     *
     * @param name the new name (required).
     */
    public void setName( String name ) {

        Objects.requireNonNull( name, "Missing name" );

        this.name.set( name );

    }

    /**
     * Changes the super type of this vertex type.
     *
     * @param superType the new super type.
     */
    public void setSuperType( IVertexType superType ) {

        this.superType.set( superType );

    }

    private final UUID id;

    private final V<String> name;

    private final V<IPackage> parentPackage;

    private final V<IVertexType> superType;

}
