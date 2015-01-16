//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.attributes

import org.grestler.h2database.datasource.H2DataSource
import org.grestler.h2database.queries.elements.EdgeTypeLoader
import org.grestler.h2database.queries.elements.PackageLoader
import org.grestler.h2database.queries.elements.VertexTypeLoader
import org.grestler.metamodel.api.elements.IEdgeType
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import spock.lang.Specification

/**
 * Specification for attribute type loading.
 */
class AttributeTypeLoaderSpec
        extends Specification {

    def "An edge loader retrieves the top level base edge type"() {

        given:
        def dataSource = new H2DataSource();

        def ploader = new PackageLoader( dataSource );
        def aloader = new AttributeTypeLoader( dataSource );
        def vloader = new VertexTypeLoader( dataSource );
        def eloader = new EdgeTypeLoader( dataSource )

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, aloader, vloader, eloader );

        expect:
        !m.findAttributeTypeById( IEdgeType.BASE_EDGE_TYPE.id ).isPresent();
        m.findAttributeTypesAll().size() == 0;

    }

}
