//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * An index.
 */
public interface ISqlIndex
    extends ISqlNamedModelElement {

    /** Returns the column. */
    ISqlColumn getColumn();

    /**
     * Sets the column.
     *
     * @param column The new value for column.
     */
    void setColumn( ISqlColumn column );

}
