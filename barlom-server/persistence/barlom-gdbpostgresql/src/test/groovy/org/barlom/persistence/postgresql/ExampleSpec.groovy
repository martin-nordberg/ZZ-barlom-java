package org.barlom.persistence.postgresql

import org.barlom.domain.metamodel.impl.queries.MetamodelRepository
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi
import org.barlom.infrastructure.utilities.revisions.StmTransactionContext
import org.barlom.infrastructure.utilities.uuids.Uuids
import org.barlom.persistence.postgresql.impl.PostgreSqlDataSource
import spock.lang.Specification

/**
 * PostgreSQL connection testing.
 */
class ExampleSpec
    extends Specification {

    def "PostgreSQL can be accessed by JDBC"() {

        expect:
        Example.connect();
        Example.canConnectWithPool();

    }

    def "PostgreSQL can be accessed by JDBC data source"() {

        given:
        def dataSource = new PostgreSqlDataSource( "test" );
        def connection = dataSource.connection;

        expect:
        !connection.closed;

        cleanup:
        connection.close();

    }

}
