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

    /** @return the defaultValue. */
    Object getDefaultValue();

    @Override
    ISqlTable getParent();

    /** @return the precision. */
    int getPrecision();

    /** @return the size. */
    int getSize();

    /** @return the type. */
    ESqlDataType getType();

    /** @return the whether the column allows nulls. */
    boolean isNullable();

}
