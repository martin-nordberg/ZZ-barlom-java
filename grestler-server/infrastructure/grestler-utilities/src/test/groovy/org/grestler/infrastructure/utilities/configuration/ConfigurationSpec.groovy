package org.grestler.infrastructure.utilities.configuration

import spock.lang.Specification

/**
 * Specification for org.grestler.infrastructure.utilities.configuration.Configuration.
 */
class ConfigurationSpec
    extends Specification {

    def "Sample properties can be read"() {

        given:
        def config = new Configuration( ConfigurationSpec.class )

        when:
        def result = config.readString( key )

        then:
        result == expectedResult

        where:
        key    | expectedResult
        "key1" | "value1"
        "key2" | "value2"

    }

    def "An integer property can be read"() {

        given:
        def config = new Configuration( ConfigurationSpec.class )

        when:
        def result = config.readInt( key )

        then:
        result == expectedResult

        where:
        key     | expectedResult
        "key1i" | 1
        "key2i" | 2

    }

    def "A boolean property can be read"() {

        given:
        def config = new Configuration( ConfigurationSpec.class )

        when:
        def result = config.readBoolean( key )

        then:
        result == expectedResult

        where:
        key     | expectedResult
        "key1b" | true
        "key2b" | true
        "key3b" | true
        "key4b" | false
        "key5b" | false
        "key6b" | false

    }

    def "A string list of properties can be read"() {

        given:
        def config = new Configuration( ConfigurationSpec.class )

        def result = config.readStrings( "key7" )

        expect:
        result[0] == "one"
        result[1] == "two"
        result[2] == "three"

    }

}
