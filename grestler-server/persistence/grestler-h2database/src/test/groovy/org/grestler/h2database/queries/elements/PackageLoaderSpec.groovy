//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.datasource.H2DataSource
import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import spock.lang.Specification

/**
 * Specification for package loading.
 */
class PackageLoaderSpec
        extends Specification {

    def "A package loader retrieves the top level root package"() {

        given:
        def dataSource = new H2DataSource();

        def ploader = new PackageLoader( dataSource );
        def vloader = new VertexTypeLoader( dataSource );
        def eloader = new EdgeTypeLoader( dataSource )

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, vloader, eloader );

        def rootPkg = m.findPackageById( IPackage.ROOT_PACKAGE.id ).get();

        expect:
        rootPkg.name == "\$";
        rootPkg.parentPackage == rootPkg;
        m.findPackagesAll().size() == 1;

    }

}
