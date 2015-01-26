//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfEdgedness;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import javax.json.stream.JsonGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
public final class EdgeType
    extends Element
    implements IEdgeType, IEdgeTypeSpi {

    /**
     * Constructs a new edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type.
     * @param abstractness   whether the edge type is abstract or concrete.
     * @param tailVertexType the vertex type at the start of the edge type.
     * @param headVertexType the vertex type at the end of the edge type.
     * @param tailRoleName   the role name for vertexes at the tail of this edge type
     * @param headRoleName   the role name for vertexes at the head of this edge type
     * @param cyclicity      whether the edge type is constrained to be acyclic.
     * @param multiEdgedness whether the edge type is constrained to disallow multiple edges between two given
     *                       vertexes.
     * @param selfEdgedness  whether the edge type disallows edges from a vertex to itself.
     */
    public EdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        EAbstractness abstractness,
        IVertexType tailVertexType,
        IVertexType headVertexType,
        Optional<String> tailRoleName,
        Optional<String> headRoleName,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfEdgedness selfEdgedness
    ) {
        super( id, parentPackage, name );

        this.superType = superType;
        this.abstractness = abstractness;
        this.tailVertexType = tailVertexType;
        this.headVertexType = headVertexType;
        this.tailRoleName = tailRoleName;
        this.headRoleName = headRoleName;
        this.cyclicity = cyclicity;
        this.multiEdgedness = multiEdgedness;
        this.selfEdgedness = selfEdgedness;

        this.attributes = new ArrayList<>();

        ( (IPackageSpi) parentPackage ).addEdgeType( this );

    }

    @Override
    public void addAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        super.generateJsonAttributes( json );
        json.write( "tailVertexTypeId", this.tailVertexType.getId().toString() )
            .write( "headVertexTypeId", this.headVertexType.getId().toString() );
    }

    @Override
    public EAbstractness getAbstractness() {
        return this.abstractness;
    }

    @Override
    public List<IEdgeAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public ECyclicity getCyclicity() {
        return this.cyclicity;
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
    public EMultiEdgedness getMultiEdgedness() {
        return this.multiEdgedness;
    }

    @Override
    public ESelfEdgedness getSelfEdgedness() {
        return this.selfEdgedness;
    }

    @Override
    public Optional<IEdgeType> getSuperType() {
        return Optional.of( this.superType );
    }

    @Override
    public Optional<String> getTailRoleName() {
        return this.tailRoleName;
    }

    @Override
    public IVertexType getTailVertexType() {
        return this.tailVertexType;
    }

    @Override
    public boolean isSubTypeOf( IEdgeType edgeType ) {
        return this == edgeType || this.getSuperType().get().isSubTypeOf( edgeType );
    }

    /** Whether this edge type is abstract. */
    private final EAbstractness abstractness;

    /** The attribute declarations within this edge type. */
    private final List<IEdgeAttributeDecl> attributes;

    /** Whetehr this edge type is acyclic. */
    private final ECyclicity cyclicity;

    /** The name of the role for the vertex at the head of edges of this type. */
    private final Optional<String> headRoleName;

    /** The vertex type at the head of edges of this type. */
    private final IVertexType headVertexType;

    /** Whether this edge type allows multiple edges between two given vertexes. */
    private final EMultiEdgedness multiEdgedness;

    /** Whetehr this edge type allows an edge from a vertex to itself. */
    private final ESelfEdgedness selfEdgedness;

    /** The super type for this edge type. */
    private final IEdgeType superType;

    /** The name of the role for the vertex at the tail of edges of this type. */
    private final Optional<String> tailRoleName;

    /** The vertex type at the tail of edges of this type. */
    private final IVertexType tailVertexType;

}
