//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.api.commands;

import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.utilities.configuration.Configuration;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Command to create a vertex type.
 */
public class VertexTypeCreationCmdWriter
    extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new vertex type creation command.
     *
     * @param dataSource the data source in which to save the new vertex type.
     */
    public VertexTypeCreationCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand( IConnection connection, JsonObject jsonCmdArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID cmdId = UUID.fromString( jsonCmdArgs.getString( "cmdId" ) );
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

        args.put( "cmdId", cmdId );
        args.put( "jsonCmdArgs", jsonCmdArgs.toString() );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "VertexType.Insert" );

        // Perform the inserts.
        for ( String sqlInsert : sqlInserts ) {
            connection.executeOneRowCommand( sqlInsert, args );
        }

    }

}
