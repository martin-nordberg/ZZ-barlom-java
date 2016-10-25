//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IEdgeType;
import org.barlom.domain.metamodel.impl.elements.EdgeType;
import org.barlom.domain.metamodel.spi.commands.EdgeTypeAbstractnessChangeCmdRecord;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to change the abstractness of an edge type.
 */
final class EdgeTypeAbstractnessChangeCmd
    extends AbstractMetamodelCommand<EdgeTypeAbstractnessChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    EdgeTypeAbstractnessChangeCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<EdgeTypeAbstractnessChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected EdgeTypeAbstractnessChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new EdgeTypeAbstractnessChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( EdgeTypeAbstractnessChangeCmdRecord record ) {

        // Look up the related parent package.
        IEdgeType element = this.getMetamodelRepository().findEdgeTypeById( record.id );

        // Change the name
        ( (EdgeType) element ).setAbstractness( record.abstractness );

    }

}
