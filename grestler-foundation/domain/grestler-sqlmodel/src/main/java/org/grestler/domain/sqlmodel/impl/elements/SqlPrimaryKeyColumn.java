//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ESqlDataType;
import org.grestler.domain.sqlmodel.api.elements.ISqlPrimaryKeyColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;

/**
 * A column that is a surrogate primary key.
 */
public class SqlPrimaryKeyColumn
    extends SqlTableColumn
    implements ISqlPrimaryKeyColumn {

    /**
     * Constructs a new primary key column
     *
     * @param parent the parent table for the new column.
     * @param name   the name of the new key.
     */
    SqlPrimaryKeyColumn( SqlTable parent, String name ) {
        super( parent, name, "primary key", ESqlDataType.INTEGER, 0, 0, false, null );

        parent.onAddChild( this );
    }

    @Override
    public ISqlTable getParent() {
        return (ISqlTable) super.getParent();
    }

}
