//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.utilities.collections.ISizedIterable;
import org.grestler.utilities.revisions.V;
import org.grestler.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
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
     * @param superType      the super type.
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
        IEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping
    ) {
        super( id, parentPackage, name );

        this.superType = new V<>( superType );
        this.abstractness = new V<>( abstractness );
        this.cyclicity = new V<>( cyclicity );
        this.multiEdgedness = new V<>( multiEdgedness );
        this.selfLooping = new V<>( selfLooping );

        this.attributes = new VArray<>();

        ( (IPackageUnderAssembly) parentPackage ).addEdgeType( this );

    }

    @Override
    public final void addAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "superTypeId", this.getSuperType().get().getId().toString() )
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
    public final Optional<IEdgeType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    @Override
    public final boolean isSubTypeOf( IEdgeType edgeType ) {
        return this == edgeType || this.getSuperType().get().isSubTypeOf( edgeType );
    }

    /** Whether this edge type is abstract. */
    private final V<EAbstractness> abstractness;

    /** The attribute declarations within this edge type. */
    private final VArray<IEdgeAttributeDecl> attributes;

    /** Whetehr this edge type is acyclic. */
    private final V<ECyclicity> cyclicity;

    /** Whether this edge type allows multiple edges between two given vertexes. */
    private final V<EMultiEdgedness> multiEdgedness;

    /** Whetehr this edge type allows an edge from a vertex to itself. */
    private final V<ESelfLooping> selfLooping;

    /** The super type for this edge type. */
    private final V<IEdgeType> superType;

}
