//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.datasource.H2DataSource
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
        def dataSource = new H2DataSource();

        def ploader = new PackageLoader( dataSource );
        def vloader = new VertexTypeLoader( dataSource );
        def eloader = new EdgeTypeLoader( dataSource )

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, vloader, eloader );

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert m.findEdgeTypeById( IEdgeType.BASE_EDGE_TYPE.id ).get().name == "Edge";
            assert !m.findEdgeTypeById( IEdgeType.BASE_EDGE_TYPE.id ).get().superType.isPresent();
            assert m.findEdgeTypesAll().size() == 1;
        }

    }

}
