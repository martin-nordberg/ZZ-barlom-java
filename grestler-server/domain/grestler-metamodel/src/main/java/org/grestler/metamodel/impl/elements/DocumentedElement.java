//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IDocumentedElement;

import javax.json.stream.JsonGenerator;
import java.util.Objects;
import java.util.UUID;

/**
 * Top-level class for Grestler model elements.
 */
public abstract class DocumentedElement
    implements IDocumentedElement {

    protected DocumentedElement( UUID id ) {

        Objects.requireNonNull( id, "Missing ID" );

        this.id = id;

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() );
    }

    @Override
    public final UUID getId() {
        return this.id;
    }

    /** The unique ID of this element. */
    private final UUID id;

}
