//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfEdgedness;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

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
     * @param selfEdgedness    whether the edge type disallows edges from a vertex to itself.
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
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfEdgedness selfEdgedness,
        IVertexType tailVertexType,
        IVertexType headVertexType,
        Optional<String> tailRoleName,
        Optional<String> headRoleName,
        OptionalInt minTailOutDegree,
        OptionalInt maxTailOutDegree,
        OptionalInt minHeadInDegree,
        OptionalInt maxHeadInDegree
    ) {
        super( id, parentPackage, name, superType, abstractness, cyclicity, multiEdgedness, selfEdgedness );

        this.tailVertexType = tailVertexType;
        this.headVertexType = headVertexType;
        this.tailRoleName = tailRoleName;
        this.headRoleName = headRoleName;
        this.minTailOutDegree = minTailOutDegree;
        this.maxTailOutDegree = maxTailOutDegree;
        this.minHeadInDegree = minHeadInDegree;
        this.maxHeadInDegree = maxHeadInDegree;

        ( (IPackageSpi) parentPackage ).addEdgeType( this );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "tailVertexTypeId", this.tailVertexType.getId().toString() )
            .write( "headVertexTypeId", this.headVertexType.getId().toString() );

        this.tailRoleName.ifPresent( tailRoleName -> json.write( "tailRoleName", tailRoleName ) );
        this.headRoleName.ifPresent( headRoleName -> json.write( "headRoleName", headRoleName ) );
        this.minTailOutDegree.ifPresent( minTailOutDegree -> json.write( "minTailOutDegree", minTailOutDegree ) );
        this.maxTailOutDegree.ifPresent( maxTailOutDegree -> json.write( "maxTailOutDegree", maxTailOutDegree ) );
        this.minHeadInDegree.ifPresent( minHeadInDegree -> json.write( "minHeadInDegree", minHeadInDegree ) );
        this.maxHeadInDegree.ifPresent( maxHeadInDegree -> json.write( "maxHeadInDegree", maxHeadInDegree ) );

    }

    @Override
    public Optional<String> getHeadRoleName() {
        return this.headRoleName;
    }

    @Override
    public IVertexType getHeadVertexType() {
        return this.headVertexType;
    }

    @Override
    public OptionalInt getMaxHeadInDegree() {
        return this.maxHeadInDegree;
    }

    @Override
    public OptionalInt getMaxTailOutDegree() {
        return this.maxTailOutDegree;
    }

    @Override
    public OptionalInt getMinHeadInDegree() {
        return this.minHeadInDegree;
    }

    @Override
    public OptionalInt getMinTailOutDegree() {
        return this.minTailOutDegree;
    }

    @Override
    public Optional<String> getTailRoleName() {
        return this.tailRoleName;
    }

    @Override
    public IVertexType getTailVertexType() {
        return this.tailVertexType;
    }

    /** The name of the role for the vertex at the head of edges of this type. */
    private final Optional<String> headRoleName;

    /** The vertex type at the head of edges of this type. */
    private final IVertexType headVertexType;

    /** The maximum in-degree for the head vertex of edges of this type. */
    private final OptionalInt maxHeadInDegree;

    /** The maximum out-degree for the tail vertex of edges of this type. */
    private final OptionalInt maxTailOutDegree;

    /** The minimum in-degree for the head vertex of edges of this type. */
    private final OptionalInt minHeadInDegree;

    /** The minimum out-degree for the tail vertex of edges of this type. */
    private final OptionalInt minTailOutDegree;

    /** The name of the role for the vertex at the tail of edges of this type. */
    private final Optional<String> tailRoleName;

    /** The vertex type at the tail of edges of this type. */
    private final IVertexType tailVertexType;

}
