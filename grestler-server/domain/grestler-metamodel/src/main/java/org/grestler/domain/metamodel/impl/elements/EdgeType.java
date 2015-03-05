//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.infrastructure.utilities.collections.ISizedIterable;
import org.grestler.infrastructure.utilities.revisions.V;
import org.grestler.infrastructure.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for edge types.
 */
abstract class EdgeType
    extends PackagedElement
    implements IEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the edge type.
     * @param abstractness   whether the edge type is abstract or concrete.
     * @param cyclicity      whether the edge type is constrained to be acyclic.
     * @param multiEdgedness whether the edge type is constrained to disallow multiple edges between two given
     *                       vertexes.
     * @param selfLooping    whether the edge type disallows edges from a vertex to itself.
     */
    protected EdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping
    ) {
        super( id, parentPackage, name );

        this.abstractness = new V<>( abstractness );
        this.cyclicity = new V<>( cyclicity );
        this.multiEdgedness = new V<>( multiEdgedness );
        this.selfLooping = new V<>( selfLooping );

        this.attributes = new VArray<>();

        // TODO: track sub-types

    }

    @Override
    public final void addAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "superTypeId", this.getSuperEdgeType().get().getId().toString() )
            .write( "abstractness", this.getAbstractness().name() )
            .write( "cyclicity", this.getCyclicity().name() )
            .write( "multiEdgedness", this.getMultiEdgedness().name() )
            .write( "selfLooping", this.getSelfLooping().name() );

        // TODO: attribute declarations

    }

    @Override
    public final EAbstractness getAbstractness() {
        return this.abstractness.get();
    }

    @Override
    public final ISizedIterable<IEdgeAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public final ECyclicity getCyclicity() {
        return this.cyclicity.get();
    }

    @Override
    public final EMultiEdgedness getMultiEdgedness() {
        return this.multiEdgedness.get();
    }

    @Override
    public final ESelfLooping getSelfLooping() {
        return this.selfLooping.get();
    }

    @Override
    public final void removeAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.remove( attribute );
    }

    /**
     * Changes whether this edge type is abstract.
     *
     * @param abstractness the new abstractness value.
     */
    public void setAbstractness( EAbstractness abstractness ) {
        this.abstractness.set( abstractness );
    }

    /**
     * Changes the cyclicity of this edge type.
     *
     * @param cyclicity the new cyclicity.
     */
    public void setCyclicity( ECyclicity cyclicity ) {
        this.cyclicity.set( cyclicity );
    }

    /**
     * Changes whether this edge type allows multi-edges.
     *
     * @param multiEdgedness the new value.
     */
    public void setMultiEdgedness( EMultiEdgedness multiEdgedness ) {
        this.multiEdgedness.set( multiEdgedness );
    }

    /**
     * Changes whether this edge type allows self-looping.
     *
     * @param selfLooping the new value.
     */
    public void setSelfLooping( ESelfLooping selfLooping ) {
        this.selfLooping.set( selfLooping );
    }

    /** Whether this edge type is abstract. */
    private final V<EAbstractness> abstractness;

    /** The attribute declarations within this edge type. */
    private final VArray<IEdgeAttributeDecl> attributes;

    /** Whether this edge type is acyclic. */
    private final V<ECyclicity> cyclicity;

    /** Whether this edge type allows multiple edges between two given vertexes. */
    private final V<EMultiEdgedness> multiEdgedness;

    /** Whether this edge type allows an edge from a vertex to itself. */
    private final V<ESelfLooping> selfLooping;

}
