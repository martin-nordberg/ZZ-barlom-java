//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * A column within a table.
 */
public interface ISqlTableColumn
    extends ISqlColumn {

    /** Returns the defaultValue. */
    Object getDefaultValue();

    /** Returns the precision. */
    int getPrecision();

    /** Returns the size. */
    int getSize();

    /** Returns the type. */
    ESqlDataType getType();

    /** Returns the whether the column allows nulls. */
    boolean isNullable();

}
