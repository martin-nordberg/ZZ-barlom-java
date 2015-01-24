//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import org.grestler.dbutilities.IDataSource;
import org.grestler.dbutilities.JdbcConnection;
import org.grestler.h2database.H2DatabaseException;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IAttributeDeclLoader;
import org.grestler.utilities.configuration.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for loading all attribute declarations into a metamodel repository.
 */
public class AttributeDeclLoader
    implements IAttributeDeclLoader {

    /**
     * Constructs a new DAO for attribute types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public AttributeDeclLoader( IDataSource dataSource ) {

        this.dataSource = dataSource;

        this.config = new Configuration( H2DatabaseModule.class );

    }

    @Override
    public void loadAllAttributeDecls( IMetamodelRepositorySpi repository ) {

        this.loadAllVertexAttributeDecls( repository );
        this.loadAllEdgeAttributeDecls( repository );

    }

    /**
     * Finds a UUID attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IEdgeAttributeDecl findOrCreateEdgeAttributeDecl(
        EdgeAttributeDeclRecord record, IMetamodelRepositorySpi repository
    ) {

        // Find the parent package
        Optional<IEdgeType> parentEdgeType = repository.findEdgeTypeById( record.parentEdgeTypeId );
        Optional<IAttributeType> attributeType = repository.findAttributeTypeById( record.attributeTypeId );

        return repository.loadEdgeAttributeDecl(
            record.id, parentEdgeType.get(), record.name, attributeType.get(), record.optionality
        );

    }

    /**
     * Finds a boolean attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IVertexAttributeDecl findOrCreateVertexAttributeDecl(
        VertexAttributeDeclRecord record, IMetamodelRepositorySpi repository
    ) {

        // Find the parent vertex type and the attribute type
        Optional<IVertexType> parentVertexType = repository.findVertexTypeById( record.parentVertexTypeId );
        Optional<IAttributeType> attributeType = repository.findAttributeTypeById( record.attributeTypeId );

        return repository.loadVertexAttributeDecl(
            record.id, parentVertexType.get(), record.name, attributeType.get(), record.optionality
        );

    }

    /**
     * Loads all the UUID attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllEdgeAttributeDecls( IMetamodelRepositorySpi repository ) {

        Collection<EdgeAttributeDeclRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new EdgeAttributeDeclRecord( rs ) ),
                    this.config.readString( "EdgeAttributeDecl.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Edge attribute declaration loading failed.", e );
        }

        // Copy the results into the repository.
        for ( EdgeAttributeDeclRecord atRecord : atRecords ) {
            AttributeDeclLoader.findOrCreateEdgeAttributeDecl( atRecord, repository );
        }

    }

    /**
     * Loads all the boolean attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllVertexAttributeDecls( IMetamodelRepositorySpi repository ) {

        Collection<VertexAttributeDeclRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new VertexAttributeDeclRecord( rs ) ),
                    this.config.readString( "VertexAttributeDecl.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Vertex attribute declaration loading failed.", e );
        }

        // Copy the results into the repository.
        for ( VertexAttributeDeclRecord atRecord : atRecords ) {
            AttributeDeclLoader.findOrCreateVertexAttributeDecl( atRecord, repository );
        }

    }

    /** The configuration for SQL queries. */
    private final Configuration config;

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for boolean attribute type records.
     */
    private static class EdgeAttributeDeclRecord {

        EdgeAttributeDeclRecord( ResultSet resultSet ) throws SQLException {

            this.id = UUID.fromString( resultSet.getString( "ID" ) );
            this.parentEdgeTypeId = UUID.fromString( resultSet.getString( "PARENT_EDGE_TYPE_ID" ) );
            this.name = resultSet.getString( "NAME" );
            this.attributeTypeId = UUID.fromString( resultSet.getString( "ATTRIBUTE_TYPE_ID" ) );
            this.optionality = resultSet.getBoolean( "IS_REQUIRED" ) ? EAttributeOptionality.REQUIRED : EAttributeOptionality.OPTIONAL;
        }

        public final UUID id;

        public final String name;

        public final UUID parentEdgeTypeId;

        private final UUID attributeTypeId;

        private final EAttributeOptionality optionality;

    }

    /**
     * Data structure for UUID attribute type records.
     */
    private static class VertexAttributeDeclRecord {

        VertexAttributeDeclRecord( ResultSet resultSet ) throws SQLException {

            this.id = UUID.fromString( resultSet.getString( "ID" ) );
            this.parentVertexTypeId = UUID.fromString( resultSet.getString( "PARENT_VERTEX_TYPE_ID" ) );
            this.name = resultSet.getString( "NAME" );
            this.attributeTypeId = UUID.fromString( resultSet.getString( "ATTRIBUTE_TYPE_ID" ) );
            this.optionality = resultSet.getBoolean( "IS_REQUIRED" ) ? EAttributeOptionality.REQUIRED : EAttributeOptionality.OPTIONAL;
        }

        public final UUID id;

        public final String name;

        public final UUID parentVertexTypeId;

        private final UUID attributeTypeId;

        private final EAttributeOptionality optionality;

    }

}
