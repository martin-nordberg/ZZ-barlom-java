//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
public final class UndirectedEdgeType
    extends EdgeType
    implements IUndirectedEdgeType {

    /**
     * Constructs a new edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type.
     * @param abstractness   whether the edge type is abstract or concrete.
     * @param cyclicity      whether the edge type is constrained to be acyclic.
     * @param multiEdgedness whether the edge type is constrained to disallow multiple edges between two given
     *                       vertexes.
     * @param selfLooping    whether the edge type disallows edges from a vertex to itself.
     * @param vertexType     the vertex type for the edge type.
     * @param minDegree      the minimum degree for the any vertex of an edge of this type.
     * @param maxDegree      the maximum degree for the any vertex of an edge of this type.
     */
    public UndirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IUndirectedEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping,
        IVertexType vertexType,
        OptionalInt minDegree,
        OptionalInt maxDegree
    ) {
        super( id, parentPackage, name, abstractness, cyclicity, multiEdgedness, selfLooping );

        this.superType = new V<>( superType );
        this.vertexType = new V<>( vertexType );
        this.minDegree = new V<>( minDegree );
        this.maxDegree = new V<>( maxDegree );

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
