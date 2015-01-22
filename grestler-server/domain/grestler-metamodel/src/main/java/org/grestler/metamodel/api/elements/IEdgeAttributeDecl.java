//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Interface to an edge attribute declaration.
 */
public interface IEdgeAttributeDecl {

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
     * @return the parent of this attribute.
     */
    IEdgeType getParentEdgeType();

    /**
     * @return the typeof this attribute.
     */
    IAttributeType getType();

    /**
     * @return whether this is a required attribute.
     */
    EAttributeOptionality getOptionality();

}
