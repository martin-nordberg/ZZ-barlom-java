//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.util.Map;

/**
 * Representation of a single table record in the database.
 */
public interface ISqlRecord
    extends ISqlModelElement {

    /** Returns the columnValues. */
    Object getColumnValue( String key );

    /** Returns the columnValues. */
    Map<String, Object> getColumnValues();

    /** Returns whether this record is for unit testing (as opposed to reference data). */
    boolean isUnitTestValue();

}
