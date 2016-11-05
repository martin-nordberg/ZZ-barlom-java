package org.barlom.persistence.postgresql.impl

import org.barlom.domain.metamodel.api.IMetamodelFacade
import org.barlom.infrastructure.utilities.uuids.Uuids
import org.barlom.persistence.dbutilities.api.IConnection
import org.barlom.persistence.dbutilities.api.IDataSource
import org.barlom.persistence.dbutilities.api.IResultSet
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

        IDataSource dataSource = new PostgreSqlDataSource( "test" );
        IMetamodelFacade facade = new PostgreSqlMetamodelFacade( dataSource );

        facade.upsertVertexType( uuid, name );

        int foundByUuid = facade.findVertexTypeByUuid( uuid, readResult );

        facade.deleteVertexType( uuid );

        expect:
        foundByUuid == 1;
        count >= 1;

        cleanup:
        dataSource.close();

    }

}
