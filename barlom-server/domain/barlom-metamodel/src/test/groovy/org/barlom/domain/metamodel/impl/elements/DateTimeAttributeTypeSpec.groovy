//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements

import org.barlom.domain.metamodel.api.elements.IDateTimeAttributeType

import java.time.Instant

/**
 * Spec for core behavior of a date/time attribute type..
 */
class DateTimeAttributeTypeSpec
    extends AttributeTypeSpec {

    static def dt1 = Instant.parse( '2015-02-01T05:15:40.000Z' );

    static def dt2 = Instant.parse( '2018-07-10T23:59:59.123Z' );

    def "A date/time attribute type generates correct JSON"() {

        expect:
        new DateTimeAttributeType(
            new IDateTimeAttributeType.Record(
                id,
                parentPackage.id,
                name,
                Optional.ofNullable( minValue ),
                Optional.ofNullable( maxValue )
            ),
            parentPackage
        ).toJson() == json;

        where:
        id  | parentPackage | name    | minValue | maxValue || json
        id1 | pkg1          | 'dtat1' | null     | null     || '{"id":"00000003-0000-0000-0000-000000000000","name":"dtat1","path":"pkg1.dtat1","parentPackageId":"00000001-0000-0000-0000-000000000000","dataType":"DATETIME"}'
        id2 | pkg2          | 'dtat2' | dt1      | null     || '{"id":"00000004-0000-0000-0000-000000000000","name":"dtat2","path":"pkg1.pkg2.dtat2","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"DATETIME","minValue":"2015-02-01T05:15:40.000Z"}'
        id1 | pkg2          | 'dtat3' | null     | dt1      || '{"id":"00000003-0000-0000-0000-000000000000","name":"dtat3","path":"pkg1.pkg2.dtat3","parentPackageId":"00000002-0000-0000-0000-000000000000","dataType":"DATETIME","maxValue":"2015-02-01T05:15:40.000Z"}'
        id2 | root          | 'dtat4' | dt1      | dt2      || '{"id":"00000004-0000-0000-0000-000000000000","name":"dtat4","path":"dtat4","parentPackageId":"00000000-0000-0000-0000-000000000000","dataType":"DATETIME","maxValue":"2018-07-10T23:59:59.123Z","minValue":"2015-02-01T05:15:40.000Z"}'

    }

}
