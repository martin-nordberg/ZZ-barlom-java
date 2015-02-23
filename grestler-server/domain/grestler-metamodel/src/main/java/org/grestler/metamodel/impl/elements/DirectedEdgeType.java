//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
public final class DirectedEdgeType
    extends EdgeType
    implements IDirectedEdgeType {

    /**
     * Constructs a new edge type.
     *
     * @param id               the unique ID of the edge type.
     * @param parentPackage    the package containing the edge type.
     * @param name             the name of the edge type.
     * @param superType        the super type.
     * @param abstractness     whether the edge type is abstract or concrete.
     * @param cyclicity        whether the edge type is constrained to be acyclic.
     * @param multiEdgedness   whether the edge type is constrained to disallow multiple edges between two given
     *                         vertexes.
     * @param selfLooping      whether the edge type disallows edges from a vertex to itself.
     * @param tailVertexType   the vertex type at the start of the edge type.
     * @param headVertexType   the vertex type at the end of the edge type.
     * @param tailRoleName     the role name for vertexes at the tail of this edge type
     * @param headRoleName     the role name for vertexes at the head of this edge type
     * @param minTailOutDegree the minimum out-degree for the tail vertex of an edge of this type.
     * @param maxTailOutDegree the maximum out-degree for the tail vertex of an edge of this type.
     * @param minHeadInDegree  the minimum in-degree for the head vertex of an edge of this type.
     * @param maxHeadInDegree  the maximum in-degree for the head vertex of an edge of this type.
     */
    public DirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        // TODO: should be IDirectedEdgeType
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping,
        IVertexType tailVertexType,
        IVertexType headVertexType,
        Optional<String> tailRoleName,
        Optional<String> headRoleName,
        OptionalInt minTailOutDegree,
        OptionalInt maxTailOutDegree,
        OptionalInt minHeadInDegree,
        OptionalInt maxHeadInDegree
    ) {
        super( id, parentPackage, name, superType, abstractness, cyclicity, multiEdgedness, selfLooping );

        this.tailVertexType = new V<>( tailVertexType );
        this.headVertexType = new V<>( headVertexType );
        this.tailRoleName = new V<>( tailRoleName );
        this.headRoleName = new V<>( headRoleName );
        this.minTailOutDegree = new V<>( minTailOutDegree );
        this.maxTailOutDegree = new V<>( maxTailOutDegree );
        this.minHeadInDegree = new V<>( minHeadInDegree );
        this.maxHeadInDegree = new V<>( maxHeadInDegree );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "tailVertexTypeId", this.getTailVertexType().getId().toString() )
            .write( "headVertexTypeId", this.getHeadVertexType().getId().toString() );

        this.getTailRoleName().ifPresent( tailRoleName -> json.write( "tailRoleName", tailRoleName ) );
        this.getHeadRoleName().ifPresent( headRoleName -> json.write( "headRoleName", headRoleName ) );
        this.getMinTailOutDegree().ifPresent( minTailOutDegree -> json.write( "minTailOutDegree", minTailOutDegree ) );
        this.getMaxTailOutDegree().ifPresent( maxTailOutDegree -> json.write( "maxTailOutDegree", maxTailOutDegree ) );
        this.getMinHeadInDegree().ifPresent( minHeadInDegree -> json.write( "minHeadInDegree", minHeadInDegree ) );
        this.getMaxHeadInDegree().ifPresent( maxHeadInDegree -> json.write( "maxHeadInDegree", maxHeadInDegree ) );

    }

    @Override
    public Optional<String> getHeadRoleName() {
        return this.headRoleName.get();
    }

    @Override
    public IVertexType getHeadVertexType() {
        return this.headVertexType.get();
    }

    @Override
    public OptionalInt getMaxHeadInDegree() {
        return this.maxHeadInDegree.get();
    }

    @Override
    public OptionalInt getMaxTailOutDegree() {
        return this.maxTailOutDegree.get();
    }

    @Override
    public OptionalInt getMinHeadInDegree() {
        return this.minHeadInDegree.get();
    }

    @Override
    public OptionalInt getMinTailOutDegree() {
        return this.minTailOutDegree.get();
    }

    @Override
    public Optional<String> getTailRoleName() {
        return this.tailRoleName.get();
    }

    @Override
    public IVertexType getTailVertexType() {
        return this.tailVertexType.get();
    }

    /**
     * Updates the head role name for this edge type.
     *
     * @param headRoleName the new name for the role of the vertex at the head of edges of this type.
     */
    public void setHeadRoleName( Optional<String> headRoleName ) {
        this.headRoleName.set( headRoleName );
    }

    /**
     * Changes the vertex type for the head of this edge type.
     *
     * @param headVertexType the new head vertex type.
     */
    public void setHeadVertexType( IVertexType headVertexType ) {
        this.headVertexType.set( headVertexType );
    }

    /**
     * Changes the maximum head in-degree for edges of this type.
     *
     * @param maxHeadInDegree the new head in-degree limit.
     */
    public void setMaxHeadInDegree( OptionalInt maxHeadInDegree ) {
        this.maxHeadInDegree.set( maxHeadInDegree );
    }

    /**
     * Changes the maximum tail out-degree for edges of this type.
     *
     * @param maxTailOutDegree the new tail out-degree limit.
     */
    public void setMaxTailOutDegree( OptionalInt maxTailOutDegree ) {
        this.maxTailOutDegree.set( maxTailOutDegree );
    }

    /**
     * Changes the minimum head in-degree for edges of this type.
     *
     * @param minHeadInDegree the new head in-degree minimum.
     */
    public void setMinHeadInDegree( OptionalInt minHeadInDegree ) {
        this.minHeadInDegree.set( minHeadInDegree );
    }

    /**
     * Changes the minimum tail out-degree for edges of this type.
     *
     * @param minTailOutDegree the new tail out-degree minimum.
     */
    public void setMinTailOutDegree( OptionalInt minTailOutDegree ) {
        this.minTailOutDegree.set( minTailOutDegree );
    }

    /**
     * Updates the tail role name for this edge type.
     *
     * @param tailRoleName the new name for the role of the vertex at the tail of edges of this type.
     */
    public void setTailRoleName( Optional<String> tailRoleName ) {
        this.tailRoleName.set( tailRoleName );
    }

    /**
     * Changes the vertex type for the tail of this edge type.
     *
     * @param tailVertexType the new tail vertex type.
     */
    public void setTailVertexType( IVertexType tailVertexType ) {
        this.tailVertexType.set( tailVertexType );
    }

    /** The name of the role for the vertex at the head of edges of this type. */
    private final V<Optional<String>> headRoleName;

    /** The vertex type at the head of edges of this type. */
    private final V<IVertexType> headVertexType;

    /** The maximum in-degree for the head vertex of edges of this type. */
    private final V<OptionalInt> maxHeadInDegree;

    /** The maximum out-degree for the tail vertex of edges of this type. */
    private final V<OptionalInt> maxTailOutDegree;

    /** The minimum in-degree for the head vertex of edges of this type. */
    private final V<OptionalInt> minHeadInDegree;

    /** The minimum out-degree for the tail vertex of edges of this type. */
    private final V<OptionalInt> minTailOutDegree;

    /** The name of the role for the vertex at the tail of edges of this type. */
    private final V<Optional<String>> tailRoleName;

    /** The vertex type at the tail of edges of this type. */
    private final V<IVertexType> tailVertexType;

}
