//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Interface to a vertex attribute declaration.
 */
public interface IVertexAttributeDecl {

    /**
     * Generates the JSON representation of this attribute to the given generator.
     *
     * @param json the generator to write to.
     */
    void generateJson( JsonGenerator json );

    /**
     * @return the unique ID of this attribute.
     */
    UUID getId();

    /**
     * @return the name of this attribute.
     */
    String getName();

    /**
     * @return whether this is a required attribute.
     */
    EAttributeOptionality getOptionality();

    /**
     * @return the parent of this attribute.
     */
    IVertexType getParentVertexType();

    /**
     * @return the typeof this attribute.
     */
    IAttributeType getType();

}
