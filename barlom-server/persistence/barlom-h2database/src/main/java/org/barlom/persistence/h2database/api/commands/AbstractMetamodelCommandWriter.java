//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.commands;

import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandSpi;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.dbutilities.api.IDataSource;

/**
 * Abstract H2 database command writer.
 */
abstract class AbstractMetamodelCommandWriter<R extends IMetamodelCommandSpi.CmdRecord>
    implements IMetamodelCommandWriter<R> {

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
        R record, IMetamodelCommandSpi<R> cmdFinisher
    ) {

        // Insert the new record and the command itself.
        try ( IConnection connection = this.dataSource.openConnection() ) {

            connection.executeInTransaction(
                () -> this.writeCommand( connection, record )
            );

            cmdFinisher.finish( record );
        }

        // TODO: handle database validation problems or connection problems
    }

    /**
     * Performs the database inserts needed to save the command in the given connection.
     *
     * @param connection the database connection on which to write the new command.
     * @param record     the record of attributes for the command.
     */
    protected abstract void writeCommand( IConnection connection, R record );

    /** The data source for executions of this command. */
    private final IDataSource dataSource;

}
