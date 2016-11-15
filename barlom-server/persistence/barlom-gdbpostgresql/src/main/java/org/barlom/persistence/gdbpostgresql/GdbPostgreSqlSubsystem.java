//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.gdbpostgresql;

import org.barlom.domain.metamodel.api.IMetamodelFacade;
import org.barlom.infrastructure.utilities.configuration.IConfiguration;
import org.barlom.infrastructure.utilities.configuration.PropertiesFileConfiguration;
import org.barlom.persistence.gdbpostgresql.impl.PostgreSqlMetamodelFacade;
import org.barlom.persistence.postgresql.PostgreSqlSubsystem;
import org.barlom.persistence.postgresql.impl.PostgreSqlDataSource;

/**
 * Factory interface for the GDB PostgreSQL subsystem.
 */
public class GdbPostgreSqlSubsystem {

    /**
     * Do-nothing class with private constructor ensures that subsystem services are never created except via this
     * subsystem.
     */
    public static final class Token {

        private Token() {
        }

        private static final Token INSTANCE = new Token();
    }

    public GdbPostgreSqlSubsystem( PostgreSqlSubsystem postgreSqlSubsystem ) {
        this( postgreSqlSubsystem, null );
    }

    public GdbPostgreSqlSubsystem( PostgreSqlSubsystem postgreSqlSubsystem, IConfiguration clientConfiguration ) {
        this.clientConfiguration = clientConfiguration;
        this.postgreSqlSubsystem = postgreSqlSubsystem;
    }

    /**
     * Constructs a new PostgreSQL metamodel facade. Retrieves a singleton instance per subsystem configuration after
     * the initial call.
     *
     * @return the new metamodel facade.
     */
    public IMetamodelFacade provideMetamodelFacade() {

        if ( this.metamodelFacade == null ) {
            this.metamodelFacade = this.makeMetamodelFacade();
        }

        return this.metamodelFacade;

    }

    private PostgreSqlMetamodelFacade makeMetamodelFacade() {

        PostgreSqlDataSource dataSource = this.postgreSqlSubsystem.provideDataSource();

        // Read the database migration configuration.
        IConfiguration config = new PropertiesFileConfiguration(
            PostgreSqlMetamodelFacade.class, this.clientConfiguration
        );

        String extraMigrationLocations = config.readString( "extraMigrationLocations" );

        return new PostgreSqlMetamodelFacade( Token.INSTANCE, dataSource, extraMigrationLocations );

    }

    /**
     * Optional client-provided configuration override.
     */
    private final IConfiguration clientConfiguration;

    /**
     * The singleton metamodel facade within this subsystem.
     */
    private PostgreSqlMetamodelFacade metamodelFacade = null;

    /**
     * Subsystem providing the data source behind the metamodel.
     */
    private final PostgreSqlSubsystem postgreSqlSubsystem;

}
