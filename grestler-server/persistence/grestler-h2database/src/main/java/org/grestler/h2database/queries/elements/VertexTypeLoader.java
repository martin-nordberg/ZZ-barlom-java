//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import org.grestler.dbutilities.IDataSource;
import org.grestler.dbutilities.JdbcConnection;
import org.grestler.h2database.H2DatabaseException;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;
import org.grestler.utilities.configuration.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for loading all vertex types into a metamodel repository.
 */
public class VertexTypeLoader
    implements IVertexTypeLoader {

    /**
     * Constructs a new DAO for vertex types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public VertexTypeLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllVertexTypes( IMetamodelRepositorySpi repository ) {

        Configuration config = new Configuration( H2DatabaseModule.class );

        List<VertexTypeRecord> vtRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> vtRecords.add( new VertexTypeRecord( rs ) ), config.readString( "VertexType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Vertex type loading failed.", e );
        }

        // Copy the results into the repository.
        for ( VertexTypeRecord vtRecord : vtRecords ) {
            this.findOrCreateVertexType( vtRecord, vtRecords, repository );
        }

    }

    /**
     * Finds a vertex type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the vertex type.
     * @param records    the attributes of all vertex types.
     * @param repository the repository to look in.
     *
     * @return the found or newly created vertex type.
     */
    private IVertexType findOrCreateVertexType(
        VertexTypeRecord record, List<VertexTypeRecord> records, IMetamodelRepositorySpi repository
    ) {

        // Look for the vertex type already in the repository.
        Optional<IVertexType> result = repository.findVertexTypeById( record.id );

        // If already registered, use the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId ).get();

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return repository.loadRootVertexType( record.id, parentPackage );
        }

        // Find an existing vertex super type by UUID.
        Optional<IVertexType> superType = repository.findVertexTypeById( record.superTypeId );

        // If supertype not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the supertype.
            for ( VertexTypeRecord srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateVertexType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadVertexType( record.id, parentPackage, record.name, superType.get() );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for vertex type records.
     */
    private static class VertexTypeRecord {

        VertexTypeRecord( ResultSet resultSet ) throws SQLException {
            this.id = UUID.fromString( resultSet.getString( "ID" ) );
            this.parentPackageId = UUID.fromString( resultSet.getString( "PARENT_PACKAGE_ID" ) );
            this.name = resultSet.getString( "NAME" );
            this.superTypeId = UUID.fromString( resultSet.getString( "SUPER_TYPE_ID" ) );
        }

        public final UUID id;

        public final String name;

        public final UUID parentPackageId;

        public final UUID superTypeId;

    }

}
