//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.domain.metamodel.impl.elements.VertexType;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.commands.VertexTypeSuperTypeChangeCmdRecord;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to change the super type of a vertex type.
 */
final class VertexTypeSuperTypeChangeCmd
    extends AbstractMetamodelCommand<VertexTypeSuperTypeChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    VertexTypeSuperTypeChangeCmd(
        IMetamodelRepositorySpi metamodelRepository,
        IMetamodelCommandWriter<VertexTypeSuperTypeChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected VertexTypeSuperTypeChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new VertexTypeSuperTypeChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( VertexTypeSuperTypeChangeCmdRecord record ) {

        // Look up the elements.
        IVertexType element = this.getMetamodelRepository().findVertexTypeById( record.id );
        IVertexType superType = this.getMetamodelRepository().findVertexTypeById( record.superTypeId );

        // Change the name
        ( (VertexType) element ).setSuperType( superType );

    }

}
