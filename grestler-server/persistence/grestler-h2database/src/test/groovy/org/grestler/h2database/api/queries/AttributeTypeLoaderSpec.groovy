//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.api.queries

import org.grestler.h2database.impl.H2DataSource
import org.grestler.metamodel.impl.queries.MetamodelRepository
import org.grestler.metamodel.spi.queries.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
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

        expect:
        !m.findOptionalAttributeTypeById( Uuids.makeUuid() ).isPresent();
        m.findAllAttributeTypes().size() == 0;

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
