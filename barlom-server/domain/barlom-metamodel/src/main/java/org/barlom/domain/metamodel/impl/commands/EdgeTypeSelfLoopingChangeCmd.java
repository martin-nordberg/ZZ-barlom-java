//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IEdgeType;
import org.barlom.domain.metamodel.impl.elements.EdgeType;
import org.barlom.domain.metamodel.spi.commands.EdgeTypeSelfLoopingChangeCmdRecord;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to change the self-looping of an edge type.
 */
final class EdgeTypeSelfLoopingChangeCmd
    extends AbstractMetamodelCommand<EdgeTypeSelfLoopingChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    EdgeTypeSelfLoopingChangeCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<EdgeTypeSelfLoopingChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected EdgeTypeSelfLoopingChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new EdgeTypeSelfLoopingChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( EdgeTypeSelfLoopingChangeCmdRecord record ) {

        // Look up the related parent package.
        IEdgeType element = this.getMetamodelRepository().findEdgeTypeById( record.id );

        // Change the name
        ( (EdgeType) element ).setSelfLooping( record.selfLooping );

    }

}
