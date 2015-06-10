//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands

import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.grestler.domain.metamodel.spi.commands.PackageCreationCmdRecord
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.h2database.api.queries.*
import org.grestler.persistence.h2database.impl.H2DataSource
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
        def record = new PackageCreationCmdRecord( Json.createReader( new StringReader( json ) ).readObject() );
        cmd.execute( record, {} as IMetamodelCommandSpi );

        def ploader = new PackageLoader( dataSource );
        def pdloader = new PackageDependencyLoader( dataSource );
        def atloader = new AttributeTypeLoader( dataSource );
        def vtloader = new VertexTypeLoader( dataSource );
        def detloader = new DirectedEdgeTypeLoader( dataSource )
        def uetloader = new UndirectedEdgeTypeLoader( dataSource )
        def adloader = new AttributeDeclLoader( dataSource );

        IMetamodelRepositorySpi m = new MetamodelRepository(
                ploader,
                pdloader,
                atloader,
                vtloader,
                detloader,
                uetloader,
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
