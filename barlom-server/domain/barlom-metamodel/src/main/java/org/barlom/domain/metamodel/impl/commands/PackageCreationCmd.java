//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.commands;

import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.barlom.domain.metamodel.spi.commands.PackageCreationCmdRecord;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Command to create a package.
 */
final class PackageCreationCmd
    extends AbstractMetamodelCommand<PackageCreationCmdRecord> {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    PackageCreationCmd(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriter<PackageCreationCmdRecord> cmdWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    @Override
    protected PackageCreationCmdRecord parseJson( JsonObject jsonCmdArgs ) {
        return new PackageCreationCmdRecord( jsonCmdArgs );
    }

    @Override
    protected void writeChangesToMetamodel( PackageCreationCmdRecord record ) {

        // Extract the package attributes from the command JSON.
        UUID parentPackageId = record.pkg.parentPackageId;

        // Look up the related parent package.
        IPackage parentPackage = this.getMetamodelRepository().findPackageById( parentPackageId );

        // Create the new package.
        this.getMetamodelRepository().loadPackage( record.pkg, parentPackage );

    }

}
