//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.commands;

import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Command to create a package.
 */
final class PackageCreationCmd
    extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    PackageCreationCmd(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected void writeChangesToMetamodel( JsonObject jsonCmdArgs ) {

        // Extract the package attributes from the command JSON.
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        UUID parentPackageId = UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) );
        String name = jsonCmdArgs.getString( "name" );

        // Look up the related parent package.
        IPackage parentPackage = this.getMetamodelRepository().findPackageById( parentPackageId );

        // Create the new package.
        this.getMetamodelRepository().loadPackage( id, parentPackage, name );

    }

}
