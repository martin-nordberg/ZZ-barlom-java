//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.dbutilities.api.DatabaseException;
import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.spi.IResultSetSpi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Thin, focused wrapper for a JDBC connection.
 */
public abstract class AbstractConnection
    implements IConnection {

    /**
     * Constructs a new JDBC connection wrapper.
     *
     * @param connection the encapsulated connection.
     */
    protected AbstractConnection( Connection connection ) {
        this.connection = connection;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        }
        catch ( SQLException e ) {
            this.throwException( "Failed to close connection.", e );
        }
    }

    @Override
    public void executeQuery( IQueryCallback queryCallback, String sqlQuery ) {

        assert sqlQuery.startsWith( "SELECT" );

        AbstractConnection.LOG.info( "Executing query: {}.", sqlQuery );

        try {
            try ( PreparedStatement stmt = this.connection.prepareStatement( sqlQuery ) ) {

                try ( IResultSetSpi resultSet = this.makeResultSet( stmt.executeQuery() ) ) {

                    // Call back with each record.
                    while ( resultSet.next() ) {
                        queryCallback.handleRecord( resultSet );
                    }

                }

            }
        }
        catch ( SQLException e ) {
            this.throwException( "Failed to complete SQL query \"" + sqlQuery + "\"", e );
        }

    }

    // TODO: executeQuery with (named) parameters

    // TODO: executeCommand (with named parameters)

    /**
     * Creates a concrete result set wrapping a given JDBC result set.
     *
     * @param resultSet the JDBC result set to be wrapped.
     */
    protected abstract IResultSetSpi makeResultSet( ResultSet resultSet );

    /**
     * Throws an exception wrapping the underlying SQLException from JDBC.
     *
     * @param message the message for the exception.
     * @param cause   the internal exception causing the problem.
     *
     * @throws DatabaseException always thrown by this method.
     */
    protected abstract void throwException( String message, Throwable cause ) throws DatabaseException;

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /** The wrapped JDBC connection. */
    private final Connection connection;

}
