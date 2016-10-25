//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
    SqlRecord( SqlTable parent, Map<String, Object> columnValues ) {
        super( parent, null );

        this.columnValues = new HashMap<>( columnValues );

        parent.onAddChild( this );
    }

    @Override
    public Object getColumnValue( String key ) {

        String simplifiedKey = key.toUpperCase();
        simplifiedKey = SqlRecord.SPACE_PATTERN.matcher( simplifiedKey ).replaceAll( "" );
        simplifiedKey = SqlRecord.UNDERSCORE_PATTERN.matcher( simplifiedKey ).replaceAll( "" );

        return this.columnValues.get( simplifiedKey );
    }

    @Override
    public Map<String, Object> getColumnValues() {
        return this.columnValues;
    }

    @Override
    public String toString() {
        return "[columnValues=" + this.columnValues + "]";
    }

    private static final Pattern SPACE_PATTERN = Pattern.compile( " " );

    private static final Pattern UNDERSCORE_PATTERN = Pattern.compile( "_" );

    /** Mapping from column name to SQL-formatted value. */
    private final Map<String, Object> columnValues;

}
