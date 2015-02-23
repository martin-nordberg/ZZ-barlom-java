//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAttributeOptionality;
import org.grestler.metamodel.api.elements.ELabelDefaulting;
import org.grestler.metamodel.api.elements.IAttributeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for vertex attribute declarations.
 */
public final class VertexAttributeDecl
    extends NamedElement
    implements IVertexAttributeDecl {

    /**
     * Constructs a new vertex attribute declaration.
     *
     * @param id               the unique ID of the attribute declaration.
     * @param parentVertexType the parent vertex type.
     * @param name             the name of the attribute.
     * @param type             the type of the attribute.
     * @param optionality      whether this attribute is optionality.
     * @param labelDefaulting  whether this is the default label for vertexes of the parent type.
     */
    public VertexAttributeDecl(
        UUID id,
        IVertexType parentVertexType,
        String name,
        IAttributeType type,
        EAttributeOptionality optionality,
        ELabelDefaulting labelDefaulting
    ) {

        super( id, name );

        this.parentVertexType = new V<>( parentVertexType );
        this.type = new V<>( type );
        this.optionality = new V<>( optionality );
        this.labelDefaulting = new V<>( labelDefaulting );

        ( (IVertexTypeUnderAssembly) parentVertexType ).addAttribute( this );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "typeId", this.getType().getId().toString() )
            .write( "optionality", this.getOptionality().name() )
            .write( "labelDefaulting", this.getLabelDefaulting().name() );

    }

    @Override
    public ELabelDefaulting getLabelDefaulting() {
        return this.labelDefaulting.get();
    }

    @Override
    public EAttributeOptionality getOptionality() {
        return this.optionality.get();
    }

    @Override
    public IVertexType getParentVertexType() {
        return this.parentVertexType.get();
    }

    @Override
    public IAttributeType getType() {
        return this.type.get();
    }

    /**
     * Changes whether this is the default label for its vertex type.
     *
     * @param labelDefaulting the new defaulting.
     */
    public void setLabelDefaulting( ELabelDefaulting labelDefaulting ) {
        // TODO: enforce one default per vertex type
        this.labelDefaulting.set( labelDefaulting );
    }

    /**
     * Changes whether this is an optional attribute.
     *
     * @param optionality the new optionality.
     */
    public void setOptionality( EAttributeOptionality optionality ) {
        this.optionality.set( optionality );
    }

    /**
     * Moves this attribute type to another vertex type.
     *
     * @param parentVertexType the new parent vertex type.
     */
    public void setParentVertexType( IVertexType parentVertexType ) {

        ( (IVertexTypeUnderAssembly) this.parentVertexType.get() ).removeAttribute( this );

        this.parentVertexType.set( parentVertexType );

        ( (IVertexTypeUnderAssembly) parentVertexType ).addAttribute( this );

    }

    /**
     * Changes the type of this attribute.
     *
     * @param type the new attribute type.
     */
    public void setType( IAttributeType type ) {
        this.type.set( type );
    }

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    private final V<ELabelDefaulting> labelDefaulting;

    /** Whether this attribute is required for instances of the parent vertex type. */
    private final V<EAttributeOptionality> optionality;

    /** The parent vertex type with this attribute. */
    private final V<IVertexType> parentVertexType;

    /** The type of this attribute declaration. */
    private final V<IAttributeType> type;
}
