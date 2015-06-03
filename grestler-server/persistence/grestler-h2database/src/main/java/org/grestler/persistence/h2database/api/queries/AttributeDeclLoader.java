//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries;

import org.grestler.domain.metamodel.api.elements.EAttributeOptionality;
import org.grestler.domain.metamodel.api.elements.ELabelDefaulting;
import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.spi.queries.IAttributeDeclLoader;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
        IEdgeAttributeDecl.Record record, IMetamodelRepositorySpi repository
    ) {

        // Find the edge type and the attribute type
        Optional<IEdgeType> parentEdgeType = repository.findOptionalEdgeTypeById( record.parentEdgeTypeId );
        Optional<IAttributeType> attributeType = repository.findOptionalAttributeTypeById( record.typeId );

        return repository.loadEdgeAttributeDecl( record, parentEdgeType.get(), attributeType.get() );

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
        IVertexAttributeDecl.Record record, IMetamodelRepositorySpi repository
    ) {

        // Find the parent vertex type and the attribute type
        Optional<IVertexType> parentVertexType = repository.findOptionalVertexTypeById( record.parentVertexTypeId );
        Optional<IAttributeType> attributeType = repository.findOptionalAttributeTypeById( record.typeId );

        return repository.loadVertexAttributeDecl( record, parentVertexType.get(), attributeType.get() );

    }

    /**
     * Loads all the UUID attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllEdgeAttributeDecls( IMetamodelRepositorySpi repository ) {

        Collection<IEdgeAttributeDecl.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IEdgeAttributeDecl.Record(
                        rs.getUuid( "ID" ),
                        rs.getString( "NAME" ),
                        EAttributeOptionality.fromBoolean( rs.getBoolean( "IS_REQUIRED" ) ),
                        rs.getUuid( "PARENT_EDGE_TYPE_ID" ),
                        rs.getUuid( "ATTRIBUTE_TYPE_ID" )
                    )
                ), this.config.readString( "EdgeAttributeDecl.All" )
            );
        }

        // Copy the results into the repository.
        for ( IEdgeAttributeDecl.Record atRecord : atRecords ) {
            AttributeDeclLoader.findOrCreateEdgeAttributeDecl( atRecord, repository );
        }

    }

    /**
     * Loads all the boolean attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllVertexAttributeDecls( IMetamodelRepositorySpi repository ) {

        Collection<IVertexAttributeDecl.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IVertexAttributeDecl.Record(
                        rs.getUuid( "ID" ),
                        rs.getString( "NAME" ),
                        ELabelDefaulting.fromBoolean( rs.getBoolean( "IS_DEFAULT_LABEL" ) ),
                        EAttributeOptionality.fromBoolean( rs.getBoolean( "IS_REQUIRED" ) ),
                        rs.getUuid( "PARENT_VERTEX_TYPE_ID" ),
                        rs.getUuid( "ATTRIBUTE_TYPE_ID" )
                    )
                ), this.config.readString( "VertexAttributeDecl.All" )
            );
        }

        // Copy the results into the repository.
        for ( IVertexAttributeDecl.Record atRecord : atRecords ) {
            AttributeDeclLoader.findOrCreateVertexAttributeDecl( atRecord, repository );
        }

    }

    /** The configuration for SQL queries. */
    private final Configuration config;

    /** The data source for queries. */
    private final IDataSource dataSource;

}
