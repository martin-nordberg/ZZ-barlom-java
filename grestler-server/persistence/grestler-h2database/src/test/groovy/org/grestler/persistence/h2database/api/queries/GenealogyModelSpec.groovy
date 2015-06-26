//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries

import org.grestler.domain.metamodel.api.elements.EAttributeOptionality
import org.grestler.domain.metamodel.api.elements.EDataType
import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType
import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

/**
 * Spec for the genealogy schema; exercises metamodel repository loading.
 */
class GenealogyModelSpec
    extends Specification {

    def "The genealogy schema loads correctly"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def dataSource = new H2DataSource( "test1" );

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

        expect:

        // packages
        m.findAllPackages().size() == 2;

        def rootPkg = m.findRootPackage();
        def genealogyPkg = m.findPackageById( UUID.fromString( "e4c4a700-a294-11e4-b20d-08002751500b" ) );
        genealogyPkg.name == "test1";
        genealogyPkg.parentPackage == rootPkg;

        // vertex types
        m.findAllVertexTypes().size() == 2;

        def rootVertexType = m.findRootVertexType();
        def vtPerson = m.findVertexTypeById( UUID.fromString( "e4c4a701-a294-11e4-b20d-08002751500b" ) );
        vtPerson.parentPackage == genealogyPkg;
        vtPerson.name == "Person";
        vtPerson.superType.get() == rootVertexType;
        vtPerson.attributes.size() == 5;
        vtPerson.attributes[0].name == "Given Name";
        vtPerson.attributes[0].parentVertexType == vtPerson;
        vtPerson.attributes[0].optionality == EAttributeOptionality.OPTIONAL;
        vtPerson.attributes[0].type.name == "Person Name";
        vtPerson.attributes[0].type.dataType == EDataType.STRING;
        vtPerson.attributes[1].name == "Surname";
        vtPerson.attributes[1].optionality == EAttributeOptionality.REQUIRED;
        vtPerson.attributes[1].type.name == "Person Name";
        vtPerson.attributes[1].type.dataType == EDataType.STRING;
        vtPerson.attributes[2].name == "Current Assets";
        vtPerson.attributes[2].type.name == "Asset Value Dollars";
        vtPerson.attributes[2].type.dataType == EDataType.FLOAT64;
        vtPerson.attributes[3].name == "Lifetime Address Count";
        vtPerson.attributes[3].type.name == "Address Count";
        vtPerson.attributes[3].type.dataType == EDataType.INTEGER32;
        vtPerson.attributes[4].name == "Person ID";
        vtPerson.attributes[4].type.name == "UUID";
        vtPerson.attributes[4].type.dataType == EDataType.UUID;

        // edge types
        def rootEdgeType = m.findRootDirectedEdgeType();
        IDirectedEdgeType etHasFather = m.findEdgeTypeById(
            UUID.fromString( "e4c4a702-a294-11e4-b20d-08002751500b" )
        ) as IDirectedEdgeType;
        etHasFather.parentPackage == genealogyPkg;
        etHasFather.name == "Has Father";
        etHasFather.superType.get() == rootEdgeType;
        etHasFather.tailVertexType == vtPerson;
        etHasFather.headVertexType == vtPerson;
        etHasFather.attributes.size() == 0;

        IDirectedEdgeType etHasMother = m.findEdgeTypeById(
            UUID.fromString( "e4c4a703-a294-11e4-b20d-08002751500b" )
        ) as IDirectedEdgeType;
        etHasMother.parentPackage == genealogyPkg;
        etHasMother.name == "Has Mother";
        etHasMother.superType.get() == rootEdgeType;
        etHasMother.tailVertexType == vtPerson;
        etHasMother.headVertexType == vtPerson;
        etHasMother.attributes.size() == 1;
        etHasMother.attributes[0].name == "Calls Weekly";
        etHasMother.attributes[0].parentEdgeType == etHasMother;
        etHasMother.attributes[0].optionality == EAttributeOptionality.OPTIONAL;
        etHasMother.attributes[0].type.name == "Calls Weekly";
        etHasMother.attributes[0].type.dataType == EDataType.BOOLEAN;

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
