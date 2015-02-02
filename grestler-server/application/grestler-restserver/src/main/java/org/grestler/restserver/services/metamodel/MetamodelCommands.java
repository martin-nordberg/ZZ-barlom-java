//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.services.metamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.IMetamodelCommand;
import org.grestler.metamodel.api.IMetamodelCommandFactory;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.StringReader;

/**
 * JAX-RS service supporting commands for a metamodel repository.
 */
@Path( "/metadata/commands" )
public class MetamodelCommands {

    /**
     * Constructs a new edge type query service backed by given metamodel repository.
     *
     * @param commandFactory    the factory for making new commands.
     * @param jsonParserFactory parser factory for incoming JSON.
     */
    @Inject
    public MetamodelCommands( IMetamodelCommandFactory commandFactory, JsonParserFactory jsonParserFactory ) {

        this.commandFactory = commandFactory;
        this.jsonParserFactory = jsonParserFactory;

    }

    @POST
    @Path( "/{commandName}" )
    @Consumes( "application/json" )
    @Produces( "application/json" )
    public String getEdgeTypeByIdOrPath( String jsonCommandArgsStr, @PathParam( "commandName" ) String commandName ) {

        MetamodelCommands.LOG.info( "Executing command {}.", commandName );

        JsonParser jsonCommandArgs = this.jsonParserFactory.createParser( new StringReader( jsonCommandArgsStr ) );

        IMetamodelCommand command = this.commandFactory.makeCommand( commandName, jsonCommandArgs );

        try {
            command.execute();
            return "{ \"success\": true, \"id\": \"" + command.getId() + "\" }";
        }
        catch ( Exception e ) {
            // TODO: 422 error for failed validation
            // TODO: 409 error for duplication
            return "{ \"success\": false, \"id\": \"\" + command.getId() + \"\\, \"message\": \"" + e.getMessage() + "\" }";
        }
    }

    private static final Logger LOG = LogManager.getLogger();

    private final IMetamodelCommandFactory commandFactory;

    private final JsonParserFactory jsonParserFactory;

}
