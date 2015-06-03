//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries;

import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IPackageDependency;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.domain.metamodel.spi.queries.IPackageDependencyLoader;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.ArrayList;
import java.util.Collection;

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

        Collection<IPackageDependency.Record> pkgRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> pkgRecords.add(
                    new IPackageDependency.Record(
                        rs.getUuid( "ID" ), rs.getUuid( "CLIENT_PACKAGE_ID" ), rs.getUuid( "SUPPLIER_PACKAGE_ID" )
                    )
                ), config.readString( "PackageDependency.All" )
            );
        }

        // Copy the results into the repository.
        for ( IPackageDependency.Record pkgRecord : pkgRecords ) {
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
        IPackageDependency.Record record, IMetamodelRepositorySpi repository
    ) {

        // Find the edge type and the attribute type
        IPackage clientPackage = repository.findPackageById( record.clientPackageId );
        IPackage supplierPackage = repository.findPackageById( record.supplierPackageId );

        return repository.loadPackageDependency( record, clientPackage, supplierPackage );

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

}
