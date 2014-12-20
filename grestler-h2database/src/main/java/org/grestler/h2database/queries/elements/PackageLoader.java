//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.elements;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.h2database.datasource.H2DataSource;
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
    public PackageLoader( H2DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadAllPackages( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry().registerInstantiator( PackageData.class, new PackageInstantiator() );

        // Perform the raw query.
        List<PackageData> records = database.findAll(
            PackageData.class, "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME FROM GRESTLER_PACKAGE"
        );

        // Copy the results into the repository.
        for ( PackageData record : records ) {
            this.findOrCreatePackage( record, records, repository );
        }

    }

    /**
     * Finds a package in the metamodel repository or creates it if not yet there.
     *
     * @param record  the attributes of the package.
     * @param records the attributes of all packages.
     *
     * @return the found or newly created package.
     */
    private IPackage findOrCreatePackage(
        PackageData record, List<PackageData> records, IMetamodelRepositorySpi repository
    ) {

        Optional<IPackage> result = repository.findPackageById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // If top of inheritance hierarchy, create w/o super type.
        if ( record.id.equals( record.parentPackageId ) ) {
            return IPackage.ROOT_PACKAGE;
        }

        // Find an existing parent package by UUID.
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        // If parent package not already registered, ...
        if ( !parentPackage.isPresent() ) {
            // ... recursively register the parent package.
            for ( PackageData srecord : records ) {
                if ( srecord.id.equals( record.parentPackageId ) ) {
                    parentPackage = Optional.of( this.findOrCreatePackage( srecord, records, repository ) );
                }
            }
        }

        return repository.loadPackage( record.id, parentPackage.get(), record.name );

    }

    /** The data source for queries. */
    private final H2DataSource dataSource;

    /**
     * Data structure for package records.
     */
    private static class PackageData {

        PackageData( UUID id, UUID parentPackageId, String name ) {
            this.id = id;
            this.parentPackageId = parentPackageId;
            this.name = name;
        }

        final UUID id;

        final String name;

        final UUID parentPackageId;

    }

    /**
     * Custom instantiator for packages.
     */
    private static class PackageInstantiator
        implements Instantiator<PackageData> {

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
        public PackageData instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new PackageData(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 )
            );

        }

    }

}
