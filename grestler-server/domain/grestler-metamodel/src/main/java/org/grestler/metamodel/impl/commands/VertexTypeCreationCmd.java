//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.commands;

import org.grestler.metamodel.api.queries.IMetamodelRepository;
import org.grestler.metamodel.spi.commands.IMetamodelCommandWriter;

import javax.json.JsonObject;

/**
 * Command to create a vertex type.
 */
public class VertexTypeCreationCmd
    extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param repository the repsoitory the command will act upon.
     * @param cmdWriter  the command's persistence provider.
     */
    protected VertexTypeCreationCmd(
        IMetamodelRepository repository, IMetamodelCommandWriter cmdWriter
    ) {
        super( repository, cmdWriter );
    }

    @Override
    protected void writeChangesToMetamodel( JsonObject jsonCmdArgs ) {
        // TODO
    }
}
