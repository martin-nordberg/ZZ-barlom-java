//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.persistence.dbutilities.api.DatabaseException;
import org.barlom.persistence.dbutilities.impl.AbstractConnection;
import org.barlom.persistence.dbutilities.spi.IResultSetSpi;
import org.barlom.persistence.postgresql.api.exceptions.PostgreSqlDatabaseException;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Concrete connection implementation for the PostgreSQL database.
 */
class PostgreSqlConnection
    extends AbstractConnection {

    /**
     * Constructs a new PostgreSQL connection wrapping the given JDBC connection.
     *
     * @param connection the JDBC connection to wrap.
     */
    PostgreSqlConnection( Connection connection ) {
        super( connection );
    }

    @Override
    protected IResultSetSpi makeResultSet( ResultSet resultSet ) {
        return new PostgreSqlResultSet( resultSet );
    }

    @Override
    protected int throwException( String message ) throws DatabaseException {
        throw new PostgreSqlDatabaseException( PostgreSqlConnection.LOG, message );
    }

    @Override
    protected int throwException( String message, Throwable cause ) throws DatabaseException {
        throw new PostgreSqlDatabaseException( PostgreSqlConnection.LOG, message, cause );
    }

    /** The logger for this class. */
    @SuppressWarnings( "FieldNameHidesFieldInSuperclass" )
    private static final Logger LOG = LogManager.getLogger();

}
