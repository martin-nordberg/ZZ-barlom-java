//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * A column that is a surrogate primary key.
 */
public interface ISqlPrimaryKeyColumn
    extends ISqlTableColumn {

    @Override
    ISqlTable getParent();

}
