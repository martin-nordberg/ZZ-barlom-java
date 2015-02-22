//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
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
        IEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping,
        IVertexType vertexType,
        OptionalInt minDegree,
        OptionalInt maxDegree
    ) {
        super( id, parentPackage, name, superType, abstractness, cyclicity, multiEdgedness, selfLooping );

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
    public IVertexType getVertexType() {
        return this.vertexType.get();
    }

    /** The maximum in-degree for the head vertex of edges of this type. */
    private final V<OptionalInt> maxDegree;

    /** The minimum in-degree for the head vertex of edges of this type. */
    private final V<OptionalInt> minDegree;

    /** The vertex type for edges of this type. */
    private final V<IVertexType> vertexType;

}
