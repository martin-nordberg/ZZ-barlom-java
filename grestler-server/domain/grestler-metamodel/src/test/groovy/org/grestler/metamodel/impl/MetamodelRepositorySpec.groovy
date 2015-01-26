//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl

import org.grestler.metamodel.api.elements.EAbstractness
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader
import org.grestler.metamodel.spi.elements.IAttributeDeclLoader
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader
import org.grestler.metamodel.spi.elements.IPackageLoader
import org.grestler.metamodel.spi.elements.IVertexTypeLoader
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for a metamodel repository.
 */
class MetamodelRepositorySpec
        extends Specification {

    UUID id0 = Uuids.makeUuid();

    UUID id1 = Uuids.makeUuid();

    def "A metamodel repository lets added vertex types be retrieved"() {

        given:
        IMetamodelRepositorySpi m = new MetamodelRepository(
                { r -> r.loadRootPackage( id0 ) } as IPackageLoader,
                { r -> } as IAttributeTypeLoader,
                { r ->
                    def rootPkg = r.findPackageRoot().get();
                    def rootVertexType = r.loadRootVertexType( Uuids.makeUuid(), rootPkg );
                    r.loadVertexType( id1, rootPkg, "V1", rootVertexType, EAbstractness.ABSTRACT );
                    r.loadVertexType( Uuids.makeUuid(), rootPkg, "V2", rootVertexType, EAbstractness.ABSTRACT );
                    r.loadVertexType( Uuids.makeUuid(), rootPkg, "V3", rootVertexType, EAbstractness.ABSTRACT );
                    r.loadVertexType( Uuids.makeUuid(), rootPkg, "V4", rootVertexType, EAbstractness.ABSTRACT );
                } as IVertexTypeLoader,
                { r -> } as IEdgeTypeLoader,
                { r -> } as IAttributeDeclLoader
        );

        expect:
        m.findVertexTypeById( id1 ).get().name == "V1";
        m.findVertexTypesAll().size() == 5;

    }

}
