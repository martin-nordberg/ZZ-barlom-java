package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IElement;

import javax.json.stream.JsonGenerator;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of abstract element.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class Element
    implements IElement {

    /**
     * Constructs a new model element.
     *
     * @param id   the unique ID for the element.
     * @param name the name of the element.
     */
    protected Element( UUID id, String name ) {
        Objects.requireNonNull( id, "Missing ID" );

        this.id = id;
        this.name = name;
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() ).write( "name", this.name ).write( "path", this.getPath() );
    }

    @Override
    public final UUID getId() {
        return this.id;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    /** The unique ID of this element. */
    private final UUID id;

    /** The name of this element. */
    private final String name;

}
