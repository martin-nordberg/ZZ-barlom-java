//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements
/**
 * Specification for isolated boolean attribute type behavior.
 */
class BooleanAttributeTypeSpec
        extends AttributeTypeSpec {

    def "A boolean attribute type generates correct JSON"() {

        expect:
        new BooleanAttributeType( id, parentPackage, name, Optional.ofNullable( defaultValue ) ).toJson() == json;

        where:
        id  | parentPackage | name   | defaultValue || json
        id1 | pkg1          | 'bat1' | null         || '{"id":"00000003-0000-0000-0000-000000000000","name":"bat1","path":"pkg1.bat1","parentPackageId":"00000001-0000-0000-0000-000000000000","dataType":"BOOLEAN"}'
        id2 | pkg2          | 'bat2' | true         || '{"id":"00000004-0000-0000-0000-000000000000","name":"bat2","path":"pkg1.pkg2.bat2","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"BOOLEAN","defaultValue":true}'
        id1 | pkg2          | 'bat3' | false        || '{"id":"00000003-0000-0000-0000-000000000000","name":"bat3","path":"pkg1.pkg2.bat3","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"BOOLEAN","defaultValue":false}'

    }

}
