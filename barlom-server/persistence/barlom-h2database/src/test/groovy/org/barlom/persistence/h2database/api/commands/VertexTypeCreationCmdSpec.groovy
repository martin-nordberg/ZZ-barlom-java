//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.commands

import org.barlom.domain.metamodel.impl.queries.MetamodelRepository
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandSpi
import org.barlom.domain.metamodel.spi.commands.VertexTypeCreationCmdRecord
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.barlom.infrastructure.utilities.exceptions.EValidationType
import org.barlom.infrastructure.utilities.revisions.StmTransactionContext
import org.barlom.persistence.dbutilities.api.DatabaseException
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
 * Specification for vertex type creation.
 */
class VertexTypeCreationCmdSpec
    extends Specification {

    def "A vertex type creation command creates a vertex type"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def cmdId = "12341234-7a26-11e4-a545-08002741a702";
        def vtId = "12345678-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + vtId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"sample1","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmdWriter( dataSource );
        def record = new VertexTypeCreationCmdRecord( Json.createReader( new StringReader( json ) ).readObject() );
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

        def vertexType = m.findOptionalVertexTypeById( UUID.fromString( vtId ) );

        expect:
        vertexType.isPresent();
        vertexType.get().name == "sample1";
        vertexType.get().superType.isPresent();
        vertexType.get().abstractness.isAbstract();

        cleanup:
        StmTransactionContext.commitTransaction();
    }

    def "Duplicate vertex type creation is prevented"() {

        given:
        def cmdId = "12341235-7a26-11e4-a545-08002741a702";
        def vtId = "12341234-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + vtId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"sample2","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmdWriter( dataSource );
        def record = new VertexTypeCreationCmdRecord( Json.createReader( new StringReader( json ) ).readObject() );
        cmd.execute( record, {} as IMetamodelCommandSpi );

        when:
        cmd.execute( record, {} as IMetamodelCommandSpi );

        then:
        DatabaseException e = thrown();
        e.getValidationType() == EValidationType.DUPLICATE_ENTITY_CREATION;
        e.getValidationMessage() == "Duplicate element creation."

    }

    def "Vertex type creation requires a valid parent package"() {

        given:
        def cmdId = "12341236-7a26-11e4-a545-08002741a702";
        def vtId = "23456789-7a26-11e4-a545-08002741a702";
        def json = '{"cmdId":"' + cmdId + '","id":"' + vtId + '","parentPackageId":"99999999-7a26-11e4-a545-08002741a702","name":"sample3","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmdWriter( dataSource );
        def record = new VertexTypeCreationCmdRecord( Json.createReader( new StringReader( json ) ).readObject() );

        when:
        cmd.execute( record, {} as IMetamodelCommandSpi );

        then:
        DatabaseException e = thrown();
        e.getValidationType() == EValidationType.RELATED_ENTITY_NOT_FOUND;
        e.getValidationMessage() == "Related package not found."

    }

}
