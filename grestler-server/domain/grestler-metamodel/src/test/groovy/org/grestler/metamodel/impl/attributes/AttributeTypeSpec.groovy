//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes

import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.impl.elements.Package
import org.grestler.metamodel.impl.elements.RootPackage
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

    static IPackage root = new RootPackage( rootId );

    static IPackage pkg1 = new Package( pkgId1, root, 'pkg1' );

    static IPackage pkg2 = new Package( pkgId2, pkg1, 'pkg2' );

}
