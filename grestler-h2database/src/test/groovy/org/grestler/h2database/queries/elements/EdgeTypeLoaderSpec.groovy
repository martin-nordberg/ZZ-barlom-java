//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.H2DataSourceDefinition
import org.grestler.metamodel.api.elements.IEdgeType
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import spock.lang.Specification

/**
 * Specification for edge type loading.
 */
class EdgeTypeLoaderSpec
        extends Specification {

    def "An edge loader retrieves the top level base edge type"() {

        given:
        IMetamodelRepositorySpi m
        StmTransactionContext.doInTransaction( 1 ) {
            m = new MetamodelRepository();

            def dataSource = new H2DataSourceDefinition().makeDataSource();

            def vloader = new VertexTypeLoader( dataSource );

            vloader.loadAllVertexTypes( m );

            def eloader = new EdgeTypeLoader( dataSource )

            eloader.loadAllEdgeTypes( m );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert m.findEdgeTypeById( IEdgeType.BASE_EDGE_TYPE.id ).get().name == "Edge";
            assert !m.findEdgeTypeById( IEdgeType.BASE_EDGE_TYPE.id ).get().superType.isPresent();
            assert m.findEdgeTypesAll().size() == 1;
        }

    }

}
