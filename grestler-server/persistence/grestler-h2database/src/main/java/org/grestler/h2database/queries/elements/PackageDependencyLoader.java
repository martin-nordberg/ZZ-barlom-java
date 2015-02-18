//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import org.grestler.dbutilities.api.IConnection;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.dbutilities.api.IResultSet;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;
import org.grestler.metamodel.spi.cmdquery.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IPackageDependencyLoader;
import org.grestler.utilities.configuration.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for loading all package dependencies into a metamodel repository.
 */
public class PackageDependencyLoader
    implements IPackageDependencyLoader {

    /**
     * Constructs a new loader for package dependencies.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public PackageDependencyLoader( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllPackageDependencies( IMetamodelRepositorySpi repository ) {

        Configuration config = new Configuration( H2DatabaseModule.class );

        Collection<PackageDependencyRecord> pkgRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> pkgRecords.add( new PackageDependencyRecord( rs ) ), config.readString( "PackageDependency.All" )
            );
        }

        // Copy the results into the repository.
        for ( PackageDependencyRecord pkgRecord : pkgRecords ) {
            PackageDependencyLoader.findOrCreatePackageDependency( pkgRecord, repository );
        }

    }

    /**
     * Finds a package in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the package.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created package.
     */
    private static IPackageDependency findOrCreatePackageDependency(
        PackageDependencyRecord record, IMetamodelRepositorySpi repository
    ) {

        // Find the edge type and the attribute type
        Optional<IPackage> clientPackage = repository.findPackageById( record.clientPackageId );
        Optional<IPackage> supplierPackage = repository.findPackageById( record.supplierPackageId );

        return repository.loadPackageDependency(
            record.id, clientPackage.get(), supplierPackage.get()
        );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for package dependency records.
     */
    private static final class PackageDependencyRecord {

        private PackageDependencyRecord( IResultSet resultSet ) {
            this.id = resultSet.getUuid( "ID" );
            this.clientPackageId = resultSet.getUuid( "CLIENT_PACKAGE_ID" );
            this.supplierPackageId = resultSet.getUuid( "SUPPLIER_PACKAGE_ID" );
        }

        public final UUID clientPackageId;

        public final UUID id;

        public final UUID supplierPackageId;

    }

}
