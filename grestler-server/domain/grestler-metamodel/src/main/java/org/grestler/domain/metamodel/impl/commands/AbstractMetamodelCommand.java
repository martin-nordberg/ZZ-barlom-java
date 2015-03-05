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
abstract class AbstractMetamodelCommand
    implements IMetamodelCommand, IMetamodelCommandSpi {

    /**
     * Constructs a new command.
     */
    protected AbstractMetamodelCommand(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter cmdWriter
    ) {
        this.cmdWriter = cmdWriter;
        this.metamodelRepository = metamodelRepository;
    }

    @Override
    public void execute( JsonObject jsonCmdArgs ) {
        this.cmdWriter.execute( jsonCmdArgs, this );
    }

    @Override
    public void finish( JsonObject jsonCmdArgs ) {
        StmTransactionContext.doInReadWriteTransaction( 1, () -> this.writeChangesToMetamodel( jsonCmdArgs ) );
    }

    /**
     * @return The metamodel repository to be acted upon by this command.
     */
    protected IMetamodelRepositorySpi getMetamodelRepository() {
        return this.metamodelRepository;
    }

    /**
     * Makes the in-memory changes needed to complete this command.
     *
     * @param jsonCmdArgs the JSON for the changes.
     */
    protected abstract void writeChangesToMetamodel( JsonObject jsonCmdArgs );

    /** The persistence provider for this command. */
    private final IMetamodelCommandWriter cmdWriter;

    /** The metamodel repository to be acted upon by this command. */
    private final IMetamodelRepositorySpi metamodelRepository;

}
