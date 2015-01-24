//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.impl;

import org.apache.logging.log4j.LogManager;
import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.h2database.H2DatabaseException;
import org.grestler.h2database.migration.DatabaseMigration;
import org.grestler.utilities.configuration.Configuration;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Wrapper for an H2 data source with pooled connections.
 */
public final class H2DataSource
    implements IDataSource {

    /**
     * Constructs a new H2 data source.
     */
    public H2DataSource( String dataSourceName ) {

        // Read the database configuration.
        Configuration config = new Configuration( H2DataSource.class );
        String url = config.readString( dataSourceName + ".url" );
        String username = config.readString( dataSourceName + ".username" );
        String password = config.readString( dataSourceName + ".password" );
        String extraMigrationLocations = config.readString( dataSourceName + ".extraMigrationLocations" );

        H2DataSource.LOG.info( "Opening data source {}: URL = {}, User Name = {}", dataSourceName, url, username );

        // Create a connection pool.
        this.connectionPool = JdbcConnectionPool.create( url, username, password );

        // Update the schema if needed.
        new DatabaseMigration( this ).updateDatabaseSchema( extraMigrationLocations );

    }

    @Override
    public void close() {

        H2DataSource.LOG.info( "Closing data source" );

        this.connectionPool.dispose();

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
            return new H2Connection( this.getConnection() );
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Failed to open connection.", e );
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
     * The underlying connection pool delegated to by this data source.
     */
    private final JdbcConnectionPool connectionPool;

}
