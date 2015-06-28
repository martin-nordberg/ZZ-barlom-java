//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An enumeration of fundamental SQL data types.
 */
@SuppressWarnings( "javadoc" )
public enum ESqlDataType {

    BLOB,

    BOOLEAN,

    DATE,

    INTEGER,

    NVARCHAR2,

    TIMESTAMP,

    VARCHAR2;

    public String getSqlType( Integer size, Integer precision ) {

        switch ( this ) {
            case INTEGER:
                if ( size == null ) {
                    return "INTEGER";
                }
                if ( precision == null ) {
                    return "NUMBER(" + size + ")";
                }
                return "NUMBER(" + size + "," + precision + ")";
            case VARCHAR2:
                if ( size == null ) {
                    return "VARCHAR2";
                }
                return "VARCHAR2(" + size + ")";
            case BOOLEAN:
                return "NUMBER(1,0)";
            case NVARCHAR2:
                if ( size == null ) {
                    return "NVARCHAR2";
                }
                return "NVARCHAR2(" + size + ")";
            default:
                return super.toString();
        }
    }

    public String getValueForSql( Object value ) {
        if ( this == VARCHAR2 ) {
            return "'" + value + "'";
        }

        if ( this == DATE ) {
            return "TO_DATE( '" + this.dateMmDdYyyy( (Date) value ) + "', 'MM-DD-YYYY' )";
        }

        if ( value instanceof Boolean ) {
            if ( (Boolean) value ) {
                return "1";
            }
            return "0";
        }

        return value.toString();
    }

    private String dateMmDdYyyy( Date value ) {
        DateFormat dateFormat = new SimpleDateFormat( "MM-dd-yyyy" );
        return dateFormat.format( value );
    }
}
