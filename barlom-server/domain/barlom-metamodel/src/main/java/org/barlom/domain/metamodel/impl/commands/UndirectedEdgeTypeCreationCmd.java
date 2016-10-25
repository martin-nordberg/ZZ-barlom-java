//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.commands.UndirectedEdgeTypeCreationCmdRecord;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to create an undirected edge type.
 */
final class UndirectedEdgeTypeCreationCmd
    extends AbstractMetamodelCommand<UndirectedEdgeTypeCreationCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    UndirectedEdgeTypeCreationCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<UndirectedEdgeTypeCreationCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected UndirectedEdgeTypeCreationCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new UndirectedEdgeTypeCreationCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( UndirectedEdgeTypeCreationCmdRecord record ) {

        // Find the related elements.
        IPackage parentPackage = this.getMetamodelRepository().findPackageById( record.et.parentPackageId );
        IUndirectedEdgeType superType = this.getMetamodelRepository()
                                            .findUndirectedEdgeTypeById( record.et.superTypeId );
        IVertexType vertexType = this.getMetamodelRepository().findVertexTypeById( record.et.vertexTypeId );

        // Create the new edge type.
        this.getMetamodelRepository().loadUndirectedEdgeType( record.et, parentPackage, superType, vertexType );

    }

}
