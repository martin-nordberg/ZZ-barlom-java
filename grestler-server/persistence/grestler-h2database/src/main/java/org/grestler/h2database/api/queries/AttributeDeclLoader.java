//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.api.queries;

import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.dbutilities.api.IResultSet;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.EAttributeOptionality;
import org.grestler.metamodel.api.elements.ELabelDefaulting;
import org.grestler.metamodel.api.elements.IAttributeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.queries.IAttributeDeclLoader;
import org.grestler.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.utilities.configuration.Configuration;

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

        // Find the edge type and the attribute type
        Optional<IEdgeType> parentEdgeType = repository.findOptionalEdgeTypeById( record.parentEdgeTypeId );
        Optional<IAttributeType> attributeType = repository.findOptionalAttributeTypeById( record.attributeTypeId );

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
        Optional<IVertexType> parentVertexType = repository.findOptionalVertexTypeById( record.parentVertexTypeId );
        Optional<IAttributeType> attributeType = repository.findOptionalAttributeTypeById( record.attributeTypeId );

        return repository.loadVertexAttributeDecl(
            record.id,
            parentVertexType.get(),
            record.name,
            attributeType.get(),
            record.optionality,
            record.labelDefaulting
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
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new EdgeAttributeDeclRecord( rs ) ),
                this.config.readString( "EdgeAttributeDecl.All" )
            );
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
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new VertexAttributeDeclRecord( rs ) ),
                this.config.readString( "VertexAttributeDecl.All" )
            );
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
    private static final class EdgeAttributeDeclRecord {

        private EdgeAttributeDeclRecord( IResultSet resultSet ) {

            this.id = resultSet.getUuid( "ID" );
            this.parentEdgeTypeId = resultSet.getUuid( "PARENT_EDGE_TYPE_ID" );
            this.name = resultSet.getString( "NAME" );
            this.attributeTypeId = resultSet.getUuid( "ATTRIBUTE_TYPE_ID" );
            this.optionality = EAttributeOptionality.fromBoolean( resultSet.getBoolean( "IS_REQUIRED" ) );
        }

        public final UUID attributeTypeId;

        public final UUID id;

        public final String name;

        public final EAttributeOptionality optionality;

        public final UUID parentEdgeTypeId;

    }

    /**
     * Data structure for UUID attribute type records.
     */
    private static final class VertexAttributeDeclRecord {

        private VertexAttributeDeclRecord( IResultSet resultSet ) {

            this.id = resultSet.getUuid( "ID" );
            this.parentVertexTypeId = resultSet.getUuid( "PARENT_VERTEX_TYPE_ID" );
            this.name = resultSet.getString( "NAME" );
            this.attributeTypeId = resultSet.getUuid( "ATTRIBUTE_TYPE_ID" );
            this.optionality = EAttributeOptionality.fromBoolean( resultSet.getBoolean( "IS_REQUIRED" ) );
            this.labelDefaulting = ELabelDefaulting.fromBoolean( resultSet.getBoolean( "IS_DEFAULT_LABEL" ) );

        }

        public final UUID attributeTypeId;

        public final UUID id;

        public final ELabelDefaulting labelDefaulting;

        public final String name;

        public final EAttributeOptionality optionality;

        public final UUID parentVertexTypeId;

    }

}
