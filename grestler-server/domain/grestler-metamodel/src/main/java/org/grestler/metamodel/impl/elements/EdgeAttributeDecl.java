//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for edge attribute declarations.
 */
public final class EdgeAttributeDecl
    implements IEdgeAttributeDecl {

    /**
     * Constructs a new edge attribute declaration.
     *
     * @param id             the unique ID of the attribute declaration.
     * @param parentEdgeType the parent edge type.
     * @param name           the name of the attribute.
     * @param type           the type of the attribute.
     * @param optionality    whether this attribute is optionality.
     */
    public EdgeAttributeDecl(
        UUID id, IEdgeType parentEdgeType, String name, IAttributeType type, EAttributeOptionality optionality
    ) {
        this.id = id;
        this.parentEdgeType = parentEdgeType;
        this.name = name;
        this.type = type;
        this.optionality = optionality;

        ( (IEdgeTypeUnderAssembly) this.parentEdgeType ).addAttribute( this );
    }

    @Override
    public void generateJson( JsonGenerator json ) {
        json.writeStartObject()
            .write( "id", this.id.toString() )
            .write( "parentEdgeTypeId", this.parentEdgeType.getId().toString() )
            .write( "name", this.name )
            .write( "typeId", this.type.getId().toString() )
            .write( "optionality", this.optionality.name() )
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
    public EAttributeOptionality getOptionality() {
        return this.optionality;
    }

    @Override
    public IEdgeType getParentEdgeType() {
        return this.parentEdgeType;
    }

    @Override
    public IAttributeType getType() {
        return this.type;
    }

    /** The unique ID of this attribute declaration. */
    private final UUID id;

    /** The name of this attribute declaration. */
    private final String name;

    /** Whether this attribute is required for instances of the parent edge type. */
    private final EAttributeOptionality optionality;

    /** The parent edge type of this attribute declaration. */
    private final IEdgeType parentEdgeType;

    /** The type of this attribute declaration. */
    private final IAttributeType type;

}
