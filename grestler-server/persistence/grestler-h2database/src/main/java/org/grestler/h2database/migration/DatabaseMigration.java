//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.migration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.logging.LogFactory;
import org.grestler.dbutilities.flyway.FlywayLog4j2Creator;

import javax.sql.DataSource;

/**
 * Utility class to update the database schema.
 */
public final class DatabaseMigration {

    /**
     * Static utility class not intended for instantiation.
     *
     * @param dataSource the data source for the database to be migrated.
     */
    public DatabaseMigration( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    /**
     * Updates the database schema to the latest version.
     */
    public void updateDatabaseSchema() {

        DatabaseMigration.LOG.info( "Updating database schema..." );

        // Send Flyway logging to Log4j2.
        LogFactory.setLogCreator( new FlywayLog4j2Creator() );

        // Create the Flyway instance.
        Flyway flyway = new Flyway();

        // Point it to the database.
        flyway.setDataSource( this.dataSource );

        // Start the migration.
        flyway.migrate();

    }

    /**
     * The logger for this class.
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * The data source for the database to migrate.
     */
    private final DataSource dataSource;

}
