//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.queries

import org.barlom.domain.metamodel.impl.queries.MetamodelRepository
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.barlom.infrastructure.utilities.revisions.StmTransactionContext
import org.barlom.persistence.h2database.impl.H2DataSource
import spock.lang.Specification

/**
 * Specification for package loading.
 */
class PackageLoaderSpec
    extends Specification {

    def "A package loader retrieves the top level root package"() {

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

        def rootPkg = m.findRootPackage();

        expect:
        rootPkg != null;
        rootPkg.name == "\$";
        rootPkg.parentPackage == rootPkg;
        m.findAllPackages().size() == 1;
        m.findPackageById( rootPkg.id ).equals( rootPkg );

        cleanup:
        StmTransactionContext.commitTransaction();
    }

}
