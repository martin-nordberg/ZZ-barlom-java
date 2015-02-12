//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes

/**
 * Spec for core behavior of a 32-bit integer attribute type..
 */
class Integer32AttributeTypeSpec
        extends AttributeTypeSpec {

    static def empty = OptionalInt.empty();

    static def zero = OptionalInt.of( 0 );

    static def fifty = OptionalInt.of( 50 );

    static def hundred = OptionalInt.of( 100 );

    def "A 32-bit integer attribute type generates correct JSON"() {

        expect:
        new Integer32AttributeType(
                id,
                parentPackage,
                name,
                minValue,
                maxValue,
                defaultValue
        ).toJson() == json;

        where:
        id  | parentPackage | name     | minValue | maxValue | defaultValue || json
        id1 | pkg1          | 'i32at1' | empty    | empty    | empty        || '{"id":"00000003-0000-0000-0000-000000000000","name":"i32at1","path":"pkg1.i32at1","parentPackageId":"00000001-0000-0000-0000-000000000000","dataType":"INTEGER32"}'
        id2 | pkg2          | 'i32at2' | zero     | empty    | empty        || '{"id":"00000004-0000-0000-0000-000000000000","name":"i32at2","path":"pkg1.pkg2.i32at2","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"INTEGER32","minValue":0}'
        id1 | pkg2          | 'i32at3' | empty    | hundred  | fifty        || '{"id":"00000003-0000-0000-0000-000000000000","name":"i32at3","path":"pkg1.pkg2.i32at3","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"INTEGER32","maxValue":100,"defaultValue":50}'
        id2 | root          | 'i32at4' | zero     | hundred  | empty        || '{"id":"00000004-0000-0000-0000-000000000000","name":"i32at4","path":"i32at4","parentPackageId":"00000000-0000-0000-0000-000000000000","dataType":"INTEGER32","minValue":0,"maxValue":100}'

    }

}
