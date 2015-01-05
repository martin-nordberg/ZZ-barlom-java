//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.UUID;

/**
 * Shared general interface for metadata elements.
 */
public interface IElement {

    /**
     * Generates the JSON representation of this element to the given generator.
     *
     * @param json the generator to write to.
     */
    void generateJson( JsonGenerator json );

    /**
     * @return the unique ID of this element.
     */
    UUID getId();

    /**
     * @return the name of this element.
     */
    String getName();

    /**
     * @return the fully qualified path to this element.
     */
    String getPath();

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
