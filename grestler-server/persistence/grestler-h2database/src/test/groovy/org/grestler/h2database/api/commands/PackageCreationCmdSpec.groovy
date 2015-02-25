//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.api.commands

import org.grestler.h2database.api.queries.*
import org.grestler.h2database.impl.H2DataSource
import org.grestler.metamodel.impl.queries.MetamodelRepository
import org.grestler.metamodel.spi.commands.IMetamodelCommandSpi
import org.grestler.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for package creation.
 */
class PackageCreationCmdSpec
        extends Specification {

    def "A package creation command creates a package"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdId = "12341111-7a26-11e4-a545-08002741a702";
        def pkgId = "12341112-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + pkgId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"pkg1"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new PackageCreationCmdWriter( dataSource );
        cmd.execute( Json.createReader( new StringReader( json ) ).readObject(), {} as IMetamodelCommandSpi );

        def ploader = new PackageLoader( dataSource );
        def pdloader = new PackageDependencyLoader( dataSource );
        def atloader = new AttributeTypeLoader( dataSource );
        def vtloader = new VertexTypeLoader( dataSource );
        def etloader = new EdgeTypeLoader( dataSource )
        def adloader = new AttributeDeclLoader( dataSource );

        IMetamodelRepositorySpi m = new MetamodelRepository(
                ploader,
                pdloader,
                atloader,
                vtloader,
                etloader,
                adloader
        );

        def pkg = m.findOptionalPackageById( UUID.fromString( pkgId ) );

        expect:
        pkg.isPresent();
        pkg.get().name == "pkg1";

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
