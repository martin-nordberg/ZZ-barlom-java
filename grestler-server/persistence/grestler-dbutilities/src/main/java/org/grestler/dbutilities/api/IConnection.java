//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities.api;

/**
 * Interface to a database connection.
 */
public interface IConnection
    extends AutoCloseable {

    /**
     * @throws DatabaseException if closing fails.
     */
    @Override
    void close();

    /**
     * Executes a single SQL select query. Calls a callback for each record found.
     *
     * @param queryCallback the callback function called with each record of the result.
     * @param sqlQuery      the text of the SQL query to execute.
     *
     * @throws DatabaseException if the query process fails.
     */
    void executeQuery( IQueryCallback queryCallback, String sqlQuery );

    /**
     * Interface for query result callbacks.
     */
    @FunctionalInterface
    interface IQueryCallback {

        /**
         * Callback receiving the result set for each row of a query.
         *
         * @param resultSet the result set on a given row.
         *
         * @throws DatabaseException if reading the result fails.
         */
        void handleRecord( IResultSet resultSet );

    }
}
