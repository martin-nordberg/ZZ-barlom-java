//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.queries;

import org.barlom.domain.metamodel.api.elements.EAbstractness;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.barlom.domain.metamodel.spi.queries.IVertexTypeLoader;
import org.barlom.infrastructure.utilities.configuration.Configuration;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.h2database.H2DatabaseModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<IVertexType.Record> vtRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> vtRecords.add(
                    new IVertexType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getString( "NAME" ),
                        EAbstractness.fromBoolean( rs.getBoolean( "IS_ABSTRACT" ) ),
                        rs.getUuid( "SUPER_TYPE_ID" )
                    )
                ), config.readString( "VertexType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IVertexType.Record vtRecord : vtRecords ) {
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
        IVertexType.Record record, List<IVertexType.Record> records, IMetamodelRepositorySpi repository
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
            for ( IVertexType.Record srecord : records ) {
                if ( srecord.id.equals( record.superTypeId ) ) {
                    superType = Optional.of( this.findOrCreateVertexType( srecord, records, repository ) );
                }
            }
        }

        return repository.loadVertexType( record, parentPackage, superType.get() );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

}
