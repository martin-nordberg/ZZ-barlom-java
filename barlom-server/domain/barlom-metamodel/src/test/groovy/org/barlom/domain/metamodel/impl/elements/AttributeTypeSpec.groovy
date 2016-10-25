//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IPackage
import org.barlom.infrastructure.utilities.revisions.StmTransactionContext
import spock.lang.Specification

/**
 * Specification for isolated boolean attribute type behavior.
 */
class AttributeTypeSpec
    extends Specification {

    static UUID rootId = UUID.fromString( '00000000-0000-0000-0000-000000000000' );

    static UUID pkgId1 = UUID.fromString( '00000001-0000-0000-0000-000000000000' );

    static UUID pkgId2 = UUID.fromString( '00000002-0000-0000-0000-000000000000' );

    static UUID id1 = UUID.fromString( '00000003-0000-0000-0000-000000000000' );

    static UUID id2 = UUID.fromString( '00000004-0000-0000-0000-000000000000' );

    static IPackage root;

    static IPackage pkg1;

    static IPackage pkg2;

    def setup() {
        StmTransactionContext.beginReadWriteTransaction();
    }

    def setupSpec() {
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            root = new RootPackage( rootId );
            pkg1 = new Package( new IPackage.Record( pkgId1, root.id, 'pkg1' ), root );
            pkg2 = new Package( new IPackage.Record( pkgId2, pkg1.id, 'pkg2' ), pkg1 );
        }
    }

    def cleanup() {
        StmTransactionContext.commitTransaction();
    }

}
