//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.spi.commands.DirectedEdgeTypeCreationCmdRecord;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to create a directed edge type.
 */
final class DirectedEdgeTypeCreationCmd
    extends AbstractMetamodelCommand<DirectedEdgeTypeCreationCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    DirectedEdgeTypeCreationCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<DirectedEdgeTypeCreationCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected DirectedEdgeTypeCreationCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new DirectedEdgeTypeCreationCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( DirectedEdgeTypeCreationCmdRecord record ) {

        // Find the related elements.
        IPackage parentPackage = this.getMetamodelRepository().findPackageById( record.et.parentPackageId );
        IDirectedEdgeType superType = this.getMetamodelRepository().findDirectedEdgeTypeById( record.et.superTypeId );
        IVertexType tailVertexType = this.getMetamodelRepository().findVertexTypeById( record.et.tailVertexTypeId );
        IVertexType headVertexType = this.getMetamodelRepository().findVertexTypeById( record.et.headVertexTypeId );

        // Create the new edge type.
        this.getMetamodelRepository()
            .loadDirectedEdgeType( record.et, parentPackage, superType, tailVertexType, headVertexType );

    }

}
