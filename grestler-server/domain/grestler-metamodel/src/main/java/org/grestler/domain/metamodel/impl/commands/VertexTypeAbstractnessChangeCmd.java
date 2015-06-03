//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.impl.elements.VertexType;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.commands.VertexTypeAbstractnessChangeCmdRecord;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to change the abstractness of a vertex type.
 */
final class VertexTypeAbstractnessChangeCmd
    extends AbstractMetamodelCommand<VertexTypeAbstractnessChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    VertexTypeAbstractnessChangeCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<VertexTypeAbstractnessChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected VertexTypeAbstractnessChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new VertexTypeAbstractnessChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( VertexTypeAbstractnessChangeCmdRecord record ) {

        // Look up the related parent package.
        IVertexType element = this.getMetamodelRepository().findVertexTypeById( record.id );

        // Change the name
        ( (VertexType) element ).setAbstractness( record.abstractness );

    }

}
