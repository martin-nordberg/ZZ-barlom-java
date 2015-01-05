//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
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
     * @param id             the unique ID of the type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the type.
     * @param superType      the super type.
     * @param fromVertexType the vertex type at the start of the edge type.
     * @param toVertexType   the vertex type at the end of the edge type.
     */
    public EdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        IVertexType fromVertexType,
        IVertexType toVertexType
    ) {

        Objects.requireNonNull( id, "Missing ID" );

        // TODO: unique name per package

        this.id = id;
        this.parentPackage = new V<>( parentPackage );
        this.name = new V<>( name );
        this.superType = new V<>( superType );
        this.fromVertexType = new V<>( fromVertexType );
        this.toVertexType = new V<>( toVertexType );

    }

    @Override
    public void generateJson( JsonGenerator json ) {
        json.writeStartObject()
            .write( "id", this.getId().toString() )
            .write( "parentPackageId", this.getParentPackage().getId().toString() )
            .write( "name", this.getName() )
            .write( "path", this.getPath() )
            .write( "fromVertexTypeId", this.getFromVertexType().getId().toString() )
            .write( "toVertexTypeId", this.getToVertexType().getId().toString() )
            .writeEnd();
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
    public IPackage getParentPackage() {
        return this.parentPackage.get();
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

        // TODO: unique per parent package

        Objects.requireNonNull( name, "Missing name" );

        this.name.set( name );

    }

    /**
     * Changes the parent package of this edge type.
     *
     * @param parentPackage the new parent package (required).
     */
    public void setParentPackage( IPackage parentPackage ) {

        // TODO: unique per parent package

        Objects.requireNonNull( parentPackage, "Missing parentPackage" );

        this.parentPackage.set( parentPackage );

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

    private final V<IPackage> parentPackage;

    private final V<IEdgeType> superType;

    private final V<IVertexType> toVertexType;

}
