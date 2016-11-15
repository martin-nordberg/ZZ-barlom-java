//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.gdbpostgresql

import org.barlom.domain.metamodel.api.IMetamodelFacade
import org.barlom.infrastructure.utilities.uuids.Uuids
import org.barlom.persistence.postgresql.PostgreSqlSubsystem
import spock.lang.Specification

/**
 * Specification for the PostgreSQL metamodel facade.
 */
class MetamodelFacadeSpec
  extends Specification {

    def "PostgreSQL metamodel facade CRUDs vertex types"() {

        given:
        UUID uuid = Uuids.makeUuid();
        String name = "Vertex Type Ten";

        def count = 0;
        def readResult = { UUID readUuid, String readName ->
            assert readUuid == uuid;
            assert readName == name;
            count += 1;
        }

        PostgreSqlSubsystem postgreSqlSubsystem = new PostgreSqlSubsystem( "test" );
        GdbPostgreSqlSubsystem gdbPostgreSqlSubsystem = new GdbPostgreSqlSubsystem( postgreSqlSubsystem );
        IMetamodelFacade facade = gdbPostgreSqlSubsystem.provideMetamodelFacade();

        facade.upsertVertexType( uuid, name );

        int foundByUuid = facade.findVertexTypeByUuid( uuid, readResult );

        facade.deleteVertexType( uuid );

        expect:
        foundByUuid == 1;
        count >= 1;

        cleanup:
        postgreSqlSubsystem.provideDataSource().close();

    }

}
