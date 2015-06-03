//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.queries

import org.grestler.domain.metamodel.api.elements.EAbstractness
import org.grestler.domain.metamodel.api.elements.IVertexType
import org.grestler.domain.metamodel.spi.queries.*
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.infrastructure.utilities.uuids.Uuids
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
                    def rootVertexType = r.loadRootVertexType( Uuids.makeUuid(), rootPkg );
                    r.loadVertexType(
                            new IVertexType.Record( id1, rootPkg.id, "V1", EAbstractness.ABSTRACT, rootVertexType.id ),
                            rootPkg,
                            rootVertexType
                    );
                    r.loadVertexType(
                            new IVertexType.Record(
                                    Uuids.makeUuid(),
                                    rootPkg.id,
                                    "V2",
                                    EAbstractness.ABSTRACT,
                                    rootVertexType.id
                            ),
                            rootPkg,
                            rootVertexType
                    );
                    r.loadVertexType(
                            new IVertexType.Record(
                                    Uuids.makeUuid(),
                                    rootPkg.id,
                                    "V3",
                                    EAbstractness.ABSTRACT,
                                    rootVertexType.id
                            ),
                            rootPkg,
                            rootVertexType
                    );
                    r.loadVertexType(
                            new IVertexType.Record(
                                    Uuids.makeUuid(),
                                    rootPkg.id,
                                    "V4",
                                    EAbstractness.ABSTRACT,
                                    rootVertexType.id
                            ),
                            rootPkg,
                            rootVertexType
                    );
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
