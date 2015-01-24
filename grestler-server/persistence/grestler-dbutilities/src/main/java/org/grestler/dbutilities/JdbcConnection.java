//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Thin, focused wrapper for a JDBC connection.
 */
public class JdbcConnection
    implements AutoCloseable {

    /**
     * Constructs a new JDBC connection wrapper.
     *
     * @param dataSource the data source for getting the connection.
     *
     * @throws SQLException if a database call fails.
     */
    public JdbcConnection( DataSource dataSource ) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }

    /**
     * Executes a single SQL select query. Calls a callback for each record found.
     *
     * @param queryCallback the callback function called with each record of the result.
     * @param sqlQuery      the text of the SQL query to execute.
     *
     * @throws SQLException if the query process fails.
     */
    public void executeQuery( IQueryCallback queryCallback, String sqlQuery ) throws SQLException {

        assert sqlQuery.startsWith( "SELECT" );

        JdbcConnection.LOG.info( "Executing query: {}.", sqlQuery );

        try ( PreparedStatement stmt = this.connection.prepareStatement( sqlQuery ) ) {

            try ( ResultSet resultSet = stmt.executeQuery() ) {

                // Call back with each record.
                while ( resultSet.next() ) {
                    queryCallback.handleRecord( resultSet );
                }

            }

        }

    }

    // TODO: executeQuery with (named) parameters

    // TODO: executeCommand (with named parameters)

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /** The wrapped JDBC connection. */
    private final Connection connection;

    /**
     * Interface for query result callbacks.
     */
    @FunctionalInterface
    public interface IQueryCallback {

        /**
         * Callback receiving the result set for each row of a query.
         *
         * @param resultSet the result set on a given row.
         *
         * @throws SQLException if reading the result fails.
         */
        void handleRecord( ResultSet resultSet ) throws SQLException;

    }

}
