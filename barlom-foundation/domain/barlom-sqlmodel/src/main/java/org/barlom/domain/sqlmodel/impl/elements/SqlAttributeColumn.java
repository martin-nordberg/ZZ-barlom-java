//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ESqlDataType;
import org.barlom.domain.sqlmodel.api.elements.ISqlAttributeColumn;

/**
 * A column for an attribute.
 */
public class SqlAttributeColumn
    extends SqlTableColumn
    implements ISqlAttributeColumn {

    /**
     * Constructs a new attribute column.
     */
    SqlAttributeColumn(
        SqlTable parent,
        String name,
        String attributeName,
        String description,
        ESqlDataType type,
        int size,
        int precision,
        boolean isNullable,
        Object defaultValue
    ) {
        super( parent, name, description, type, size, precision, isNullable, defaultValue );

        this.attributeName = attributeName;

        parent.onAddChild( this );
    }

    @Override
    public String getAttributeName() {
        return this.attributeName;
    }

    private final String attributeName;

}
