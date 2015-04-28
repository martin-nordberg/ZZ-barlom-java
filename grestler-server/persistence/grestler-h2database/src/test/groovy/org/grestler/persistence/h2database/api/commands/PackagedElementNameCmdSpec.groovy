//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands

import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.h2database.api.queries.*
import org.grestler.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for packaged element renaming.
 */
class PackagedElementNameCmdSpec
        extends Specification {

    def "A packaged element name change command renames an element"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdIdA = "1234111A-7a26-11e4-a545-08002741a702";
        def cmdIdB = "1234111B-7a26-11e4-a545-08002741a702";
        def pkgId = "12341113-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdIdA + '","id":"' + pkgId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"pkg1before"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new PackageCreationCmdWriter( dataSource );
        cmd.execute( Json.createReader( new StringReader( json ) ).readObject(), {} as IMetamodelCommandSpi );

        json = '{"cmdId":"' + cmdIdB + '","id":"' + pkgId + '","name":"pkg1after"}';
        cmd = new PackagedElementNameChangeCmdWriter( dataSource );
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
        pkg.get().name == "pkg1after";

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
