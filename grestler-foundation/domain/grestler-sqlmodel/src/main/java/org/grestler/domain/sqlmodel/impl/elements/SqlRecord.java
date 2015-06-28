//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a single table record in the database.
 */
public class SqlRecord
    extends SqlModelElement
    implements ISqlRecord {

    /**
     * Constructs a new record.
     *
     * @param parent The table the record is part of.
     */
    SqlRecord( SqlTable parent, Map<String, Object> columnValues, Boolean isUnitTestValue ) {
        super( parent, null );

        this.columnValues = new HashMap<>( columnValues );
        this.isUnitTestValue = isUnitTestValue;

        parent.onAddChild( this );
    }

    @SuppressWarnings( "DynamicRegexReplaceableByCompiledPattern" )
    @Override
    public Object getColumnValue( String key ) {

        String simplifiedKey = key.toUpperCase();
        simplifiedKey = simplifiedKey.replaceAll( " ", "" );
        simplifiedKey = simplifiedKey.replaceAll( "_", "" );

        return this.columnValues.get( simplifiedKey );
    }

    @Override
    public Map<String, Object> getColumnValues() {
        return this.columnValues;
    }

    @Override
    public boolean isUnitTestValue() {
        return this.isUnitTestValue;
    }

    @Override
    public String toString() {
        return "[columnValues=" + this.columnValues + ", isUnitTestValue=" + this.isUnitTestValue
            + "]";
    }

    /** Mapping from column name to SQL-formatted value. */
    private final Map<String, Object> columnValues;

    private final Boolean isUnitTestValue;

}
