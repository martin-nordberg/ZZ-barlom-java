//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries

import org.grestler.domain.metamodel.impl.queries.MetamodelRepository
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

/**
 * Specification for vertex type loading.
 */
class VertexTypeLoaderSpec
    extends Specification {

    def "A vertex type loader retrieves the top level base vertex type"() {

        given:
        StmTransactionContext.beginReadWriteTransaction();

        def dataSource = new H2DataSource( "test0" );

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

        def rootVertexType = m.findRootVertexType();

        expect:
        rootVertexType != null;
        rootVertexType.name == "Vertex";
        !rootVertexType.superType.isPresent();
        m.findAllVertexTypes().size() == 1;
        m.findVertexTypeById( rootVertexType.id ).equals( rootVertexType );

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
