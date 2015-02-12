//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.metamodel;

import org.grestler.metamodel.api.metamodel.IMetamodelCommand;
import org.grestler.utilities.uuids.Uuids;

import java.util.UUID;

/**
 * Partial implementation of IMetamodelCommand.
 */
public abstract class AbstractMetamodelCommand
    implements IMetamodelCommand {

    /**
     * Constructs a new command; gives it a new unique ID.
     */
    protected AbstractMetamodelCommand() {
        this.cmdId = Uuids.makeUuid();
    }

    @Override
    public final UUID getCmdId() {
        return this.cmdId;
    }

    /** The unique ID of this command. */
    private final UUID cmdId;

}
