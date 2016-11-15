package org.barlom.persistence.postgresql

import org.barlom.persistence.dbutilities.api.IConnection
import org.barlom.persistence.dbutilities.api.IDataSource
import org.barlom.persistence.dbutilities.api.IResultSet
import spock.lang.Specification

/**
 * Specification for PostgreSqlDataSource.
 */
public class PostgreSqlDataSourceSpec
    extends Specification {

    def "PostgreSQL can be accessed by JDBC data source"() {

        given:
        def count = 0;
        def readResult = { IResultSet rs ->
            assert rs.getInt( "one" ) == 1;
            assert rs.getString( "two" ) == "TWO";
            assert rs.getInt( "three" ) == 3;
            count += 1;
        }

        IDataSource dataSource = new GdbPostgreSqlSubsystem( "test" ).provideDataSource();
        IConnection connection = dataSource.openConnection();
        connection.executeQuery( readResult, "SELECT 1 one, 'TWO' two, 3 three" );

        expect:
        count == 1;

        cleanup:
        connection.close();
        dataSource.close();

    }

}
