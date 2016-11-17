//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.commands

import org.barlom.domain.metamodel.api.elements.EAbstractness
import org.barlom.domain.metamodel.impl.queries.MetamodelRepository
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.barlom.domain.metamodel.spi.commands.VertexTypeAbstractnessChangeCmdRecord
import org.barlom.domain.metamodel.spi.commands.VertexTypeCreationCmdRecord
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.barlom.infrastructure.utilities.revisions.StmTransactionContext
import org.barlom.persistence.h2database.api.queries.AttributeDeclLoader
import org.barlom.persistence.h2database.api.queries.AttributeTypeLoader
import org.barlom.persistence.h2database.api.queries.DirectedEdgeTypeLoader
import org.barlom.persistence.h2database.api.queries.PackageDependencyLoader
import org.barlom.persistence.h2database.api.queries.PackageLoader
import org.barlom.persistence.h2database.api.queries.UndirectedEdgeTypeLoader
import org.barlom.persistence.h2database.api.queries.VertexTypeLoader
import org.barlom.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for vertex type abstractness changes.
 */
class VertexTypeAbstractnessChangeCmdSpec
    extends Specification {

    def "A vertex type abstractness change command revises a vertex type"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdIdA = "1234111C-7a26-11e4-a545-08002741a702";
        def cmdIdB = "1234111D-7a26-11e4-a545-08002741a702";
        def vtId = "1234111E-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdIdA + '","id":"' + vtId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"vt1","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmdWriter( dataSource );
        def recordA = new VertexTypeCreationCmdRecord( Json.createReader( new StringReader( json ) ).readObject() );
        cmd.execute( recordA, {} as IMetamodelCommandSpi );

        json = '{"cmdId":"' + cmdIdB + '","id":"' + vtId + '","abstractness":"CONCRETE"}';
        cmd = new VertexTypeAbstractnessChangeCmdWriter( dataSource );
        def recordB = new VertexTypeAbstractnessChangeCmdRecord(
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
        vt.get().abstractness == EAbstractness.CONCRETE;

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}