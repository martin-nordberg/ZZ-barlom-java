//
// (C) Copyright 2014-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql;

import org.barlom.infrastructure.utilities.configuration.IConfiguration;
import org.barlom.infrastructure.utilities.configuration.PropertiesFileConfiguration;
import org.barlom.persistence.postgresql.impl.PostgreSqlDataSource;

/**
 * Factory interface for the PostgreSQL subsystem.
 */
public class PostgreSqlSubsystem {

    /**
     * Do-nothing class with private constructor ensures that subsystem services are never created except via this
     * subsystem.
     */
    public static final class Token {

        private Token() {
        }

        private static final Token INSTANCE = new Token();
    }

    public PostgreSqlSubsystem( String dataSourceName ) {
        this( dataSourceName, null );
    }

    public PostgreSqlSubsystem( String dataSourceName, IConfiguration clientConfiguration ) {
        this.clientConfiguration = clientConfiguration;
        this.dataSourceName = dataSourceName;
    }

    /**
     * Constructs a new data source for the given data source name following the configuration of this subsystem.
     * Retrieves a singleton instance per subsystem configuration after the initial call.
     *
     * @return the newly created data source.
     */
    public PostgreSqlDataSource provideDataSource() {

        if ( this.dataSource == null ) {
            this.dataSource = this.makeDataSource();
        }

        return this.dataSource;

    }

    private PostgreSqlDataSource makeDataSource() {

        // Read the database configuration.
        IConfiguration config = new PropertiesFileConfiguration( PostgreSqlDataSource.class,
                                                                 this.clientConfiguration, this.dataSourceName );

        String serverName = config.readString( "serverName" );
        int portNumber = config.readInt( "portNumber" );
        String databaseName = config.readString( "databaseName" );
        String currentSchema = config.readString( "currentSchema" );
        String user = config.readString( "user" );
        String password = config.readString( "password" );
        int maxConnections = config.readInt( "maxConnections" );

        return new PostgreSqlDataSource(
            Token.INSTANCE,
            this.dataSourceName,
            serverName,
            portNumber,
            databaseName,
            currentSchema,
            user,
            password,
            maxConnections
        );

    }

    /**
     * Optional client-provided configuration override.
     */
    private final IConfiguration clientConfiguration;

    /**
     * The singleton data source within this subsystem.
     */
    private PostgreSqlDataSource dataSource = null;

    /**
     * The name of the data source to be created by this subsystem.
     */
    private final String dataSourceName;

}
