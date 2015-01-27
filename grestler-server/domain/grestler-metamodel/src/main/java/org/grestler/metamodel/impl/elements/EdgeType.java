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

import javax.json.stream.JsonGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
abstract class EdgeType
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
     * @param cyclicity      whether the edge type is constrained to be acyclic.
     * @param multiEdgedness whether the edge type is constrained to disallow multiple edges between two given
     *                       vertexes.
     * @param selfEdgedness  whether the edge type disallows edges from a vertex to itself.
     */
    protected EdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfEdgedness selfEdgedness
    ) {
        super( id, parentPackage, name );

        this.superType = superType;
        this.abstractness = abstractness;
        this.cyclicity = cyclicity;
        this.multiEdgedness = multiEdgedness;
        this.selfEdgedness = selfEdgedness;

        this.attributes = new ArrayList<>();

        ( (IPackageSpi) parentPackage ).addEdgeType( this );

    }

    @Override
    public final void addAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        super.generateJsonAttributes( json );
        json.write( "superTypeId", this.superType.getId().toString() )
            .write( "abstractness", this.abstractness.name() )
            .write( "cyclicity", this.cyclicity.name() )
            .write( "multiEdgedness", this.multiEdgedness.name() )
            .write( "selfEdgedness", this.selfEdgedness.name() );

        // TODO: attribute declarations
    }

    @Override
    public final EAbstractness getAbstractness() {
        return this.abstractness;
    }

    @Override
    public final List<IEdgeAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public final ECyclicity getCyclicity() {
        return this.cyclicity;
    }

    @Override
    public final EMultiEdgedness getMultiEdgedness() {
        return this.multiEdgedness;
    }

    @Override
    public final ESelfEdgedness getSelfEdgedness() {
        return this.selfEdgedness;
    }

    @Override
    public final Optional<IEdgeType> getSuperType() {
        return Optional.of( this.superType );
    }

    @Override
    public final boolean isSubTypeOf( IEdgeType edgeType ) {
        return this == edgeType || this.getSuperType().get().isSubTypeOf( edgeType );
    }

    /** Whether this edge type is abstract. */
    private final EAbstractness abstractness;

    /** The attribute declarations within this edge type. */
    private final List<IEdgeAttributeDecl> attributes;

    /** Whetehr this edge type is acyclic. */
    private final ECyclicity cyclicity;

    /** Whether this edge type allows multiple edges between two given vertexes. */
    private final EMultiEdgedness multiEdgedness;

    /** Whetehr this edge type allows an edge from a vertex to itself. */
    private final ESelfEdgedness selfEdgedness;

    /** The super type for this edge type. */
    private final IEdgeType superType;

}
