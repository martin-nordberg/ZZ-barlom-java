package org.barlom.persistence.postgresql;

import org.barlom.domain.metamodel.api.IMetamodelFacade;
import org.barlom.infrastructure.utilities.configuration.IConfiguration;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.postgresql.impl.PostgreSqlDataSource;
import org.barlom.persistence.postgresql.impl.PostgreSqlMetamodelFacade;

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

    public GdbPostgreSqlSubsystem( String dataSourceName ) {
        this( dataSourceName, null );
    }

    public GdbPostgreSqlSubsystem( String dataSourceName, IConfiguration clientConfiguration ) {
        this.clientConfiguration = clientConfiguration;
        this.dataSourceName = dataSourceName;
    }

    /**
     * Constructs a new data source for the given data source name following the configuration of this subsystem.
     * Retrieves a singleton instance per subsystem configuration after the initial call.
     *
     * @return the newly created data source.
     */
    public IDataSource provideDataSource() {

        if ( this.dataSource == null ) {
            this.dataSource = new PostgreSqlDataSource( Token.INSTANCE, this.dataSourceName, this.clientConfiguration );
        }

        return this.dataSource;

    }

    /**
     * Constructs a new PostgreSQL metamodel facade. Retrieves a singleton instance per subsystem configuration after
     * the initial call.
     *
     * @return the new metamodel facade.
     */
    public IMetamodelFacade provideMetamodelFacade() {

        this.provideDataSource();

        if ( this.metamodelFacade == null ) {
            this.metamodelFacade = new PostgreSqlMetamodelFacade( Token.INSTANCE, this.dataSource, this.clientConfiguration );
        }

        return this.metamodelFacade;

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

    /**
     * The singleton metamodel facade within this subsystem.
     */
    private PostgreSqlMetamodelFacade metamodelFacade = null;

}
