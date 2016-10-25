//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.domain.metamodel.impl.elements.VertexType;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.commands.VertexTypeAbstractnessChangeCmdRecord;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

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
