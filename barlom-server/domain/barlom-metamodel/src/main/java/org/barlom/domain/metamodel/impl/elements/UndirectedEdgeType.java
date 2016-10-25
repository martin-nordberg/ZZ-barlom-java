//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.IEdgeType;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Implementation class for edge types.
 */
public final class UndirectedEdgeType
    extends EdgeType
    implements IUndirectedEdgeType {

    /**
     * Constructs a new edge type.
     *
     * @param record        the attributes of the edge type.
     * @param parentPackage the package containing the edge type.
     * @param superType     the super type.
     * @param vertexType    the vertex type for the edge type.
     */
    public UndirectedEdgeType(
        IUndirectedEdgeType.Record record, IPackage parentPackage, IUndirectedEdgeType superType, IVertexType vertexType
    ) {
        super( record, parentPackage );

        this.superType = new V<>( superType );
        this.vertexType = new V<>( vertexType );
        this.minDegree = new V<>( record.minDegree );
        this.maxDegree = new V<>( record.maxDegree );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "vertexTypeId", this.getVertexType().getId().toString() );

        this.getMinDegree().ifPresent( minDegree -> json.write( "minDegree", minDegree ) );
        this.getMaxDegree().ifPresent( maxDegree -> json.write( "maxDegree", maxDegree ) );
    }

    @Override
    public OptionalInt getMaxDegree() {
        return this.maxDegree.get();
    }

    @Override
    public OptionalInt getMinDegree() {
        return this.minDegree.get();
    }

    @Override
    public Optional<IEdgeType> getSuperEdgeType() {
        return Optional.of( this.superType.get() );
    }

    @Override
    public Optional<IUndirectedEdgeType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    @Override
    public IVertexType getVertexType() {
        return this.vertexType.get();
    }

    @Override
    public boolean isSubTypeOf( IUndirectedEdgeType edgeType ) {
        return this == edgeType || this.getSuperType().get().isSubTypeOf( edgeType );
    }

    /**
     * Changes the maximum degree for edges of this type.
     *
     * @param maxDegree the new maximum.
     */
    public void setMaxDegree( OptionalInt maxDegree ) {
        this.maxDegree.set( maxDegree );
    }

    /**
     * Changes the minimum degree for edges of this type.
     *
     * @param minDegree the new minimum.
     */
    public void setMinDegree( OptionalInt minDegree ) {
        this.minDegree.set( minDegree );
    }

    /**
     * Changes the type of vertex connected by edges of this type.
     *
     * @param vertexType the new vertex type.
     */
    public void setVertexType( IVertexType vertexType ) {
        this.vertexType.set( vertexType );
    }

    /** The maximum in-degree for the head vertex of edges of this type. */
    private final V<OptionalInt> maxDegree;

    /** The minimum in-degree for the head vertex of edges of this type. */
    private final V<OptionalInt> minDegree;

    /** The super type of this type. */
    private final V<IUndirectedEdgeType> superType;

    /** The vertex type for edges of this type. */
    private final V<IVertexType> vertexType;

}
