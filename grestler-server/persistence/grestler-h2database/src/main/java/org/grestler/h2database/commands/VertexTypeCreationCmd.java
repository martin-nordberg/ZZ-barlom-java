//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.commands;

import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.IMetamodelCommand;
import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.utilities.configuration.Configuration;
import org.grestler.utilities.uuids.Uuids;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Command to create a vertex type.
 */
public class VertexTypeCreationCmd
    implements IMetamodelCommand {

    /**
     * Constructs a new vertex type creation command.
     */
    public VertexTypeCreationCmd( IDataSource dataSource ) {
        this.id = Uuids.makeUuid();
        this.dataSource = dataSource;
    }

    @Override
    public void execute( JsonObject jsonCommandArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID vertexTypeId = UUID.fromString( jsonCommandArgs.getString( "id" ) );
        UUID parentPackageId = UUID.fromString( jsonCommandArgs.getString( "parentPackageId" ) );
        String name = jsonCommandArgs.getString( "name" );
        UUID superTypeId = UUID.fromString( jsonCommandArgs.getString( "superTypeId" ) );
        EAbstractness abstractness = EAbstractness.valueOf( jsonCommandArgs.getString( "abstractness" ) );

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", vertexTypeId );
        args.put( "parentPackageId", parentPackageId );
        args.put( "name", name );
        args.put( "superTypeId", superTypeId );
        args.put( "isAbstract", abstractness.isAbstract() );

        // Read the SQL command.
        Configuration config = new Configuration( H2DatabaseModule.class );
        String sql = config.readString( "VertexType.Insert" );

        // Insert the new vertex type record.
        try ( IConnection connection = this.dataSource.openConnection() ) {

            connection.executeInTransaction(
                () -> connection.executeCommand( sql, args )
            );

        }

        // TODO: handle database validation problems or connection problems
    }

    @Override
    public UUID getId() {
        return this.id;
    }


    private final IDataSource dataSource;

    private final UUID id;

}
