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
 * Specification for package loading.
 */
class PackageLoaderSpec
        extends Specification {

    def "A package loader retrieves the top level root package"() {

        given:
        def dataSource = new H2DataSource();

        def ploader = new PackageLoader( dataSource );
        def aloader = new AttributeTypeLoader( dataSource );
        def vloader = new VertexTypeLoader( dataSource );
        def eloader = new EdgeTypeLoader( dataSource )

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, aloader, vloader, eloader );

        def rootPkg = m.findPackageRoot();

        expect:
        rootPkg.isPresent();
        rootPkg.get().name == "\$";
        rootPkg.get().parentPackage == rootPkg.get();
        m.findPackagesAll().size() == 1;
        m.findPackageById( rootPkg.get().id ).equals( rootPkg );
    }

}
