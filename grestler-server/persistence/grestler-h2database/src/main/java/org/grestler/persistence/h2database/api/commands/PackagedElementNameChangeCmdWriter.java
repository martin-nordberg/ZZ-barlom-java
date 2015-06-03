package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.spi.commands.NamedElementNameChangeCmdRecord;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command writer for persisting a name change to a packaged element.
 */
public final class PackagedElementNameChangeCmdWriter
    extends AbstractMetamodelCommandWriter<NamedElementNameChangeCmdRecord> {

    /**
     * Constructs a new package name change command.
     *
     * @param dataSource the data source in which to save the change.
     */
    PackagedElementNameChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, NamedElementNameChangeCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.id );
        args.put( "name", record.name );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "PackagedElement.Update.Name" );

        // Perform the inserts.
        for ( String sqlInsert : sqlInserts ) {
            connection.executeOneRowCommand( sqlInsert, args );
        }

    }

}
