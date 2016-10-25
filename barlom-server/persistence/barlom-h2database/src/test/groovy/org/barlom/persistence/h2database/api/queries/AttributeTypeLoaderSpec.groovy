//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.queries

import org.barlom.domain.metamodel.impl.queries.MetamodelRepository
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.barlom.infrastructure.utilities.revisions.StmTransactionContext
import org.barlom.infrastructure.utilities.uuids.Uuids
import org.barlom.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

/**
 * Specification for attribute type loading.
 */
class AttributeTypeLoaderSpec
    extends Specification {

    def "An attribute type loader retrieves nothing (for starters)"() {

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

        expect:
        !m.findOptionalAttributeTypeById( Uuids.makeUuid() ).isPresent();
        m.findAllAttributeTypes().size() == 0;

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
