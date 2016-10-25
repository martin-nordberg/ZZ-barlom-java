//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.commands.VertexTypeCreationCmdRecord;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to create a vertex type.
 */
final class VertexTypeCreationCmd
    extends AbstractMetamodelCommand<VertexTypeCreationCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    VertexTypeCreationCmd(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter<VertexTypeCreationCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected VertexTypeCreationCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new VertexTypeCreationCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( VertexTypeCreationCmdRecord record ) {

        // Find the related elements.
        IPackage parentPackage = this.getMetamodelRepository().findPackageById( record.vt.parentPackageId );
        IVertexType superType = this.getMetamodelRepository().findVertexTypeById( record.vt.superTypeId );

        // Create the new vertex type.
        this.getMetamodelRepository().loadVertexType( record.vt, parentPackage, superType );

    }

}
