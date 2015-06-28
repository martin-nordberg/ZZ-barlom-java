//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ESqlDataType;
import org.grestler.domain.sqlmodel.api.elements.ISqlDiscriminatorColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;

/**
 * A column used as a discriminator (i.e. type indicator).
 */
public class SqlDiscriminatorColumn
    extends SqlTableColumn
    implements ISqlDiscriminatorColumn {

    /**
     * Constructs a new
     *
     * @param parent       the parent table for the new column.
     * @param defaultValue the default value for the column.
     */
    SqlDiscriminatorColumn( SqlTable parent, String defaultValue ) {
        super(
            parent,
            "Discriminator",
            "A flag indicating what type of object this is (for joins to inheriting tables)",
            ESqlDataType.VARCHAR2,
            2,
            0,
            false,
            defaultValue
        );

        parent.onAddChild( this );
    }

    @Override
    public ISqlTable getParent() {
        return (ISqlTable) super.getParent();
    }

}
