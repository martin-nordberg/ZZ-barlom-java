//
// (C) Copyright 2014-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql.impl;

import org.apache.logging.log4j.LogManager;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.postgresql.PostgreSqlSubsystem;
import org.barlom.persistence.postgresql.api.PostgreSqlDatabaseException;
import org.postgresql.ds.PGPoolingDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Wrapper for an PostgreSQL data source providing pooled connections.
 */
public final class PostgreSqlDataSource
    implements IDataSource {

    /**
     * Constructs a new PostgreSQL data source.
     *
     * @param token          token ensures that only the subsystem can construct a new data source.
     * @param dataSourceName the name of the data source.
     * @param serverName     the server host name.
     * @param portNumber     the server port number.
     * @param databaseName   the database name to connect to.
     * @param currentSchema  the schema to operate within.
     * @param user           the connecting user name.
     * @param password       the user's password.
     * @param maxConnections the maximum number of connections to keep open in the pool.
     */
    public PostgreSqlDataSource(
        @SuppressWarnings( "UnusedParameters" ) PostgreSqlSubsystem.Token token,
        String dataSourceName,
        String serverName,
        int portNumber,
        String databaseName,
        String currentSchema,
        String user,
        String password,
        int maxConnections
    ) {

        PostgreSqlDataSource.LOG.info( "Opening data source {}: User Name = {}, Database = {}, Schema = {}.",
                                       dataSourceName, user, databaseName
        );

        this.connectionPool = new PGPoolingDataSource();
        this.connectionPool.setDataSourceName( dataSourceName );
        this.connectionPool.setServerName( serverName );
        this.connectionPool.setPortNumber( portNumber );
        this.connectionPool.setDatabaseName( databaseName );
        this.connectionPool.setCurrentSchema( currentSchema );
        this.connectionPool.setUser( user );
        this.connectionPool.setPassword( password );
        this.connectionPool.setMaxConnections( maxConnections );

    }

    @Override
    public void close() {

        PostgreSqlDataSource.LOG.info( "Closing data source" );

        this.connectionPool.close();

    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }

    @Override
    public Connection getConnection( String username, String password ) throws SQLException {
        return this.connectionPool.getConnection( username, password );
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.connectionPool.getLogWriter();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.connectionPool.getLoginTimeout();
    }

    @Override
    public String getName() {
        return this.connectionPool.getDataSourceName();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException( "Parent logger not supported" );
    }

    @Override
    public boolean isWrapperFor( Class<?> iface ) throws SQLException {
        return this.connectionPool.isWrapperFor( iface );
    }

    @Override
    public IConnection openConnection() {
        try {
            return new PostgreSqlConnection( this.getConnection() );
        }
        catch ( SQLException e ) {
            throw new PostgreSqlDatabaseException( PostgreSqlDataSource.LOG, "Failed to open connection.", e );
        }
    }

    @Override
    public void setLogWriter( PrintWriter out ) throws SQLException {
        this.connectionPool.setLogWriter( out );
    }

    @Override
    public void setLoginTimeout( int seconds ) throws SQLException {
        this.connectionPool.setLoginTimeout( seconds );
    }

    @Override
    public <T> T unwrap( Class<T> iface ) throws SQLException {
        return this.connectionPool.unwrap( iface );
    }

    /**
     * The logger for this class.
     */
    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger();

    /**
     * The underlying PostgreSQL JDBC connection pool.
     */
    private final PGPoolingDataSource connectionPool;

}
