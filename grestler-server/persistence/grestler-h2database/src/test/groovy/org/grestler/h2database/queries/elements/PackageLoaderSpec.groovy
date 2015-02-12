//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.impl.H2DataSource
import org.grestler.h2database.queries.attributes.AttributeTypeLoader
import org.grestler.metamodel.impl.metamodel.MetamodelRepository
import org.grestler.metamodel.spi.metamodel.IMetamodelRepositorySpi
import spock.lang.Specification

/**
 * Specification for package loading.
 */
class PackageLoaderSpec
        extends Specification {

    def "A package loader retrieves the top level root package"() {

        given:
        def dataSource = new H2DataSource( "test0" );

        def ploader = new PackageLoader( dataSource );
        def pdloader = new PackageDependencyLoader( dataSource );
        def atloader = new AttributeTypeLoader( dataSource );
        def vtloader = new VertexTypeLoader( dataSource );
        def etloader = new EdgeTypeLoader( dataSource )
        def adloader = new AttributeDeclLoader( dataSource );

        IMetamodelRepositorySpi m = new MetamodelRepository( ploader, pdloader, atloader, vtloader, etloader, adloader );

        def rootPkg = m.findPackageRoot();

        expect:
        rootPkg.isPresent();
        rootPkg.get().name == "\$";
        rootPkg.get().parentPackage == rootPkg.get();
        m.findPackagesAll().size() == 1;
        m.findPackageById( rootPkg.get().id ).equals( rootPkg );
    }

}
