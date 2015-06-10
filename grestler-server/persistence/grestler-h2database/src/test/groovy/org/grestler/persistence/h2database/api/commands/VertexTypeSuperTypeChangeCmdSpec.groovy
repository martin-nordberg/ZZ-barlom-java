//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands

import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.grestler.domain.metamodel.spi.commands.VertexTypeCreationCmdRecord
import org.grestler.domain.metamodel.spi.commands.VertexTypeSuperTypeChangeCmdRecord
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.h2database.api.queries.*
import org.grestler.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for vertex type abstractness changes.
 */
class VertexTypeSuperTypeChangeCmdSpec
        extends Specification {

    def "A vertex type super type change command revises a vertex type"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdIdA = "12341120-7a26-11e4-a545-08002741a702";
        def cmdIdB = "12341121-7a26-11e4-a545-08002741a702";
        def vtId = "12341122-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdIdA + '","id":"' + vtId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"vt2","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmdWriter( dataSource );
        def recordA = new VertexTypeCreationCmdRecord( Json.createReader( new StringReader( json ) ).readObject() );
        cmd.execute( recordA, {} as IMetamodelCommandSpi );

        json = '{"cmdId":"' + cmdIdB + '","id":"' + vtId + '","superTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        cmd = new VertexTypeSuperTypeChangeCmdWriter( dataSource );
        def recordB = new VertexTypeSuperTypeChangeCmdRecord(
                Json.createReader( new StringReader( json ) ).readObject()
        );
        cmd.execute( recordB, {} as IMetamodelCommandSpi );

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

        def vt = m.findOptionalVertexTypeById( UUID.fromString( vtId ) );

        expect:
        vt.isPresent();
        vt.get().superType.get().id == UUID.fromString( "00000010-7a26-11e4-a545-08002741a702" );

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
