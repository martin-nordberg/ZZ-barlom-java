//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.api.elements;

import org.barlom.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * An abstract relation (table, view, etc.).
 */
public interface ISqlRelation
    extends ISqlNamedModelElement {

    /** @return the columnsByName. */
    Optional<? extends ISqlColumn> getColumnByName( String name );

    /** @return the columns. */
    IIndexable<ISqlColumn> getColumns();

    @Override
    ISqlSchema getParent();

}
