//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.impl.elements.EdgeType;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeCyclicityChangeCmdRecord;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to change the cyclicity of a vertex type.
 */
final class EdgeTypeCyclicityChangeCmd
    extends AbstractMetamodelCommand<EdgeTypeCyclicityChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    EdgeTypeCyclicityChangeCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<EdgeTypeCyclicityChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected EdgeTypeCyclicityChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new EdgeTypeCyclicityChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( EdgeTypeCyclicityChangeCmdRecord record ) {

        // Look up the related parent package.
        IEdgeType element = this.getMetamodelRepository().findEdgeTypeById( record.id );

        // Change the name
        ( (EdgeType) element ).setCyclicity( record.cyclicity );

    }

}
