//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.attributes

import org.grestler.h2database.impl.H2DataSource
import org.grestler.h2database.queries.elements.AttributeDeclLoader
import org.grestler.h2database.queries.elements.EdgeTypeLoader
import org.grestler.h2database.queries.elements.PackageDependencyLoader
import org.grestler.h2database.queries.elements.PackageLoader
import org.grestler.h2database.queries.elements.VertexTypeLoader
import org.grestler.metamodel.impl.metamodel.MetamodelRepository
import org.grestler.metamodel.spi.metamodel.IMetamodelRepositorySpi
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for attribute type loading.
 */
class AttributeTypeLoaderSpec
        extends Specification {

    def "An attribute type loader retrieves nothing (for starters)"() {

        given:
        def dataSource = new H2DataSource( "test0" );

        def ploader = new PackageLoader( dataSource );
        def pdloader = new PackageDependencyLoader( dataSource );
        def atloader = new AttributeTypeLoader( dataSource );
        def vtloader = new VertexTypeLoader( dataSource );
        def etloader = new EdgeTypeLoader( dataSource )
        def adloader = new AttributeDeclLoader( dataSource );

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, pdloader, atloader, vtloader, etloader, adloader );

        expect:
        !m.findAttributeTypeById( Uuids.makeUuid() ).isPresent();
        m.findAttributeTypesAll().size() == 0;

    }

}
