//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.IPackagedElement;
import org.grestler.domain.metamodel.impl.elements.NamedElement;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.commands.NamedElementNameChangeCmdRecord;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;

/**
 * Command to rename a packages element.
 */
final class PackagedElementNameChangeCmd
    extends AbstractMetamodelCommand<NamedElementNameChangeCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    PackagedElementNameChangeCmd(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter<NamedElementNameChangeCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected NamedElementNameChangeCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new NamedElementNameChangeCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( NamedElementNameChangeCmdRecord record ) {

        // Look up the related parent package.
        IPackagedElement element = this.getMetamodelRepository().findPackagedElementById( record.id );

        // Change the name
        ( (NamedElement) element ).setName( record.name );

    }

}
