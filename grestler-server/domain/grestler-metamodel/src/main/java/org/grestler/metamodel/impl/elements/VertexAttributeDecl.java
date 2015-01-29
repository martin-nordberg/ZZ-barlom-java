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

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for vertex attribute declarations.
 */
public final class VertexAttributeDecl
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

        this.id = id;
        this.parentVertexType = parentVertexType;
        this.name = name;
        this.type = type;
        this.optionality = optionality;
        this.labelDefaulting = labelDefaulting;

        ( (IVertexTypeSpi) this.parentVertexType ).addAttribute( this );

    }

    @Override
    public void generateJson( JsonGenerator json ) {
        json.writeStartObject()
            .write( "id", this.id.toString() )
            .write( "parentVertexTypeId", this.parentVertexType.getId().toString() )
            .write( "name", this.name )
            .write( "typeId", this.type.getId().toString() )
            .write( "optionality", this.optionality.name() )
            .write( "labelDefaulting", this.labelDefaulting.name() )
            .writeEnd();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public ELabelDefaulting getLabelDefaulting() {
        return this.labelDefaulting;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public EAttributeOptionality getOptionality() {
        return this.optionality;
    }

    @Override
    public IVertexType getParentVertexType() {
        return this.parentVertexType;
    }

    @Override
    public IAttributeType getType() {
        return this.type;
    }

    /** The unique ID of this attribute declaration. */
    private final UUID id;

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    private final ELabelDefaulting labelDefaulting;

    /** The name of this attribute declaration. */
    private final String name;

    /** Whether this attribute is required for instances of the parent vertex type. */
    private final EAttributeOptionality optionality;

    /** The parent vertex type of this attribute declaration. */
    private final IVertexType parentVertexType;

    /** The type of this attribute declaration. */
    private final IAttributeType type;
}
