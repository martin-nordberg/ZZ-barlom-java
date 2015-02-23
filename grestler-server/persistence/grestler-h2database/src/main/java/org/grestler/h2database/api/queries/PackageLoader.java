//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.api.queries;

import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.dbutilities.api.IResultSet;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.queries.IPackageLoader;
import org.grestler.utilities.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for loading all packages into a metamodel repository.
 */
public class PackageLoader
    implements IPackageLoader {

    /**
     * Constructs a new DAO for packages.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public PackageLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllPackages( IMetamodelRepositorySpi repository ) {

        Configuration config = new Configuration( H2DatabaseModule.class );

        List<PackageRecord> pkgRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> pkgRecords.add( new PackageRecord( rs ) ), config.readString( "Package.All" )
            );
        }

        // Copy the results into the repository.
        for ( PackageRecord pkgRecord : pkgRecords ) {
            this.findOrCreatePackage( pkgRecord, pkgRecords, repository );
        }

    }

    /**
     * Finds a package in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the package.
     * @param records    the attributes of all packages.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created package.
     */
    private IPackage findOrCreatePackage(
        PackageRecord record, List<PackageRecord> records, IMetamodelRepositorySpi repository
    ) {

        // Look for the package already in the repository.
        Optional<IPackage> result = repository.findPackageById( record.id );

        // If already registered, use the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.parentPackageId ) ) {
            return repository.loadRootPackage( record.id );
        }

        // Find an existing parent package by UUID.
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        // If parent package not already registered, ...
        if ( !parentPackage.isPresent() ) {
            // ... recursively register the parent package.
            for ( PackageRecord srecord : records ) {
                if ( srecord.id.equals( record.parentPackageId ) ) {
                    parentPackage = Optional.of( this.findOrCreatePackage( srecord, records, repository ) );
                }
            }
        }

        return repository.loadPackage( record.id, parentPackage.get(), record.name );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for package records.
     */
    private static final class PackageRecord {

        private PackageRecord( IResultSet resultSet ) {
            this.id = resultSet.getUuid( "ID" );
            this.parentPackageId = resultSet.getUuid( "PARENT_PACKAGE_ID" );
            this.name = resultSet.getString( "NAME" );
        }

        public final UUID id;

        public final String name;

        public final UUID parentPackageId;

    }

}
