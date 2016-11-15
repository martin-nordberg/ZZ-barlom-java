//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.gdbpostgresql.impl;

import org.barlom.domain.metamodel.api.IMetamodelFacade;
import org.barlom.domain.metamodel.api.exceptions.MetamodelException;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.gdbpostgresql.GdbPostgreSqlSubsystem;
import org.barlom.persistence.postgresql.impl.PostgreSqlDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of metamodel facade for PostgreSQL.
 */
public class PostgreSqlMetamodelFacade
    implements IMetamodelFacade {

    public PostgreSqlMetamodelFacade(
        @SuppressWarnings( "UnusedParameters" ) GdbPostgreSqlSubsystem.Token token,
        PostgreSqlDataSource dataSource,
        String extraMigrationLocations
    ) {

        this.dataSource = dataSource;

        // Update the schema if needed.
        DatabaseMigration.updateDatabaseSchema( this.dataSource, extraMigrationLocations );

    }

    @Override
    public void deleteVertexType( UUID uuid ) throws MetamodelException {

        try ( IConnection connection = this.dataSource.openConnection() ) {

            Map<String, Object> params = new HashMap<>();
            params.put( "uuid", uuid );

            int deleted = connection.executeCountQuery( "SELECT DeleteVertexType( {{uuid}} )", params );

            assert deleted == 1 : "Deletion failed " + uuid + ". " + deleted + " records.";

        }

    }

    @Override
    public int findVertexTypeByUuid(
        UUID uuid, IVertexTypeQueryCallback callback
    ) throws MetamodelException {

        try ( IConnection connection = this.dataSource.openConnection() ) {

            Map<String, Object> params = new HashMap<>();
            params.put( "uuid", uuid );

            return connection.executeQuery(
                rs -> callback.handleVertexType( UUID.fromString( rs.getString( "uuid" ) ), rs.getString( "name" ) ),
                "SELECT uuid, name FROM FindVertexTypeByUuid( {{uuid}} )",
                params
            );

        }

    }

    @Override
    public void upsertVertexType( UUID uuid, String name ) throws MetamodelException {

        try ( IConnection connection = this.dataSource.openConnection() ) {

            Map<String, Object> params = new HashMap<>();
            params.put( "uuid", uuid );
            params.put( "name", name );

            int inserted = connection.executeCountQuery( "SELECT UpsertVertexType( {{uuid}}, {{name}} )", params );

            assert inserted == 1;

        }

    }

    private final PostgreSqlDataSource dataSource;

}
