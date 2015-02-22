//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation class for edge attribute declarations.
 */
public final class EdgeAttributeDecl
    extends NamedElement
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

        super( id, name );

        this.parentEdgeType = new V<>( parentEdgeType );
        this.type = new V<>( type );
        this.optionality = new V<>( optionality );

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

    /** Whether this attribute is required for instances of the parent edge type. */
    private final V<EAttributeOptionality> optionality;

    /** The parent edge type with this attribute. */
    private final V<IEdgeType> parentEdgeType;

    /** The type of this attribute declaration. */
    private final V<IAttributeType> type;

}
