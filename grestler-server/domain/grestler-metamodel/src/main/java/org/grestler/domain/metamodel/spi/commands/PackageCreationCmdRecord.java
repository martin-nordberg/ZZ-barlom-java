//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

import org.grestler.domain.metamodel.api.elements.IPackage;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of a package creation command.
 */
public class PackageCreationCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public PackageCreationCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.pkg = new IPackage.Record(
            UUID.fromString( jsonCmdArgs.getString( "id" ) ),
            UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) ),
            jsonCmdArgs.getString( "name" )
        );
    }

    public final IPackage.Record pkg;

}
