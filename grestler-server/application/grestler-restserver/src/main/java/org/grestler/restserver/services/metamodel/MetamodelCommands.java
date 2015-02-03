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
import javax.json.JsonObject;
import javax.json.JsonReaderFactory;
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
     * @param jsonReaderFactory parser factory for incoming JSON.
     */
    @Inject
    public MetamodelCommands( IMetamodelCommandFactory commandFactory, JsonReaderFactory jsonReaderFactory ) {

        this.commandFactory = commandFactory;
        this.jsonReaderFactory = jsonReaderFactory;

    }

    @POST
    @Path( "/{commandName}" )
    @Consumes( "application/json" )
    @Produces( "application/json" )
    public String executeCommand( String jsonCommandArgsStr, @PathParam( "commandName" ) String commandName ) {

        MetamodelCommands.LOG.info( "Executing command {}.", commandName );

        JsonObject jsonCmdArgs = this.jsonReaderFactory.createReader( new StringReader( jsonCommandArgsStr ) )
                                                       .readObject();

        IMetamodelCommand command = this.commandFactory.makeCommand( commandName );

        try {
            command.execute( jsonCmdArgs );
            return "{ \"success\": true, \"cmdId\": \"" + command.getCmdId() + "\" }";
        }
        catch ( Exception e ) {
            // TODO: 422 error for failed validation
            // TODO: 409 error for duplication
            return "{ \"success\": false, \"cmdId\": \"\" + command.getCmdId() + \"\\, \"message\": \"" + e.getMessage() + "\" }";
        }
    }

    private static final Logger LOG = LogManager.getLogger();

    private final IMetamodelCommandFactory commandFactory;

    private final JsonReaderFactory jsonReaderFactory;

}
