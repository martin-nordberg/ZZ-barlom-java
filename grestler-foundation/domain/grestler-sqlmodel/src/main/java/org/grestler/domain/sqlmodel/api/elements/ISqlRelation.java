//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

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
