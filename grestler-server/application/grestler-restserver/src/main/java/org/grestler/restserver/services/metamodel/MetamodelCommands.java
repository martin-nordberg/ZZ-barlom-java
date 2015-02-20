//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.services.metamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.cmdquery.IMetamodelCommand;
import org.grestler.metamodel.api.cmdquery.IMetamodelCommandFactory;
import org.grestler.utilities.exceptions.IValidationError;
import org.grestler.utilities.revisions.StmTransactionContext;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.JsonReaderFactory;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
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
    @Path( "/{commandTypeName}" )
    @Consumes( "application/json" )
    @Produces( "application/json" )
    public String executeCommand( String jsonCommandArgsStr, @PathParam( "commandTypeName" ) String commandTypeName ) {

        MetamodelCommands.LOG.info( "Executing command {}.", commandTypeName );

        // Parse the incoming JSON into a JSON object.
        JsonObject jsonCmdArgs = this.parseJsonCmdArgs( jsonCommandArgsStr );

        // Make the command by name.
        IMetamodelCommand command = this.makeCommand( commandTypeName );

        // Execute the command.
        return MetamodelCommands.execute( command, jsonCmdArgs );

    }

    /**
     * Executes a given command with given arguments.
     *
     * @param command     the command to execute.
     * @param jsonCmdArgs the JSON arguments for the command.
     *
     * @return JSON for the success of the execution.
     */
    private static String execute( IMetamodelCommand command, JsonObject jsonCmdArgs ) {

        try {

            // Execute the command.
            StmTransactionContext.doInReadWriteTransaction(
                1, () -> command.execute( jsonCmdArgs )
            );

            // Return JSON for success.
            return "{ \"success\": true, \"cmdId\": \"" + command.getCmdId() + "\" }";

        }
        catch ( Exception e ) {

            if ( e instanceof IValidationError ) {
                IValidationError ev = (IValidationError) e;

                switch ( ev.getValidationType() ) {
                    case DUPLICATE_ENTITY_CREATION:
                    case RELATED_ENTITY_NOT_FOUND:
                        // TODO: 422 error code instead, but it's not in RESTEasy
                        throw new BadRequestException( ev.getValidationMessage() );
                    case NONSPECIFIC:
                    default:
                        throw new BadRequestException( ev.getValidationMessage() );
                }
            }
            else {
                throw new BadRequestException( e.getMessage(), e );
            }

        }

    }

    /**
     * Makes a new command of the given named type.
     *
     * @param commandTypeName the type of command to create.
     *
     * @return the created command.
     *
     * @throws NotFoundException if the command factory does not recognize the command type.
     */
    private IMetamodelCommand makeCommand( String commandTypeName ) {

        try {
            return this.commandFactory.makeCommand( commandTypeName );
        }
        catch ( Exception e ) {
            throw new NotFoundException( "Failed to create command of type: " + commandTypeName + ".", e );
        }

    }

    /**
     * Parses the incoming JSON for the command's arguments.
     *
     * @param jsonCommandArgsStr the incoming JSON to parse.
     *
     * @return a JSON object with the result.
     *
     * @throws BadRequestException if the parsing fails.
     */
    private JsonObject parseJsonCmdArgs( String jsonCommandArgsStr ) {

        try {
            return this.jsonReaderFactory.createReader( new StringReader( jsonCommandArgsStr ) ).readObject();
        }
        catch ( Exception e ) {
            throw new BadRequestException( "Invalid JSON command argument.", e );
        }

    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** The command factory for creating commands. */
    private final IMetamodelCommandFactory commandFactory;

    /** Factory for creating JSON readers. */
    private final JsonReaderFactory jsonReaderFactory;

}
