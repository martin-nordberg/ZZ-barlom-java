//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.queries;

import org.grestler.domain.metamodel.api.elements.IAttributeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.spi.queries.IAttributeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.dbutilities.api.IResultSet;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.UUID;

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
        BooleanAttributeTypeRecord record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadBooleanAttributeType( record.id, parentPackage, record.name, record.defaultValue );

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
        DateTimeAttributeTypeRecord record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadDateTimeAttributeType(
            record.id, parentPackage, record.name, record.minValue, record.maxValue
        );

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
        Float64AttributeTypeRecord record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadFloat64AttributeType(
            record.id, parentPackage, record.name, record.minValue, record.maxValue, record.defaultValue
        );

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
        Integer32AttributeTypeRecord record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadInteger32AttributeType(
            record.id, parentPackage, record.name, record.minValue, record.maxValue, record.defaultValue
        );

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
        StringAttributeTypeRecord record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadStringAttributeType(
            record.id, parentPackage, record.name, record.minLength, record.maxLength, record.regexPattern
        );

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
        UuidAttributeTypeRecord record, IMetamodelRepositorySpi repository
    ) {

        Optional<IAttributeType> result = repository.findOptionalAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        IPackage parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadUuidAttributeType( record.id, parentPackage, record.name );

    }

    /**
     * Loads all the boolean attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllBooleanAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<BooleanAttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new BooleanAttributeTypeRecord( rs ) ),
                this.config.readString( "BooleanAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( BooleanAttributeTypeRecord atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateBooleanAttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the date/time attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllDateTimeAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<DateTimeAttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new DateTimeAttributeTypeRecord( rs ) ),
                this.config.readString( "DateTimeAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( DateTimeAttributeTypeRecord atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateDateTimeAttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the 64-bit floating point attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllFloat64AttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<Float64AttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new Float64AttributeTypeRecord( rs ) ),
                this.config.readString( "Float64AttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( Float64AttributeTypeRecord atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateFloat64AttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the 32-bit integer attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllInteger32AttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<Integer32AttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new Integer32AttributeTypeRecord( rs ) ),
                this.config.readString( "Integer32AttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( Integer32AttributeTypeRecord atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateInteger32AttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the string attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllStringAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<StringAttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new StringAttributeTypeRecord( rs ) ),
                this.config.readString( "StringAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( StringAttributeTypeRecord atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateStringAttributeType( atRecord, repository );
        }

    }

    /**
     * Loads all the UUID attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllUuidAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<UuidAttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try ( IConnection connection = this.dataSource.openConnection() ) {
            connection.executeQuery(
                rs -> atRecords.add( new UuidAttributeTypeRecord( rs ) ),
                this.config.readString( "UuidAttributeType.All" )
            );
        }

        // Copy the results into the repository.
        for ( UuidAttributeTypeRecord atRecord : atRecords ) {
            AttributeTypeLoader.findOrCreateUuidAttributeType( atRecord, repository );
        }

    }

    /** The configuration for SQL queries. */
    private final Configuration config;

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for boolean attribute type records.
     */
    private static class AttributeTypeRecord {

        AttributeTypeRecord( IResultSet resultSet ) {
            this.id = resultSet.getUuid( "ID" );
            this.parentPackageId = resultSet.getUuid( "PARENT_PACKAGE_ID" );
            this.name = resultSet.getString( "NAME" );
        }

        public final UUID id;

        public final String name;

        public final UUID parentPackageId;

    }

    /**
     * Data structure for UUID attribute type records.
     */
    private static class BooleanAttributeTypeRecord
        extends AttributeTypeRecord {

        BooleanAttributeTypeRecord( IResultSet resultSet ) {
            super( resultSet );

            this.defaultValue = resultSet.getOptionalBoolean( "DEFAULT_VALUE" );
        }

        public final Optional<Boolean> defaultValue;

    }

    /**
     * Data structure for date/time attribute type records.
     */
    private static class DateTimeAttributeTypeRecord
        extends AttributeTypeRecord {

        DateTimeAttributeTypeRecord( IResultSet resultSet ) {

            super( resultSet );

            this.minValue = resultSet.getOptionalDateTime( "MIN_VALUE" );
            this.maxValue = resultSet.getOptionalDateTime( "MAX_VALUE" );

        }

        public final Optional<Instant> maxValue;

        public final Optional<Instant> minValue;
    }

    /**
     * Data structure for 64-bit floating point attribute type records.
     */
    private static class Float64AttributeTypeRecord
        extends AttributeTypeRecord {

        Float64AttributeTypeRecord( IResultSet resultSet ) {

            super( resultSet );

            this.minValue = resultSet.getOptionalDouble( "MIN_VALUE" );
            this.maxValue = resultSet.getOptionalDouble( "MAX_VALUE" );
            this.defaultValue = resultSet.getOptionalDouble( "DEFAULT_VALUE" );

        }

        public final OptionalDouble defaultValue;

        public final OptionalDouble maxValue;

        public final OptionalDouble minValue;

    }

    /**
     * Data structure for 32-bit integer attribute type records.
     */
    private static class Integer32AttributeTypeRecord
        extends AttributeTypeRecord {

        Integer32AttributeTypeRecord( IResultSet resultSet ) {

            super( resultSet );

            this.minValue = resultSet.getOptionalInt( "MIN_VALUE" );
            this.maxValue = resultSet.getOptionalInt( "MAX_VALUE" );
            this.defaultValue = resultSet.getOptionalInt( "DEFAULT_VALUE" );

        }

        public final OptionalInt defaultValue;

        public final OptionalInt maxValue;

        public final OptionalInt minValue;

    }

    /**
     * Data structure for 32-bit integer attribute type records.
     */
    private static class StringAttributeTypeRecord
        extends AttributeTypeRecord {

        StringAttributeTypeRecord( IResultSet resultSet ) {

            super( resultSet );

            this.minLength = resultSet.getOptionalInt( "MIN_LENGTH" );
            this.maxLength = resultSet.getInt( "MAX_LENGTH" );
            this.regexPattern = resultSet.getOptionalString( "REGEX_PATTERN" );

        }

        public final int maxLength;

        public final OptionalInt minLength;

        public final Optional<String> regexPattern;
    }

    /**
     * Data structure for boolean attribute type records.
     */
    private static class UuidAttributeTypeRecord
        extends AttributeTypeRecord {

        UuidAttributeTypeRecord( IResultSet resultSet ) {
            super( resultSet );
        }

    }

}
