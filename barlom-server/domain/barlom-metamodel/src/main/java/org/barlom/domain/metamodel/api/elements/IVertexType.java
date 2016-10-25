//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.elements;

import org.barlom.infrastructure.utilities.collections.ISizedIterable;

import java.util.Optional;
import java.util.UUID;

/**
 * Top level interface to a vertex type.
 */
public interface IVertexType
    extends IPackagedElement {

    class Record
        extends IPackagedElement.Record {

        public Record( UUID id, UUID parentPackageId, String name, EAbstractness abstractness, UUID superTypeId ) {
            super( id, parentPackageId, name );

            this.abstractness = abstractness;
            this.superTypeId = superTypeId;
        }

        public final EAbstractness abstractness;

        public final UUID superTypeId;

    }

    /**
     * @return the abstractness of this vertex type
     */
    EAbstractness getAbstractness();

    /**
     * @return the defined attributes of this vertex type.
     */
    ISizedIterable<IVertexAttributeDecl> getAttributes();

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
