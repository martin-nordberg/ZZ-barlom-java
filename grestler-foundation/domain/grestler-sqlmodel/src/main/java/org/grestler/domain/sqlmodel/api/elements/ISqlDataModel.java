package org.grestler.domain.sqlmodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * Interface to a top-level data model containing one or more schemas.
 */
public interface ISqlDataModel
    extends ISqlNamedModelElement {

    /**
     * Creates a new schema within this data model.
     *
     * @param name        the name of the schema.
     * @param description a description of the schema.
     *
     * @return the newly created schema.
     */
    ISqlSchema addSchema( String name, String description );

    /** @return the schema within this data model with given name. */
    Optional<ISqlSchema> getSchemaByName( String name );

    /** @return the schemas. */
    IIndexable<ISqlSchema> getSchemas();

}
