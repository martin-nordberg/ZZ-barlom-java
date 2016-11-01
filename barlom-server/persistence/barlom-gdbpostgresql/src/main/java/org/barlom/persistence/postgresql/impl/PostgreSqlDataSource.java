//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql.impl;

import org.apache.logging.log4j.LogManager;
import org.barlom.infrastructure.utilities.configuration.Configuration;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.postgresql.api.exceptions.PostgreSqlDatabaseException;
import org.postgresql.ds.PGPoolingDataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Wrapper for an H2 data source providing pooled connections.
 */
public final class PostgreSqlDataSource
    implements IDataSource {

    /**
     * Constructs a new H2 data source.
     *
     * @param dataSourceName the name of the data source (used in configuration property names).
     */
    public PostgreSqlDataSource( String dataSourceName ) {

        // Read the database configuration.
        Configuration config = new Configuration( PostgreSqlDataSource.class );

        String serverName = config.readString( dataSourceName + ".serverName" );
        int portNumber = config.readInt( dataSourceName + ".portNumber" );
        String databaseName = config.readString( dataSourceName + ".databaseName" );
        String currentSchema = config.readString( dataSourceName + ".currentSchema" );
        String user = config.readString( dataSourceName + ".user" );
        String password = config.readString( dataSourceName + ".password" );
        int maxConnections = config.readInt( dataSourceName + ".maxConnections" );

        String extraMigrationLocations = config.readString( dataSourceName + ".extraMigrationLocations" );

        PostgreSqlDataSource.LOG.info( "Opening data source {}: User Name = {}, Database = {}, Schema = {}.",
                                       dataSourceName, user, databaseName );

        this.connectionPool = new PGPoolingDataSource();
        this.connectionPool.setDataSourceName( dataSourceName );
        this.connectionPool.setServerName( serverName );
        this.connectionPool.setPortNumber( portNumber );
        this.connectionPool.setDatabaseName( databaseName);
        this.connectionPool.setCurrentSchema( currentSchema );
        this.connectionPool.setUser( user );
        this.connectionPool.setPassword( password );
        this.connectionPool.setMaxConnections( maxConnections );

        // Update the schema if needed.
        // TODO new DatabaseMigration( this ).updateDatabaseSchema( extraMigrationLocations );

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

    private final PGPoolingDataSource connectionPool;

}
