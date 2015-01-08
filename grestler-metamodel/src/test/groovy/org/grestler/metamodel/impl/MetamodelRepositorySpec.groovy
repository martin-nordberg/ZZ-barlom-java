//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl

import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.api.elements.IVertexType
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader
import org.grestler.metamodel.spi.elements.IPackageLoader
import org.grestler.metamodel.spi.elements.IVertexTypeLoader
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for a metamodel repository.
 */
class MetamodelRepositorySpec
        extends Specification {

    UUID id1 = Uuids.makeUuid();

    def "A metamodel repository lets added vertex types be retrieved"() {

        given:
        IMetamodelRepositorySpi m
        StmTransactionContext.doInTransaction( 1 ) {

            m = new MetamodelRepository(
                    new IPackageLoader() {

                        @Override
                        void loadAllPackages( IMetamodelRepositorySpi repository ) {
                        }
                    }, new IVertexTypeLoader() {

                @Override
                void loadAllVertexTypes( IMetamodelRepositorySpi r ) {
                    r.loadVertexType( id1, IPackage.ROOT_PACKAGE, "V1", IVertexType.BASE_VERTEX_TYPE );
                    r.loadVertexType( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "V2", IVertexType.BASE_VERTEX_TYPE );
                    r.loadVertexType( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "V3", IVertexType.BASE_VERTEX_TYPE );
                    r.loadVertexType( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "V4", IVertexType.BASE_VERTEX_TYPE );
                }
            }, new IEdgeTypeLoader() {

                @Override
                void loadAllEdgeTypes( IMetamodelRepositorySpi repository ) {
                }
            }
            );

        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert m.findVertexTypeById( id1 ).get().name == "V1";
            assert m.findVertexTypesAll().size() == 5;
        }

    }

}
