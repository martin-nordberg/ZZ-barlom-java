//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands

import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.commands.DirectedEdgeTypeCreationCmdRecord
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.exceptions.EValidationType
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.dbutilities.api.DatabaseException
import org.grestler.persistence.h2database.api.queries.*
import org.grestler.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for directed edge type creation.
 */
class DirectedEdgeTypeCreationCmdSpec
        extends Specification {

    def "A directed edge type creation command creates a directed edge type"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdId = "12342111-7a26-11e4-a545-08002741a702";
        def etId = "12342112-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + etId +
                '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702",' +
                '"name":"diredge1","superTypeId":"00000020-7a26-11e4-a545-08002741a702",' +
                '"abstractness":"ABSTRACT", "cyclicity":"UNCONSTRAINED", "multiEdgedness":"MULTI_EDGES_ALLOWED",' +
                '"selfLooping":"SELF_LOOPS_NOT_ALLOWED","tailVertexTypeId":"00000010-7a26-11e4-a545-08002741a702","headVertexTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new DirectedEdgeTypeCreationCmdWriter( dataSource );
        def record = new DirectedEdgeTypeCreationCmdRecord(
                Json.createReader( new StringReader( json ) ).readObject()
        );
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

        def edgeType = m.findOptionalDirectedEdgeTypeById( UUID.fromString( etId ) );

        expect:
        edgeType.isPresent();
        edgeType.get().name == "diredge1";
        edgeType.get().superType.isPresent();
        edgeType.get().abstractness.isAbstract();

        cleanup:
        StmTransactionContext.commitTransaction();
    }

    def "Duplicate directed edge type creation is prevented"() {

        given:
        def cmdId = "12342113-7a26-11e4-a545-08002741a702";
        def etId = "12342114-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + etId +
                '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702",' +
                '"name":"diredge2","superTypeId":"00000020-7a26-11e4-a545-08002741a702",' +
                '"abstractness":"ABSTRACT", "cyclicity":"ACYCLIC", "multiEdgedness":"MULTI_EDGES_NOT_ALLOWED",' +
                '"selfLooping":"UNCONSTRAINED","tailVertexTypeId":"00000010-7a26-11e4-a545-08002741a702","headVertexTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new DirectedEdgeTypeCreationCmdWriter( dataSource );
        def record = new DirectedEdgeTypeCreationCmdRecord(
                Json.createReader( new StringReader( json ) ).readObject()
        );
        cmd.execute( record, {} as IMetamodelCommandSpi );

        when:
        cmd.execute( record, {} as IMetamodelCommandSpi );

        then:
        DatabaseException e = thrown();
        e.getValidationType() == EValidationType.DUPLICATE_ENTITY_CREATION;
        e.getValidationMessage() == "Duplicate element creation."

    }

    def "Directed edge type creation requires a valid parent package"() {

        given:
        def cmdId = "12342115-7a26-11e4-a545-08002741a702";
        def etId = "23452116-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + etId +
                '","parentPackageId":"99999999-7a26-11e4-a545-08002741a702",' +
                '"name":"diredge3","superTypeId":"00000020-7a26-11e4-a545-08002741a702",' +
                '"abstractness":"CONCRETE", "cyclicity":"ACYCLIC", "multiEdgedness":"MULTI_EDGES_NOT_ALLOWED",' +
                '"selfLooping":"UNCONSTRAINED","tailVertexTypeId":"00000010-7a26-11e4-a545-08002741a702","headVertexTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new DirectedEdgeTypeCreationCmdWriter( dataSource );
        def record = new DirectedEdgeTypeCreationCmdRecord(
                Json.createReader( new StringReader( json ) ).readObject()
        );

        when:
        cmd.execute( record, {} as IMetamodelCommandSpi );

        then:
        DatabaseException e = thrown();
        e.getValidationType() == EValidationType.RELATED_ENTITY_NOT_FOUND;
        e.getValidationMessage() == "Related package not found."

    }

}
