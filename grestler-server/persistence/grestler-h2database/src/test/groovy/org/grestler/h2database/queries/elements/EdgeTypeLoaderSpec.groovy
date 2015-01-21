//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.datasource.H2DataSource
import org.grestler.h2database.queries.attributes.AttributeTypeLoader
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
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
        def aloader = new AttributeTypeLoader( dataSource );
        def vloader = new VertexTypeLoader( dataSource );
        def eloader = new EdgeTypeLoader( dataSource )

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, aloader, vloader, eloader );

        def rootEdgeType = m.findEdgeTypeRoot();

        expect:
        rootEdgeType.isPresent();
        rootEdgeType.get().name == "Edge";
        !rootEdgeType.get().superType.isPresent();
        m.findEdgeTypesAll().size() == 1;
        m.findEdgeTypeById( rootEdgeType.get().id ).equals( rootEdgeType );

    }

}
