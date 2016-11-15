//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.gdbpostgresql

import org.barlom.infrastructure.utilities.uuids.Uuids
import org.barlom.persistence.dbutilities.api.IConnection
import org.barlom.persistence.dbutilities.api.IDataSource
import org.barlom.persistence.dbutilities.api.IResultSet
import org.barlom.persistence.postgresql.PostgreSqlSubsystem
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

        PostgreSqlSubsystem postgreSqlSubsystem = new PostgreSqlSubsystem( "test" );
        GdbPostgreSqlSubsystem gdbPostgreSqlSubsystem = new GdbPostgreSqlSubsystem( postgreSqlSubsystem );
        IDataSource dataSource = postgreSqlSubsystem.provideDataSource();
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
