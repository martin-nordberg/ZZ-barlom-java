package org.barlom.persistence.postgresql

import org.barlom.domain.metamodel.api.IMetamodelFacade
import org.barlom.infrastructure.utilities.uuids.Uuids
import org.barlom.persistence.dbutilities.api.IConnection
import org.barlom.persistence.dbutilities.api.IDataSource
import org.barlom.persistence.dbutilities.api.IResultSet
import spock.lang.Specification

/**
 * Experiments with PostgreSQL.
 */
class Experiments
  extends Specification {

    def "PostgreSQL can be accessed by JDBC data source"() {

        given:
        def count = 0;
        def readResult = { IResultSet rs ->
            assert rs.getString( "uuid" ).size() == 36;
            assert rs.getString( "name" ).size() > 0;
            count += 1;
        }

        GdbPostgreSqlSubsystem gdbPostgreSqlSubsystem = new GdbPostgreSqlSubsystem( "test" );
        IDataSource dataSource = gdbPostgreSqlSubsystem.provideDataSource();
        gdbPostgreSqlSubsystem.provideMetamodelFacade();

        IConnection connection = dataSource.openConnection();

        Map<String,Object> params = new HashMap<>();
        params.put( "uuid", Uuids.makeUuid() );
        params.put( "name", "Vertex Type Four" );

        int inserted = connection.executeCountQuery( "SELECT UpsertVertexType( {{uuid}}, {{name}} )", params );

        connection.executeQuery( readResult, "SELECT uuid, name FROM FindVertexTypesAll()" );

        int deleted = connection.executeCountQuery( "SELECT DeleteVertexType( {{uuid}} )", params );

        expect:
        inserted == 1;
        count >= 1;
        deleted == 1;

        cleanup:
        connection.close();
        dataSource.close();

    }

}
