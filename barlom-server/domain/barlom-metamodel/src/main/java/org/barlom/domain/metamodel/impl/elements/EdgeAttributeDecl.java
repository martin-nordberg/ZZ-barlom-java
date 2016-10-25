//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.EAttributeOptionality;
import org.barlom.domain.metamodel.api.elements.IAttributeType;
import org.barlom.domain.metamodel.api.elements.IEdgeAttributeDecl;
import org.barlom.domain.metamodel.api.elements.IEdgeType;
import org.barlom.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;

/**
 * Implementation class for edge attribute declarations.
 */
public final class EdgeAttributeDecl
    extends NamedElement
    implements IEdgeAttributeDecl {

    /**
     * Constructs a new edge attribute declaration.
     *
     * @param record         the unique ID of the attribute declaration.
     * @param parentEdgeType the parent edge type.
     * @param type           the type of the attribute.
     */
    public EdgeAttributeDecl(
        IEdgeAttributeDecl.Record record, IEdgeType parentEdgeType, IAttributeType type
    ) {

        super( record );

        this.parentEdgeType = new V<>( parentEdgeType );
        this.type = new V<>( type );
        this.optionality = new V<>( record.optionality );

        ( (IEdgeTypeUnderAssembly) parentEdgeType ).addAttribute( this );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "typeId", this.getType().getId().toString() ).write( "optionality", this.getOptionality().name() );
    }

    @Override
    public EAttributeOptionality getOptionality() {
        return this.optionality.get();
    }

    @Override
    public IEdgeType getParentEdgeType() {
        return this.parentEdgeType.get();
    }

    @Override
    public IAttributeType getType() {
        return this.type.get();
    }

    /**
     * Changes the optionality of this edge attribute.
     *
     * @param optionality the new value.
     */
    public void setOptionality( EAttributeOptionality optionality ) {
        this.optionality.set( optionality );
    }

    /**
     * Moves this attribute to a different edge type.
     *
     * @param parentEdgeType the new parent edge type.
     */
    public void setParentEdgeType( IEdgeType parentEdgeType ) {

        ( (IEdgeTypeUnderAssembly) this.parentEdgeType.get() ).removeAttribute( this );

        this.parentEdgeType.set( parentEdgeType );

        ( (IEdgeTypeUnderAssembly) parentEdgeType ).addAttribute( this );

    }

    /**
     * Changes the attribute type of this attribute.
     *
     * @param type the new attribute type.
     */
    public void setType( IAttributeType type ) {
        this.type.set( type );
    }

    /** Whether this attribute is required for instances of the parent edge type. */
    private final V<EAttributeOptionality> optionality;

    /** The parent edge type with this attribute. */
    private final V<IEdgeType> parentEdgeType;

    /** The type of this attribute declaration. */
    private final V<IAttributeType> type;

}
