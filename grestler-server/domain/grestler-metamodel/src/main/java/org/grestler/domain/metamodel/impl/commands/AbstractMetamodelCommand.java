//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.commands.IMetamodelCommand;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext;

import javax.json.JsonObject;

/**
 * Partial implementation of IMetamodelCommand.
 */
abstract class AbstractMetamodelCommand<R extends IMetamodelCommandSpi.CmdRecord>
    implements IMetamodelCommand, IMetamodelCommandSpi<R> {

    /**
     * Constructs a new command.
     */
    protected AbstractMetamodelCommand(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter<R> cmdWriter
    ) {
        this.cmdWriter = cmdWriter;
        this.metamodelRepository = metamodelRepository;
    }

    @Override
    public void execute( JsonObject jsonCmdArgs ) {

        R record = this.parseJson( jsonCmdArgs );

        this.cmdWriter.execute( record, this );
    }

    @Override
    public void finish( R record ) {
        StmTransactionContext.doInReadWriteTransaction( 1, () -> this.writeChangesToMetamodel( record ) );
    }

    /**
     * @return The metamodel repository to be acted upon by this command.
     */
    protected IMetamodelRepositorySpi getMetamodelRepository() {
        return this.metamodelRepository;
    }

    /**
     * Parses the JSON into a concrete record for the command completion.
     *
     * @param jsonCmdArgs the raw JSON to parse.
     *
     * @return the record for use in the rest of the command.
     */
    protected abstract R parseJson( JsonObject jsonCmdArgs );

    /**
     * Makes the in-memory changes needed to complete this command.
     *
     * @param record the JSON for the changes.
     */
    protected abstract void writeChangesToMetamodel( R record );

    /** The persistence provider for this command. */
    private final IMetamodelCommandWriter<R> cmdWriter;

    /** The metamodel repository to be acted upon by this command. */
    private final IMetamodelRepositorySpi metamodelRepository;

}
