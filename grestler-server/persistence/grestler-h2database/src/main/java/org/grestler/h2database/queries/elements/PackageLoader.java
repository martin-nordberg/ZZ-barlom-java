//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.dbutilities.IDataSource;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IPackageLoader;

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

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry().registerInstantiator( PackageRecord.class, new PackageInstantiator() );

        // Perform the raw query.
        List<PackageRecord> records = database.findAll(
            PackageRecord.class, "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME FROM GRESTLER_PACKAGE"
        );

        // Copy the results into the repository.
        for ( PackageRecord record : records ) {
            this.findOrCreatePackage( record, records, repository );
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
     * Custom instantiator for packages.
     */
    private static class PackageInstantiator
        implements Instantiator<PackageRecord> {

        /**
         * Instantiates a vertexType either by finding it in the registry or else creating it and adding it to the
         * registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new vertexType.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public PackageRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new PackageRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 )
            );

        }

    }

    /**
     * Data structure for package records.
     */
    private static class PackageRecord {

        PackageRecord( UUID id, UUID parentPackageId, String name ) {
            this.id = id;
            this.parentPackageId = parentPackageId;
            this.name = name;
        }

        public final UUID id;

        public final String name;

        public final UUID parentPackageId;

    }

}
