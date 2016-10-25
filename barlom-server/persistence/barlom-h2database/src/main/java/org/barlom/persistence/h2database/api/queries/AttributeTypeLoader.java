//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.queries;

import org.barlom.domain.metamodel.api.elements.IAttributeType;
import org.barlom.domain.metamodel.api.elements.IBooleanAttributeType;
import org.barlom.domain.metamodel.api.elements.IDateTimeAttributeType;
import org.barlom.domain.metamodel.api.elements.IFloat64AttributeType;
import org.barlom.domain.metamodel.api.elements.IInteger32AttributeType;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IStringAttributeType;
import org.barlom.domain.metamodel.api.elements.IUuidAttributeType;
import org.barlom.domain.metamodel.spi.queries.IAttributeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.barlom.infrastructure.utilities.configuration.Configuration;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.h2database.H2DatabaseModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Service for loading all attribute types into a metamodel repository.
 */
public class AttributeTypeLoader
    implements IAttributeTypeLoader {

    /**
     * Constructs a new DAO for attribute types.
     *
     * @param dataSource the data source for the H2 database to read from.
     */
    public AttributeTypeLoader( IDataSource dataSource ) {

        this.dataSource = dataSource;

        this.config = new Configuration( H2DatabaseModule.class );

    }

    @Override
    public void loadAllAttributeTypes( IMetamodelRepositorySpi repository ) {

        this.loadAllBooleanAttributeTypes( repository );
        this.loadAllDateTimeAttributeTypes( repository );
        this.loadAllFloat64AttributeTypes( repository );
        this.loadAllInteger32AttributeTypes( repository );
        this.loadAllStringAttributeTypes( repository );
        this.loadAllUuidAttributeTypes( repository );

    }

    /**
     * Finds a boolean attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IAttributeType findOrCreateBooleanAttributeType(
        IBooleanAttributeType.Record record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadBooleanAttributeType( record, parentPackage );

    }

    /**
     * Finds a date/time attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IAttributeType findOrCreateDateTimeAttributeType(
        IDateTimeAttributeType.Record record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadDateTimeAttributeType( record, parentPackage );

    }

    /**
     * Finds a 64-bit floating point attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IAttributeType findOrCreateFloat64AttributeType(
        IFloat64AttributeType.Record record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadFloat64AttributeType( record, parentPackage );

    }

    /**
     * Finds a 32-bit integer attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IAttributeType findOrCreateInteger32AttributeType(
        IInteger32AttributeType.Record record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadInteger32AttributeType( record, parentPackage );

    }

    /**
     * Finds a 32-bit integer attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IAttributeType findOrCreateStringAttributeType(
        IStringAttributeType.Record record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadStringAttributeType( record, parentPackage );

    }

    /**
     * Finds a UUID attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private static IAttributeType findOrCreateUuidAttributeType(
        IUuidAttributeType.Record record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadUuidAttributeType( record, parentPackage );

    }

    /**
     * Loads all the boolean attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllBooleanAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<IBooleanAttributeType.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IBooleanAttributeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getString( "NAME" ),
                        rs.getOptionalBoolean( "DEFAULT_VALUE" )
                    )
                ), this.config.readString( "BooleanAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IBooleanAttributeType.Record atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateBooleanAttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the date/time attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllDateTimeAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<IDateTimeAttributeType.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IDateTimeAttributeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getString( "NAME" ),
                        rs.getOptionalDateTime( "MIN_VALUE" ),
                        rs.getOptionalDateTime( "MAX_VALUE" )
                    )
                ), this.config.readString( "DateTimeAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IDateTimeAttributeType.Record atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateDateTimeAttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the 64-bit floating point attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllFloat64AttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<IFloat64AttributeType.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IFloat64AttributeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getString( "NAME" ),
                        rs.getOptionalDouble( "DEFAULT_VALUE" ),
                        rs.getOptionalDouble( "MIN_VALUE" ),
                        rs.getOptionalDouble( "MAX_VALUE" )
                    )
                ), this.config.readString( "Float64AttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IFloat64AttributeType.Record atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateFloat64AttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the 32-bit integer attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllInteger32AttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<IInteger32AttributeType.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IInteger32AttributeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getString( "NAME" ),
                        rs.getOptionalInt( "DEFAULT_VALUE" ),
                        rs.getOptionalInt( "MIN_VALUE" ),
                        rs.getOptionalInt( "MAX_VALUE" )
                    )
                ), this.config.readString( "Integer32AttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IInteger32AttributeType.Record atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateInteger32AttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the string attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllStringAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<IStringAttributeType.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IStringAttributeType.Record(
                        rs.getUuid( "ID" ),
                        rs.getUuid( "PARENT_PACKAGE_ID" ),
                        rs.getString( "NAME" ),
                        rs.getInt( "MAX_LENGTH" ),
                        rs.getOptionalInt( "MIN_LENGTH" ),
                        rs.getOptionalString( "REGEX_PATTERN" )
                    )
                ), this.config.readString( "StringAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IStringAttributeType.Record atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateStringAttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the UUID attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllUuidAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<IUuidAttributeType.Record> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add(
                    new IUuidAttributeType.Record(
                        rs.getUuid( "ID" ), rs.getUuid( "PARENT_PACKAGE_ID" ), rs.getString( "NAME" )
                    )
                ), this.config.readString( "UuidAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( IUuidAttributeType.Record atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateUuidAttributeType( atRecord, repository );
        }

    }

    /** The configuration for SQL queries. */
    private final Configuration config;

    /** The data source for queries. */
    private final IDataSource dataSource;

}
