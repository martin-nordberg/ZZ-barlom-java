//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.ELabelDefaulting;
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

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    private final V<ELabelDefaulting> labelDefaulting;

    /** Whether this attribute is required for instances of the parent vertex type. */
    private final V<EAttributeOptionality> optionality;

    /** The parent vertex type with this attribute. */
    private final V<IVertexType> parentVertexType;

    /** The type of this attribute declaration. */
    private final V<IAttributeType> type;
}
