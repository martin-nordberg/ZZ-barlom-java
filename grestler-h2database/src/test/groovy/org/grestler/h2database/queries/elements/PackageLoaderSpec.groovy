//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements

import org.grestler.h2database.datasource.H2DataSource
import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.impl.MetamodelRepository
import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import spock.lang.Specification

/**
 * Specification for vertex type loading.
 */
class PackageLoaderSpec
        extends Specification {

    def "A package loader retrieves the top level root package"() {

        given:
        IMetamodelRepositorySpi m
        StmTransactionContext.doInTransaction( 1 ) {
            m = new MetamodelRepository();

            def loader = new PackageLoader( new H2DataSource() );

            loader.loadAllPackages( m );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert m.findPackageById( IPackage.ROOT_PACKAGE.id ).get().name == "\$";
            assert !m.findPackageById( IPackage.ROOT_PACKAGE.id ).get().parentPackage.isPresent();
            assert m.findPackagesAll().size() == 1;
        }

    }

}
