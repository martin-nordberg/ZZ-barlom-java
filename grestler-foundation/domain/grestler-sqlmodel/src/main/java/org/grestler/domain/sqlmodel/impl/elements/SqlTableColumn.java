//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ESqlDataType;
import org.grestler.domain.sqlmodel.api.elements.ISqlTableColumn;

/**
 * A column within a table.
 */
public abstract class SqlTableColumn
    extends SqlColumn
    implements ISqlTableColumn {

    /**
     * Constructs a new database column.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    SqlTableColumn(
        SqlTable parent,
        String name,
        String description,
        ESqlDataType type,
        Integer size,
        Integer precision,
        boolean isNullable,
        Object defaultValue
    ) {
        super( parent, name, description );

        this.type = type;
        this.size = size;
        this.precision = precision;
        this.isNullable = isNullable;
        this.defaultValue = defaultValue;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public int getPrecision() {
        return this.precision;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( this.getName() );
    }

    @Override
    public ESqlDataType getType() {
        return this.type;
    }

    @Override
    public boolean isNullable() {
        return this.isNullable;
    }

    private final Object defaultValue;

    private final boolean isNullable;

    private final Integer precision;

    private final Integer size;

    private final ESqlDataType type;

}
