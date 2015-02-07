//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.attributes

/**
 * Spec for core behavior of a 64-bit floating point attribute type..
 */
class Float64AttributeTypeSpec
        extends AttributeTypeSpec {

    static def empty = OptionalDouble.empty();

    static def zero = OptionalDouble.of( 0.0D );

    static def fifty = OptionalDouble.of( 50.0D );

    static def hundred = OptionalDouble.of( 100.0D );

    def "A 64-bit floating point attribute type generates correct JSON"() {

        expect:
        new Float64AttributeType(
                id,
                parentPackage,
                name,
                minValue,
                maxValue,
                defaultValue
        ).toJson() == json;

        where:
        id  | parentPackage | name     | minValue | maxValue | defaultValue || json
        id1 | pkg1          | 'f64at1' | empty    | empty    | empty        || '{"id":"00000003-0000-0000-0000-000000000000","parentPackageId":"00000001-0000-0000-0000-000000000000","name":"f64at1","path":"pkg1.f64at1","dataType":"FLOAT64"}'
        id2 | pkg2          | 'f64at2' | zero     | empty    | empty        || '{"id":"00000004-0000-0000-0000-000000000000","parentPackageId":"00000002-0000-0000-0000-000000000000","name":"f64at2","path":"pkg1.pkg2.f64at2","dataType":"FLOAT64","minValue":0.0}'
        id1 | pkg2          | 'f64at3' | empty    | hundred  | fifty        || '{"id":"00000003-0000-0000-0000-000000000000","parentPackageId":"00000002-0000-0000-0000-000000000000","name":"f64at3","path":"pkg1.pkg2.f64at3","dataType":"FLOAT64","maxValue":100.0,"defaultValue":50.0}'
        id2 | root          | 'f64at4' | zero     | hundred  | empty        || '{"id":"00000004-0000-0000-0000-000000000000","parentPackageId":"00000000-0000-0000-0000-000000000000","name":"f64at4","path":"f64at4","dataType":"FLOAT64","minValue":0.0,"maxValue":100.0}'

    }

}
