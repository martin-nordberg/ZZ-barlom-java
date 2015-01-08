//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.datasource.H2DataSource
import org.grestler.metamodel.api.elements.IVertexType
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import spock.lang.Specification

/**
 * Specification for vertex type loading.
 */
class VertexTypeLoaderSpec
        extends Specification {

    def "A vertex loader retrieves the top level base vertex type"() {

        given:
        IMetamodelRepositorySpi m
        StmTransactionContext.doInTransaction( 1 ) {
            def dataSource = new H2DataSource();

            def ploader = new PackageLoader( dataSource );
            def vloader = new VertexTypeLoader( dataSource );
            def eloader = new EdgeTypeLoader( dataSource )

            m = new MetamodelRepository( ploader, vloader, eloader );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert m.findVertexTypeById( IVertexType.BASE_VERTEX_TYPE.id ).get().name == "Vertex";
            assert !m.findVertexTypeById( IVertexType.BASE_VERTEX_TYPE.id ).get().superType.isPresent();
            assert m.findVertexTypesAll().size() == 1;
        }

    }

}
