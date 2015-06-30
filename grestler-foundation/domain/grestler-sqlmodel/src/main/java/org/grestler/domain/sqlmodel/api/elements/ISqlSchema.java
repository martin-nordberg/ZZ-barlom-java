//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * A full schema of tables, views, etc.
 */
public interface ISqlSchema
    extends ISqlNamedModelElement {

    /**
     * Creates a new table within this schema.
     */
    ISqlTable addTable( String name, String description );

    /**
     * Creates a new view within this schema.
     */
    ISqlView addView( String name, String description );

    @Override
    ISqlDataModel getParent();

    /** @return the table or view within this schema with given name. */
    Optional<ISqlRelation> getRelationByName( String name );

    /** @return the table or view within this schema with given name. */
    Optional<ISqlTable> getTableByName( String name );

    /** @return the tables. */
    IIndexable<ISqlTable> getTables();

    /** @return the views. */
    IIndexable<ISqlView> getViews();

}
