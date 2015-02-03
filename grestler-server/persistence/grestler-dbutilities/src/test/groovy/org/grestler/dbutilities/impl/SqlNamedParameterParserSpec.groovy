//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities.impl

import spock.lang.Specification

/**
 * Spec for SQL named parameter substitution.
 */
class SqlNamedParameterParserSpec
        extends Specification {

    def "SQL named parameter parsing replaces named parameters with question marks"() {

        given:
        def params = [:];

        expect:
        SqlNamedParameterParser.parseSqlParameters( namedSql, params ).sql == questSql;

        where:
        namedSql                                             | questSql
        "SELECT * FROM X WHERE A = {{a}}"                    | "SELECT * FROM X WHERE A = ?"
        "INSERT INTO X (A,B,C) VALUES ({{a}}, {{b}}, {{c}})" | "INSERT INTO X (A,B,C) VALUES (?, ?, ?)"
        "DELETE FROM X WHERE ID = {{uglyLookingParam123}}"   | "DELETE FROM X WHERE ID = ?"

    }

    def "SQL named parameter parsing stages arguments in order"() {
        given:
        def params = [x: 1, y: 2, z: "three"];
        def sql = "INSERT INTO X (A,B,C) VALUES ({{z}}, {{x}}, {{y}})";
        def args = SqlNamedParameterParser.parseSqlParameters( sql, params ).arguments;

        expect:
        args[1] == 1;
        args[2] == 2;
        args[0] == "three";

    }

}
