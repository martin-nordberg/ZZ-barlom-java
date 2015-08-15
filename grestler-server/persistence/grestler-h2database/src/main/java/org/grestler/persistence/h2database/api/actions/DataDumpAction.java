//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.infrastructure.utilities.functions.IAction;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.api.exceptions.H2DatabaseException;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Command to dump the entire repository to a backup SQL file.
 */
public final class DataDumpAction
    implements IAction {

    /**
     * Constructs a new data dump action.
     */
    public DataDumpAction( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void perform() {

        // Read the SQL command.
        String scriptCmd = "SCRIPT SIMPLE";

        // TODO: get the dump file name from the database name
        String dumpFilePath = "/home/mnordberg/Workspace/grestler/steamflake-server/persistence/steamflake-metametamodel/src/main/resources/org/steamflake/persistence/metametamodel/DataDump.sql";

        try ( PrintWriter dumpFile = new PrintWriter( new FileWriter( dumpFilePath ) ) ) {

            // Run the script command, writing each INSERT command to the dump file.
            this.dataSource.openConnection().executeQuery(
                row -> {
                    String line = row.getString( "SCRIPT" );
                    if ( line.startsWith( "INSERT" ) ) {
                        dumpFile.println( line );
                    }
                },
                scriptCmd
            );

        }
        catch ( Exception e ) {
            throw new H2DatabaseException( DataDumpAction.LOG, "Failed to dump SQL data.", e );
        }
    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    private final IDataSource dataSource;
}


