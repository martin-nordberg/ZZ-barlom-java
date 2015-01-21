//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import javax.json.stream.JsonGenerator;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType
    extends IElement {

    /**
     * @return the defined attributes of this vertex type.
     */
    Iterable<IVertexAttributeDeclaration> getAttributes();

    /**
     * @return the super type of this vertex type.
     */
    Optional<IVertexType> getSuperType();

    /**
     * Determines whether this vertex type is a direct or indirect subtype of the given vertex type.
     *
     * @param vertexType the potential super type
     *
     * @return true if this vertex type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    boolean isSubTypeOf( IVertexType vertexType );

}
