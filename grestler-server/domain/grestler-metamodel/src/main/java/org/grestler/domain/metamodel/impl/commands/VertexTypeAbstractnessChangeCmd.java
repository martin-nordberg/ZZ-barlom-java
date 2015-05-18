//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.impl.elements.VertexType;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Command to change the abstractness of a vertex type.
 */
final class VertexTypeAbstractnessChangeCmd
    extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    VertexTypeAbstractnessChangeCmd(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected void writeChangesToMetamodel( JsonObject jsonCmdArgs ) {

        // Extract the vertex type attributes from the command JSON.
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        EAbstractness abstractness = EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) );

        // Look up the related parent package.
        IVertexType element = this.getMetamodelRepository().findVertexTypeById( id );

        // Change the name
        ( (VertexType) element ).setAbstractness( abstractness );

    }

}
