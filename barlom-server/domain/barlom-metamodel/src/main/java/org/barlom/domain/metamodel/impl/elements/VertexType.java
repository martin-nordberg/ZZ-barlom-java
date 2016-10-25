//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.EAbstractness;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IVertexAttributeDecl;
import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.infrastructure.utilities.collections.ISizedIterable;
import org.barlom.infrastructure.utilities.revisions.V;
import org.barlom.infrastructure.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.Optional;

/**
 * Implementation class for vertex types.
 */
public final class VertexType
    extends PackagedElement
    implements IVertexType, IVertexTypeUnderAssembly {

    /**
     * Constructs a new vertex type.
     *
     * @param record        the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     * @param superType     the super type.
     */
    public VertexType(
        IVertexType.Record record, IPackage parentPackage, IVertexType superType
    ) {

        super( record, parentPackage );

        this.superType = new V<>( superType );
        this.abstractness = new V<>( record.abstractness );
        this.attributes = new VArray<>();

        // TODO: track subtypes

    }

    @Override
    public void addAttribute( IVertexAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "superTypeId", this.getSuperType().get().getId().toString() )
            .write( "abstractness", this.getAbstractness().name() );

        // TODO: attribute declarations
    }

    @Override
    public EAbstractness getAbstractness() {
        return this.abstractness.get();
    }

    @Override
    public ISizedIterable<IVertexAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    @Override
    public boolean isSubTypeOf( IVertexType vertexType ) {
        return this == vertexType || this.getSuperType().get().isSubTypeOf( vertexType );
    }

    @Override
    public void removeAttribute( IVertexAttributeDecl attribute ) {
        this.attributes.remove( attribute );
    }

    /**
     * Changes whether this is an abstract vertex type.
     *
     * @param abstractness the new abstractness.
     */
    public void setAbstractness( EAbstractness abstractness ) {
        this.abstractness.set( abstractness );
    }

    /**
     * Changes the super type of this vertex type.
     *
     * @param superType the new super type.
     */
    public void setSuperType( IVertexType superType ) {
        // TODO: track old subtypes
        this.superType.set( superType );
        // TODO: track new subtypes
    }

    /** Whether this vertex type is abstract. */
    private final V<EAbstractness> abstractness;

    /** The attributes of this vertex type. */
    private final VArray<IVertexAttributeDecl> attributes;

    /** The super type of this vertex type. */
    private final V<IVertexType> superType;

}
