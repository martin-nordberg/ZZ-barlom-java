//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.dbutilities.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.persistence.dbutilities.api.DatabaseException;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.spi.IResultSetSpi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

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
    public int executeCommand( String sqlQuery, Map<String, Object> args ) {

        assert sqlQuery.startsWith( "INSERT" ) || sqlQuery.startsWith( "UPDATE" ) || sqlQuery.startsWith( "DELETE" );

        AbstractConnection.LOG.debug( "Executing command: {}.", sqlQuery );
        AbstractConnection.LOG.debug( "Command Arguments: {}.", args.toString() );

        try {

            try ( PreparedStatement stmt = this.prepareStatement( sqlQuery, args ) ) {
                return stmt.executeUpdate();
            }

        }
        catch ( SQLException e ) {
            this.throwException( "Failed to execute SQL command: \"" + sqlQuery + "\"", e );
            return 0;
        }

    }

    @Override
    public void executeInTransaction( ITransactionalCallback transactionalCallback ) {

        // Start the transaction.
        this.startTransaction();

        try {
            // Execute the task.
            transactionalCallback.execute();

            // Commit the transaction.
            this.connection.commit();
        }
        catch ( DatabaseException e ) {
            // On error rollback the transaction.
            this.rollbackTransaction();

            // Just rethrow the lower level database exception.
            throw e;
        }
        catch ( Exception e ) {
            // On error rollback the transaction.
            this.rollbackTransaction();

            // Wrap the exception.
            this.throwException( "Transaction failed.", e );
        }

    }

    @Override
    public void executeOneRowCommand( String sqlQuery, Map<String, Object> args ) {
        int rows = this.executeCommand( sqlQuery, args );
        if ( rows != 1 ) {
            this.throwException( "Command expected to affect one record but affected " + rows + ": " + sqlQuery + " with parameters " + args + "." );
        }

    }

    @Override
    public void executeQuery( IQueryCallback queryCallback, String sqlQuery ) {

        assert sqlQuery.startsWith( "SELECT" );

        AbstractConnection.LOG.debug( "Executing query: {}.", sqlQuery );

        try {
            try ( Statement stmt = this.connection.createStatement() ) {

                try ( IResultSetSpi resultSet = this.makeResultSet( stmt.executeQuery( sqlQuery ) ) ) {

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

    /**
     * Creates a concrete result set wrapping a given JDBC result set.
     *
     * @param resultSet the JDBC result set to be wrapped.
     */
    protected abstract IResultSetSpi makeResultSet( ResultSet resultSet );

    /**
     * Throws an exception with no underlying database exception.
     *
     * @param message the message for the exception.
     *
     * @throws DatabaseException always thrown by this method.
     */
    protected abstract void throwException( String message ) throws DatabaseException;

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
     * Creates a prepared statement
     *
     * @param sqlQuery the query with named parameters.
     * @param args     the named arguments to substitute into the query.
     *
     * @return the JDBC prepared statement.
     *
     * @throws SQLException if the preparation fails.
     */
    private PreparedStatement prepareStatement( String sqlQuery, Map<String, Object> args ) throws SQLException {

        // Replace named parameter placeholders by "?" ...
        SqlNamedParameterParser.ParseResult parsedSql = SqlNamedParameterParser.parseSqlParameters( sqlQuery, args );

        // Create the prepared statement.
        PreparedStatement result = this.connection.prepareStatement( parsedSql.sql );

        // Set the arguments.
        int i = 1;
        for ( Object arg : parsedSql.arguments ) {
            result.setObject( i, arg );
            i += 1;
        }

        return result;
    }

    // TODO: executeQuery with (named) parameters

    /**
     * Rolls back a transaction after an error.
     */
    private void rollbackTransaction() {

        try {
            this.connection.rollback();
        }
        catch ( SQLException e ) {
            this.throwException( "Failed to rollback transaction!", e );
        }

    }

    /**
     * Starts a new transaction.
     */
    private void startTransaction() {

        try {
            this.connection.setAutoCommit( false );
        }
        catch ( SQLException e ) {
            this.throwException( "Failed to open transaction.", e );
        }

    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** The wrapped JDBC connection. */
    private final Connection connection;

}
