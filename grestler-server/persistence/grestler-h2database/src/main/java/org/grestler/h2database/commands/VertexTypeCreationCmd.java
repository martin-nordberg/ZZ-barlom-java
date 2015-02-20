//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.h2database.H2DatabaseException;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.impl.cmdquery.AbstractMetamodelCommand;
import org.grestler.utilities.configuration.Configuration;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Command to create a vertex type.
 */
public class VertexTypeCreationCmd
    extends AbstractMetamodelCommand {

    /**
     * Constructs a new vertex type creation command.
     *
     * @param dataSource the data source in which to save the new vertex type.
     */
    public VertexTypeCreationCmd( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void execute( JsonObject jsonCmdArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        UUID parentPackageId = UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) );
        String name = jsonCmdArgs.getString( "name" );
        UUID superTypeId = UUID.fromString( jsonCmdArgs.getString( "superTypeId" ) );
        EAbstractness abstractness = EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) );

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", id );
        args.put( "parentPackageId", parentPackageId );
        args.put( "name", name );
        args.put( "superTypeId", superTypeId );
        args.put( "isAbstract", abstractness.isAbstract() );

        args.put( "cmdId", this.getCmdId() );
        args.put( "jsonCmdArgs", jsonCmdArgs.toString() );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "VertexType.Insert" );

        // Insert the new vertex type record and the command itself.
        try ( IConnection connection = this.dataSource.openConnection() ) {

            connection.executeInTransaction(
                () -> {
                    for ( String sqlInsert : sqlInserts ) {
                        int count = connection.executeCommand( sqlInsert, args );
                        if ( count != 1 ) {
                            throw new H2DatabaseException(
                                VertexTypeCreationCmd.LOG, "Expected to insert one record but inserted " + count + "."
                            );
                        }
                    }
                }
            );

        }

        // TODO: handle database validation problems or connection problems
    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** The data source in which to create the vertex type. */
    private final IDataSource dataSource;

}
