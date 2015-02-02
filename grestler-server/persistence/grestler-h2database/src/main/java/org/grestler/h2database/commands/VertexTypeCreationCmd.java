//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.commands;

import org.grestler.dbutilities.api.IDataSource;
import org.grestler.metamodel.api.IMetamodelCommand;
import org.grestler.utilities.uuids.Uuids;

import javax.json.stream.JsonParser;
import java.util.UUID;

/**
 * Command to create a vertex type.
 */
public class VertexTypeCreationCmd
    implements IMetamodelCommand {

    /**
     * Constructs a new vertex type creation command.
     */
    public VertexTypeCreationCmd( IDataSource dataSource, JsonParser jsonCommandArgs ) {
        this.id = Uuids.makeUuid();
        this.dataSource = dataSource;
        this.jsonCommandArguments = jsonCommandArgs;
    }

    @Override
    public void execute() {
        // TODO:
    }

    @Override
    public UUID getId() {
        return this.id;
    }


    private final IDataSource dataSource;

    private final UUID id;

    private final JsonParser jsonCommandArguments;

}
