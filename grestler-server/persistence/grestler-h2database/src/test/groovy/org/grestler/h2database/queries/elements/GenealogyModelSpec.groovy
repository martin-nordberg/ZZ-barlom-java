//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.datasource.H2DataSource
import org.grestler.h2database.queries.attributes.AttributeTypeLoader
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import spock.lang.Specification

/**
 * Spec for the genealogy schema; exercises metamodel repository loading.
 */
class GenealogyModelSpec
        extends Specification {

    def "The genealogy schema loads correctly"() {

        given:

        def dataSource = new H2DataSource( "test1" );

        def ploader = new PackageLoader( dataSource );
        def atloader = new AttributeTypeLoader( dataSource );
        def vtloader = new VertexTypeLoader( dataSource );
        def etloader = new EdgeTypeLoader( dataSource )
        def adloader = new AttributeDeclLoader( dataSource );

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, atloader, vtloader, etloader, adloader );

        expect:

        // packages
        m.findPackagesAll().size() == 2;

        def rootPkg = m.findPackageRoot().get();
        def genealogyPkg = m.findPackageById( UUID.fromString( "e4c4a700-a294-11e4-b20d-08002751500b" ) ).get();
        genealogyPkg.name == "test1";
        genealogyPkg.parentPackage == rootPkg;

        // vertex types
        m.findVertexTypesAll().size() == 2;

        def rootVertexType = m.findVertexTypeRoot().get();
        def vtPerson = m.findVertexTypeById( UUID.fromString( "e4c4a701-a294-11e4-b20d-08002751500b" ) ).get();
        vtPerson.parentPackage == genealogyPkg;
        vtPerson.name == "Person";
        vtPerson.superType.get() == rootVertexType;
        vtPerson.attributes.size() == 2;

        // edge types
        def rootEdgeType = m.findEdgeTypeRoot().get();
        def etHasFather = m.findEdgeTypeById( UUID.fromString( "e4c4a702-a294-11e4-b20d-08002751500b" ) ).get();
        etHasFather.parentPackage == genealogyPkg;
        etHasFather.name == "Has Father";
        etHasFather.superType.get() == rootEdgeType;
        etHasFather.tailVertexType == vtPerson;
        etHasFather.headVertexType == vtPerson;

        def etHasMother = m.findEdgeTypeById( UUID.fromString( "e4c4a703-a294-11e4-b20d-08002751500b" ) ).get();
        etHasMother.parentPackage == genealogyPkg;
        etHasMother.name == "Has Mother";
        etHasMother.superType.get() == rootEdgeType;
        etHasMother.tailVertexType == vtPerson;
        etHasMother.headVertexType == vtPerson;

    }

}
