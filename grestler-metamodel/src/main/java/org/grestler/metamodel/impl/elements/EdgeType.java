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
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
public final class EdgeType
    extends Element
    implements IEdgeType {

    /**
     * Constructs a new edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type.
     * @param tailVertexType the vertex type at the start of the edge type.
     * @param headVertexType the vertex type at the end of the edge type.
     */
    public EdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        IVertexType tailVertexType,
        IVertexType headVertexType
    ) {
        super( id, parentPackage, name );

        this.superType = new V<>( superType );
        this.tailVertexType = new V<>( tailVertexType );
        this.headVertexType = new V<>( headVertexType );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        super.generateJsonAttributes( json );
        json.write( "tailVertexTypeId", this.getTailVertexType().getId().toString() )
            .write( "headVertexTypeId", this.getHeadVertexType().getId().toString() );
    }

    @Override
    public IVertexType getHeadVertexType() {
        return this.headVertexType.get();
    }

    @Override
    public Optional<IEdgeType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    @Override
    public IVertexType getTailVertexType() {
        return this.tailVertexType.get();
    }

    /** The vertex type at the head of edges of this type. */
    private final V<IVertexType> headVertexType;

    /** The super type for this edge type. */
    private final V<IEdgeType> superType;

    /** The vertex type at the tail of edges of this type. */
    private final V<IVertexType> tailVertexType;

}
