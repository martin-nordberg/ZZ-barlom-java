//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Command to create a vertex type.
 */
final class VertexTypeCreationCmd
    extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    VertexTypeCreationCmd(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected void writeChangesToMetamodel( JsonObject jsonCmdArgs ) {

        // Extract the vertex type attributes from the command JSON.
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        UUID parentPackageId = UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) );
        String name = jsonCmdArgs.getString( "name" );
        UUID superTypeId = UUID.fromString( jsonCmdArgs.getString( "superTypeId" ) );
        EAbstractness abstractness = EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) );

        // Find the related elements.
        IPackage parentPackage = this.getMetamodelRepository().findPackageById( parentPackageId );
        IVertexType superType = this.getMetamodelRepository().findVertexTypeById( superTypeId );

        // Create the new vertex type.
        this.getMetamodelRepository().loadVertexType( id, parentPackage, name, superType, abstractness );

    }

}
