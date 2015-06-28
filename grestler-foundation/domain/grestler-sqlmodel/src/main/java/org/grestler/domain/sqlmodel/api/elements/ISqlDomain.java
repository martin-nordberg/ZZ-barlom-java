//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.util.List;

/**
 * A domain is a related subset of a schema.
 */
public interface ISqlDomain
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
    ISqlSchema getParent();

    /** Returns the tables. */
    List<ISqlTable> getTables();

    /** Returns the views. */
    List<ISqlView> getViews();

    /** Returns true if this domain has additional hand-written SQL DDL. */
    boolean hasSqlCustomizations();

}
