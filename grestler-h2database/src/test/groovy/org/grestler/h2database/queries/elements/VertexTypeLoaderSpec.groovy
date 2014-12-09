//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.H2DataSourceDefinition
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import spock.lang.Specification

/**
 * Specification for vertex type loading.
 */
class VertexTypeLoaderSpec extends Specification {

    def "A vertex loader retrieves the top level base vertex type"() {

        given:
        IMetamodelRepositorySpi m
        StmTransactionContext.doInTransaction( 1 ) {
            m = new MetamodelRepository();

            def loader = new VertexTypeLoader( new H2DataSourceDefinition().makeDataSource() );

            loader.loadAllVertexTypes( m );
        }

        expect:
        StmTransactionContext.doInTransaction(1) {
            assert m.findVertexTypeByName( "Vertex" ).get().name == "Vertex";
            assert !m.findVertexTypeByName( "Vertex" ).get().superType.isPresent();
            assert m.findVertexTypesAll().size() == 1;
        }

    }

}
