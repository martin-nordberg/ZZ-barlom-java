//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.api.elements.IVertexType
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for packages.
 */
class PackageSpec
        extends Specification {

    UUID id = Uuids.makeUuid();

    String name = "Example";

    def "A top level package is constructed and read"() {

        given:
        IPackage p = IPackage.ROOT_PACKAGE;

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert p.name == "\$";
            assert !p.parentPackage.isPresent();
            assert !p.isChildOf( p );
        }

    }

    def "Package parents can be detected correctly"() {

        given:
        IPackage p1;
        IPackage p2;
        IPackage r;
        StmTransactionContext.doInTransaction( 1 ) {
            p1 = new Package( id, IPackage.ROOT_PACKAGE, name );
            p2 = new Package( id, p1, name );
            r = new Package( id, IPackage.ROOT_PACKAGE, name );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert p1.parentPackage.get() == IPackage.ROOT_PACKAGE;
            assert p1.isChildOf( IPackage.ROOT_PACKAGE );
            assert !p1.isChildOf( p1 );
            assert p2.parentPackage.get() == p1;
            assert p2.isChildOf( p1 );
            assert !p1.isChildOf( p2 );
            assert !r.isChildOf( p1 );
        }

    }

}
