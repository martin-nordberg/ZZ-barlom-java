//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.UUID;

/**
 * Shared top level base interface for metadata elements.
 */
public interface IDocumentedElement {

    /**
     * Data structure for documented element records.
     */
    class Record {

        protected Record( UUID id ) {
            this.id = id;
        }

        public final UUID id;

    }

    /**
     * Generates the JSON representation of this element to the given generator.
     *
     * @param json the generator to write to.
     */
    default void generateJson( JsonGenerator json ) {
        json.writeStartObject();
        this.generateJsonAttributes( json );
        json.writeEnd();
    }

    /**
     * Generates the JSON representation of this element's attributes to the given generator.
     *
     * @param json the generator to write to.
     */
    void generateJsonAttributes( JsonGenerator json );

    /**
     * @return the unique ID of this element.
     */
    UUID getId();

    /**
     * @return the parent element containing this element.
     */
    @SuppressWarnings( "ClassReferencesSubclass" )
    INamedElement getParent();

    /**
     * @return a JSON representation of this element.
     */
    default String toJson() {

        StringWriter result = new StringWriter();
        JsonGenerator json = Json.createGenerator( result );

        this.generateJson( json );

        json.close();
        return result.toString();

    }

}
