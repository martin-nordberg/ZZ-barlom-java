//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements
/**
 * Spec for core behavior of a string attribute type..
 */
class StringAttributeTypeSpec
        extends AttributeTypeSpec {

    static def empty = OptionalInt.empty();

    static def zero = OptionalInt.of( 0 );

    static def fifty = OptionalInt.of( 50 );

    static Optional<String> emptyPattern = Optional.empty();

    static def lowerCase = Optional.of( "[a-z]*" );

    def "A string attribute type generates correct JSON"() {

        expect:
        new StringAttributeType(
                id,
                parentPackage,
                name,
                minLength,
                maxLength,
                regexPattern
        ).toJson() == json;

        where:
        id  | parentPackage | name   | minLength | maxLength | regexPattern || json
        id1 | pkg1          | 'sat1' | empty     | 100       | emptyPattern || '{"id":"00000003-0000-0000-0000-000000000000","name":"sat1","path":"pkg1.sat1","parentPackageId":"00000001-0000-0000-0000-000000000000","dataType":"STRING","maxLength":100}'
        id2 | pkg2          | 'sat2' | zero      | 200       | emptyPattern || '{"id":"00000004-0000-0000-0000-000000000000","name":"sat2","path":"pkg1.pkg2.sat2","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"STRING","minLength":0,"maxLength":200}'
        id1 | pkg2          | 'sat3' | empty     | 100       | emptyPattern || '{"id":"00000003-0000-0000-0000-000000000000","name":"sat3","path":"pkg1.pkg2.sat3","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"STRING","maxLength":100}'
        id2 | root          | 'sat4' | fifty     | 500       | lowerCase    || '{"id":"00000004-0000-0000-0000-000000000000","name":"sat4","path":"sat4","parentPackageId":"00000000-0000-0000-0000-000000000000","dataType":"STRING","minLength":50,"maxLength":500,"regexPattern":"[a-z]*"}'

    }

}
