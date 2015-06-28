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

    /** Returns the columnsByName. */
    ISqlColumn getColumnByName( String name );

    /** Returns the columns. */
    List<ISqlColumn> getColumns();

    /** @return the parent domain of this relation. */
    @Override
    ISqlDomain getParent();

}
