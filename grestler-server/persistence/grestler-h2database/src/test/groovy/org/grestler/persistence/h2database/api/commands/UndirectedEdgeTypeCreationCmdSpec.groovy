//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands

import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.grestler.domain.metamodel.spi.commands.UndirectedEdgeTypeCreationCmdRecord
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.exceptions.EValidationType
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.dbutilities.api.DatabaseException
import org.grestler.persistence.h2database.api.queries.AttributeDeclLoader
import org.grestler.persistence.h2database.api.queries.AttributeTypeLoader
import org.grestler.persistence.h2database.api.queries.DirectedEdgeTypeLoader
import org.grestler.persistence.h2database.api.queries.PackageDependencyLoader
import org.grestler.persistence.h2database.api.queries.PackageLoader
import org.grestler.persistence.h2database.api.queries.UndirectedEdgeTypeLoader
import org.grestler.persistence.h2database.api.queries.VertexTypeLoader
import org.grestler.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for directed edge type creation.
 */
class UndirectedEdgeTypeCreationCmdSpec
    extends Specification {

    def "An undirected edge type creation command creates an undirected edge type"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdId = "12343111-7a26-11e4-a545-08002741a702";
        def etId = "12343112-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + etId +
            '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702",' +
            '"name":"undiredge1","superTypeId":"00000030-7a26-11e4-a545-08002741a702",' +
            '"abstractness":"ABSTRACT", "cyclicity":"UNCONSTRAINED", "multiEdgedness":"MULTI_EDGES_ALLOWED",' +
            '"selfLooping":"SELF_LOOPS_NOT_ALLOWED","vertexTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new UndirectedEdgeTypeCreationCmdWriter( dataSource );
        def record = new UndirectedEdgeTypeCreationCmdRecord(
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

        def edgeType = m.findOptionalUndirectedEdgeTypeById( UUID.fromString( etId ) );

        expect:
        edgeType.isPresent();
        edgeType.get().name == "undiredge1";
        edgeType.get().superType.isPresent();
        edgeType.get().abstractness.isAbstract();

        cleanup:
        StmTransactionContext.commitTransaction();
    }

    def "Duplicate undirected edge type creation is prevented"() {

        given:
        def cmdId = "12343113-7a26-11e4-a545-08002741a702";
        def etId = "12343114-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + etId +
            '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702",' +
            '"name":"undiredge2","superTypeId":"00000030-7a26-11e4-a545-08002741a702",' +
            '"abstractness":"ABSTRACT", "cyclicity":"ACYCLIC", "multiEdgedness":"MULTI_EDGES_NOT_ALLOWED",' +
            '"selfLooping":"UNCONSTRAINED","vertexTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new UndirectedEdgeTypeCreationCmdWriter( dataSource );
        def record = new UndirectedEdgeTypeCreationCmdRecord(
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
        def cmdId = "12343115-7a26-11e4-a545-08002741a702";
        def etId = "23453116-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + etId +
            '","parentPackageId":"99999999-7a26-11e4-a545-08002741a702",' +
            '"name":"undiredge3","superTypeId":"00000030-7a26-11e4-a545-08002741a702",' +
            '"abstractness":"CONCRETE", "cyclicity":"ACYCLIC", "multiEdgedness":"MULTI_EDGES_NOT_ALLOWED",' +
            '"selfLooping":"UNCONSTRAINED","vertexTypeId":"00000010-7a26-11e4-a545-08002741a702"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new UndirectedEdgeTypeCreationCmdWriter( dataSource );
        def record = new UndirectedEdgeTypeCreationCmdRecord(
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
