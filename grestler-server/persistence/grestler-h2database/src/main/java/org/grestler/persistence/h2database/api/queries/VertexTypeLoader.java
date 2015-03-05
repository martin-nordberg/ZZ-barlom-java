//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.domain.metamodel.spi.queries.IVertexTypeLoader;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.dbutilities.api.IResultSet;
import org.grestler.persistence.h2database.H2DatabaseModule;

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
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> vtRecords.add( new VertexTypeRecord( rs ) ), config.readString( "VertexType.All" )
            );
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
        Optional<IVertexType> result = repository.findOptionalVertexTypeById( record.id );

        // If already registered, use the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.superTypeId ) ) {
            return repository.loadRootVertexType( record.id, parentPackage );
        }

        // Find an existing vertex super type by UUID.
        Optional<IVertexType> superType = repository.findOptionalVertexTypeById( record.superTypeId );

        // If supertype not already registered, ...
        if ( !superType.isPresent() ) {
            // ... recursively register the supertype.
            for ( VertexTypeRecord srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateVertexType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadVertexType( record.id, parentPackage, record.name, superType.get(), record.abstractness );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for vertex type records.
     */
    private static final class VertexTypeRecord {

        private VertexTypeRecord( IResultSet resultSet ) {
            this.id = resultSet.getUuid( "ID" );
            this.parentPackageId = resultSet.getUuid( "PARENT_PACKAGE_ID" );
            this.name = resultSet.getString( "NAME" );
            this.superTypeId = resultSet.getUuid( "SUPER_TYPE_ID" );
            this.abstractness = EAbstractness.fromBoolean( resultSet.getBoolean( "IS_ABSTRACT" ) );
        }

        public final EAbstractness abstractness;

        public final UUID id;

        public final String name;

        public final UUID parentPackageId;

        public final UUID superTypeId;

    }

}
