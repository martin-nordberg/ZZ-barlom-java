//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.attributes.EAttributeRequired;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDeclaration;
import org.grestler.metamodel.api.elements.IVertexType;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for vertex attribute declarations.
 */
public final class VertexAttributeDeclaration
    implements IVertexAttributeDeclaration {

    /**
     * Constructs a new vertex attribute declaration.
     *
     * @param id               the unique ID of the attribute declaration.
     * @param parentVertexType the parent vertex type.
     * @param name             the name of the attribute.
     * @param type             the type of the attribute.
     * @param required         whether this attribute is required.
     */
    public VertexAttributeDeclaration(
        UUID id, IVertexType parentVertexType, String name, IAttributeType type, EAttributeRequired required
    ) {
        this.id = id;
        this.parentVertexType = parentVertexType;
        this.name = name;
        this.type = type;
        this.required = required;

        ( (IVertexTypeSpi) this.parentVertexType ).addAttribute( this );
    }

    @Override
    public void generateJson( JsonGenerator json ) {
        json.writeStartObject()
            .write( "id", this.id.toString() )
            .write( "parentVertexTypeId", this.parentVertexType.getId().toString() )
            .write( "name", this.name )
            .write( "typeId", this.type.getId().toString() )
            .write( "required", this.required.name() )
            .writeEnd();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IVertexType getParentVertexType() {
        return this.parentVertexType;
    }

    @Override
    public EAttributeRequired getRequired() {
        return this.required;
    }

    @Override
    public IAttributeType getType() {
        return this.type;
    }

    /** The unique ID of this attribute declaration. */
    private final UUID id;

    /** The name of this attribute declaration. */
    private final String name;

    /** The parent vertex type of this attribute declaration. */
    private final IVertexType parentVertexType;

    /** Whether this attribute is required for instances of the parent vertex type. */
    private final EAttributeRequired required;

    /** The type of this attribute declaration. */
    private final IAttributeType type;

}
