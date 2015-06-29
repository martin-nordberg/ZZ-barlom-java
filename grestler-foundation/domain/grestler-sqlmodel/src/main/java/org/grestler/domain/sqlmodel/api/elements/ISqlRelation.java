//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.util.List;

/**
 * An abstract relation (table, view, etc.).
 */
public interface ISqlRelation
    extends ISqlNamedModelElement {

    /** @return the columnsByName. */
    ISqlColumn getColumnByName( String name );

    /** @return the columns. */
    List<ISqlColumn> getColumns();

    @Override
    ISqlSchema getParent();

}
