//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.impl.H2DataSource
import org.grestler.h2database.queries.attributes.AttributeTypeLoader
import org.grestler.metamodel.impl.cmdquery.MetamodelRepository
import org.grestler.metamodel.spi.cmdquery.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
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
        def etloader = new EdgeTypeLoader( dataSource )
        def adloader = new AttributeDeclLoader( dataSource );

        IMetamodelRepositorySpi m = new MetamodelRepository(
                ploader,
                pdloader,
                atloader,
                vtloader,
                etloader,
                adloader
        );

        def rootVertexType = m.findVertexTypeBase();

        expect:
        rootVertexType.isPresent();
        rootVertexType.get().name == "Vertex";
        !rootVertexType.get().superType.isPresent();
        m.findVertexTypesAll().size() == 1;
        m.findVertexTypeById( rootVertexType.get().id ).equals( rootVertexType );

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
