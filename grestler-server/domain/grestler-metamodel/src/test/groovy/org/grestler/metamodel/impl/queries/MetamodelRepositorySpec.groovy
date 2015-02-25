//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.queries

import org.grestler.metamodel.api.elements.EAbstractness
import org.grestler.metamodel.spi.queries.*
import org.grestler.utilities.revisions.StmTransactionContext
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
        StmTransactionContext.beginReadWriteTransaction();

        IMetamodelRepositorySpi m = new MetamodelRepository(
                { r -> r.loadRootPackage( id0 ) } as IPackageLoader,
                { r -> } as IPackageDependencyLoader,
                { r -> } as IAttributeTypeLoader,
                { r ->
                    def rootPkg = r.findRootPackage();
                    def rootVertexType = r.loadBaseVertexType( Uuids.makeUuid(), rootPkg );
                    r.loadVertexType( id1, rootPkg, "V1", rootVertexType, EAbstractness.ABSTRACT );
                    r.loadVertexType( Uuids.makeUuid(), rootPkg, "V2", rootVertexType, EAbstractness.ABSTRACT );
                    r.loadVertexType( Uuids.makeUuid(), rootPkg, "V3", rootVertexType, EAbstractness.ABSTRACT );
                    r.loadVertexType( Uuids.makeUuid(), rootPkg, "V4", rootVertexType, EAbstractness.ABSTRACT );
                } as IVertexTypeLoader,
                { r -> } as IEdgeTypeLoader,
                { r -> } as IAttributeDeclLoader
        );

        expect:
        m.findOptionalVertexTypeById( id1 ).get().name == "V1";
        m.findAllVertexTypes().size() == 5;

        cleanup:
        StmTransactionContext.commitTransaction();

    }

}
