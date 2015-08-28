//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.impl.elements.EdgeType;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeCyclicityChangeCmdRecord;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeMultiEdgednessChangeCmdRecord;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to change the multi-edgedness of an edge type.
 */
final class EdgeTypeMultiEdgednessChangeCmd
    extends AbstractMetamodelCommand<EdgeTypeMultiEdgednessChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    EdgeTypeMultiEdgednessChangeCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<EdgeTypeMultiEdgednessChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected EdgeTypeMultiEdgednessChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new EdgeTypeMultiEdgednessChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( EdgeTypeMultiEdgednessChangeCmdRecord record ) {

        // Look up the related parent package.
        IEdgeType element = this.getMetamodelRepository().findEdgeTypeById( record.id );

        // Change the name
        ( (EdgeType) element ).setMultiEdgedness( record.multiEdgedness );

    }

}
