//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;

import javax.json.JsonObject;

/**
 * Abstract H2 database command writer.
 */
abstract class AbstractMetamodelCommandWriter
    implements IMetamodelCommandWriter {

    /**
     * Constructs a new vertex type creation command.
     *
     * @param dataSource the data source in which to save the new vertex type.
     */
    protected AbstractMetamodelCommandWriter( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void execute(
        JsonObject jsonCmdArgs, IMetamodelCommandSpi cmdFinisher
    ) {

        // Insert the new vertex type record and the command itself.
        try ( IConnection connection = this.dataSource.openConnection() ) {

            connection.executeInTransaction( () -> this.writeCommand( connection, jsonCmdArgs ) );

            cmdFinisher.finish( jsonCmdArgs );
        }

        // TODO: handle database validation problems or connection problems
    }

    /**
     * Performs the database inserts needed to save the command in the given connection.
     *
     * @param connection  the database connection on which to write the new command.
     * @param jsonCmdArgs the JSON for the command.
     */
    protected abstract void writeCommand( IConnection connection, JsonObject jsonCmdArgs );

    /** The data source for executions of this command. */
    private final IDataSource dataSource;

}
