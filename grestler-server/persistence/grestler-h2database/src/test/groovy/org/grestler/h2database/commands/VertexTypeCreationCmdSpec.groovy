//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.commands

import org.grestler.dbutilities.api.DatabaseException
import org.grestler.h2database.impl.H2DataSource
import org.grestler.h2database.queries.attributes.AttributeTypeLoader
import org.grestler.h2database.queries.elements.*
import org.grestler.metamodel.impl.metamodel.MetamodelRepository
import org.grestler.metamodel.spi.metamodel.IMetamodelRepositorySpi
import org.grestler.utilities.exceptions.EValidationType
import spock.lang.Specification

import javax.json.Json

/**
 * Specification for edge type loading.
 */
class VertexTypeCreationCmdSpec
        extends Specification {

    def "A vertex type creation command creates a vertex type"() {

        given:
        def vtId = "12345678-7a26-11e4-a545-08002741a702";
        def json = '{"id":"' + vtId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"sample1","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmd( dataSource );
        cmd.execute( Json.createReader( new StringReader( json ) ).readObject() );

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

        def vertexType = m.findVertexTypeById( UUID.fromString( vtId ) );

        expect:
        vertexType.isPresent();
        vertexType.get().name == "sample1";
        vertexType.get().superType.isPresent();
        vertexType.get().abstractness.isAbstract();

    }

    def "Duplicate vertex type creation is prevented"() {

        given:
        def vtId = "12341234-7a26-11e4-a545-08002741a702";
        def json = '{"id":"' + vtId + '","parentPackageId":"00000000-7a26-11e4-a545-08002741a702","name":"sample2","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmd( dataSource );
        cmd.execute( Json.createReader( new StringReader( json ) ).readObject() );

        when:
        cmd.execute( Json.createReader( new StringReader( json ) ).readObject() );

        then:
        DatabaseException e = thrown();
        e.getValidationType() == EValidationType.DUPLICATE_ENTITY_CREATION;
        e.getValidationMessage() == "Duplicate vertex type creation."

    }

    def "Vertex type creation requires a valid parent package"() {

        given:
        def vtId = "23456789-7a26-11e4-a545-08002741a702";
        def json = '{"id":"' + vtId + '","parentPackageId":"99999999-7a26-11e4-a545-08002741a702","name":"sample3","superTypeId":"00000010-7a26-11e4-a545-08002741a702","abstractness":"ABSTRACT"}';
        def dataSource = new H2DataSource( "test2" );
        def cmd = new VertexTypeCreationCmd( dataSource );

        when:
        cmd.execute( Json.createReader( new StringReader( json ) ).readObject() );

        then:
        DatabaseException e = thrown();
        e.getValidationType() == EValidationType.RELATED_ENTITY_NOT_FOUND;
        e.getValidationMessage() == "Related package not found."

    }

}
