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
class BooleanAttributeTypeSpec
        extends Specification {

    static UUID rootId = UUID.fromString( '00000000-0000-0000-0000-000000000000' );

    static UUID pkgId1 = UUID.fromString( '00000001-0000-0000-0000-000000000000' );

    static UUID pkgId2 = UUID.fromString( '00000002-0000-0000-0000-000000000000' );

    static UUID id1 = UUID.fromString( '00000003-0000-0000-0000-000000000000' );

    static UUID id2 = UUID.fromString( '00000004-0000-0000-0000-000000000000' );

    static IPackage root = new RootPackage( rootId );

    static IPackage pkg1 = new Package( pkgId1, root, 'pkg1' );

    static IPackage pkg2 = new Package( pkgId2, pkg1, 'pkg2' );

    def "A boolean attribute type generates correct JSON"() {

        expect:
        new BooleanAttributeType( id, parentPackage, name, Optional.ofNullable( defaultValue ) ).toJson() == json;

        where:
        id  | parentPackage | name   | defaultValue || json
        id1 | pkg1          | 'bat1' | null         || '{"id":"00000003-0000-0000-0000-000000000000","parentPackageId":"00000001-0000-0000-0000-000000000000","name":"bat1","path":"pkg1.bat1","dataType":"BOOLEAN"}'
        id2 | pkg2          | 'bat2' | true         || '{"id":"00000004-0000-0000-0000-000000000000","parentPackageId":"00000002-0000-0000-0000-000000000000","name":"bat2","path":"pkg1.pkg2.bat2","dataType":"BOOLEAN","defaultValue":true}'
        id1 | pkg2          | 'bat3' | false        || '{"id":"00000003-0000-0000-0000-000000000000","parentPackageId":"00000002-0000-0000-0000-000000000000","name":"bat3","path":"pkg1.pkg2.bat3","dataType":"BOOLEAN","defaultValue":false}'

    }
}
