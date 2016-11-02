//
// (C) Copyright 2014-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.persistence.dbutilities.flyway.FlywayLog4j2Creator;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.logging.LogFactory;

import javax.sql.DataSource;

/**
 * Utility class to update the database schema.
 */
final class DatabaseMigration {

    /**
     * Static utility class not intended for instantiation.
     *
     * @param dataSource the data source for the database to be migrated.
     */
    DatabaseMigration( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    /**
     * Updates the database schema to the latest version.
     *
     * @param extraMigrationClasspath extra classpath folders that should be read and migrated after the base schema
     *                                definition.
     */
    void updateDatabaseSchema( String extraMigrationClasspath ) {

        DatabaseMigration.LOG.info( "Updating database schema..." );

        // Send Flyway logging to Log4j2.
        LogFactory.setLogCreator( new FlywayLog4j2Creator() );

        // Create the Flyway instance.
        Flyway flyway = new Flyway();

        // Point it to the database.
        flyway.setDataSource( this.dataSource );

        // Build the path for migrations.
        String migrationClasspath = DatabaseMigration.SCHEMA_MIGRATION_CLASSPATHS;
        if ( !extraMigrationClasspath.isEmpty() ) {
            migrationClasspath += ";";
            migrationClasspath += extraMigrationClasspath;
        }

        DatabaseMigration.LOG.info( "Flyway migration classpath: {}", migrationClasspath );

        flyway.setLocations( migrationClasspath.split( ";" ) );
        flyway.setSqlMigrationPrefix( "V" );

        // Start the migration.
        flyway.migrate();

    }

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * The base classpath locations for defining the schema.
     */
    private static final String SCHEMA_MIGRATION_CLASSPATHS = "classpath:db/migration";

    /**
     * The data source for the database to migrate.
     */
    private final DataSource dataSource;

}
