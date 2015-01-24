//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.attributes;

import org.grestler.dbutilities.IDataSource;
import org.grestler.dbutilities.JdbcConnection;
import org.grestler.h2database.H2DatabaseException;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader;
import org.grestler.utilities.configuration.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

        Optional<IAttributeType> result = repository.findAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadBooleanAttributeType(
            record.id, parentPackage.get(), record.name
        );

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

        Optional<IAttributeType> result = repository.findAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadDateTimeAttributeType(
            record.id, parentPackage.get(), record.name, record.minValue, record.maxValue
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

        Optional<IAttributeType> result = repository.findAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadFloat64AttributeType(
            record.id, parentPackage.get(), record.name, record.minValue, record.maxValue
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

        Optional<IAttributeType> result = repository.findAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadInteger32AttributeType(
            record.id, parentPackage.get(), record.name, record.minValue, record.maxValue
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

        Optional<IAttributeType> result = repository.findAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadStringAttributeType(
            record.id, parentPackage.get(), record.name, record.maxLength, record.regexPattern
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

        Optional<IAttributeType> result = repository.findAttributeTypeById( record.id );

        // If already registered, used the registered value.
        if ( result.isPresent() ) {
            return result.get();
        }

        // Find the parent package
        Optional<IPackage> parentPackage = repository.findPackageById( record.parentPackageId );

        return repository.loadUuidAttributeType(
            record.id, parentPackage.get(), record.name
        );

    }

    /**
     * Loads all the boolean attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllBooleanAttributeTypes( IMetamodelRepositorySpi repository ) {

        Collection<BooleanAttributeTypeRecord> atRecords = new ArrayList<>();

        // Perform the database query, accumulating the records found.
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new BooleanAttributeTypeRecord( rs ) ),
                    this.config.readString( "BooleanAttributeType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Boolean attribute type loading failed.", e );
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
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new DateTimeAttributeTypeRecord( rs ) ),
                    this.config.readString( "DateTimeAttributeType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Date/time attribute type loading failed.", e );
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
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new Float64AttributeTypeRecord( rs ) ),
                    this.config.readString( "Float64AttributeType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Float64 attribute type loading failed.", e );
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
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new Integer32AttributeTypeRecord( rs ) ),
                    this.config.readString( "Integer32AttributeType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Integer32 attribute type loading failed.", e );
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
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new StringAttributeTypeRecord( rs ) ),
                    this.config.readString( "StringAttributeType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Integer32 attribute type loading failed.", e );
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
        try {
            try ( JdbcConnection connection = new JdbcConnection( this.dataSource ) ) {
                connection.executeQuery(
                    rs -> atRecords.add( new UuidAttributeTypeRecord( rs ) ),
                    this.config.readString( "UuidAttributeType.All" )
                );
            }
        }
        catch ( SQLException e ) {
            throw new H2DatabaseException( "Integer32 attribute type loading failed.", e );
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

        AttributeTypeRecord( ResultSet resultSet ) throws SQLException {
            this.id = UUID.fromString( resultSet.getString( "ID" ) );
            this.parentPackageId = UUID.fromString( resultSet.getString( "PARENT_PACKAGE_ID" ) );
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

        BooleanAttributeTypeRecord( ResultSet resultSet ) throws SQLException {
            super( resultSet );
        }

    }

    /**
     * Data structure for date/time attribute type records.
     */
    private static class DateTimeAttributeTypeRecord
        extends AttributeTypeRecord {

        DateTimeAttributeTypeRecord( ResultSet resultSet ) throws SQLException {

            super( resultSet );

            Timestamp minValueTs = resultSet.getTimestamp( "MIN_VALUE" );
            this.minValue = Optional.ofNullable( minValueTs == null ? null : minValueTs.toLocalDateTime() );

            Timestamp maxValueTs = resultSet.getTimestamp( "MAX_VALUE" );
            this.maxValue = Optional.ofNullable( maxValueTs == null ? null : maxValueTs.toLocalDateTime() );

        }

        public final Optional<LocalDateTime> maxValue;

        public final Optional<LocalDateTime> minValue;
    }

    /**
     * Data structure for 64-bit floating point attribute type records.
     */
    private static class Float64AttributeTypeRecord
        extends AttributeTypeRecord {

        Float64AttributeTypeRecord( ResultSet resultSet ) throws SQLException {

            super( resultSet );

            double rsMinValue = resultSet.getDouble( "MIN_VALUE" );
            this.minValue = resultSet.wasNull() ? OptionalDouble.empty() : OptionalDouble.of( rsMinValue );

            double rsMaxValue = resultSet.getDouble( "MAX_VALUE" );
            this.maxValue = resultSet.wasNull() ? OptionalDouble.empty() : OptionalDouble.of( rsMaxValue );

        }

        public final OptionalDouble maxValue;

        public final OptionalDouble minValue;
    }

    /**
     * Data structure for 32-bit integer attribute type records.
     */
    private static class Integer32AttributeTypeRecord
        extends AttributeTypeRecord {

        Integer32AttributeTypeRecord( ResultSet resultSet ) throws SQLException {

            super( resultSet );

            int rsMinValue = resultSet.getInt( "MIN_VALUE" );
            this.minValue = resultSet.wasNull() ? OptionalInt.empty() : OptionalInt.of( rsMinValue );

            int rsMaxValue = resultSet.getInt( "MAX_VALUE" );
            this.maxValue = resultSet.wasNull() ? OptionalInt.empty() : OptionalInt.of( rsMaxValue );

        }


        public final OptionalInt maxValue;

        public final OptionalInt minValue;
    }

    /**
     * Data structure for 32-bit integer attribute type records.
     */
    private static class StringAttributeTypeRecord
        extends AttributeTypeRecord {

        StringAttributeTypeRecord( ResultSet resultSet ) throws SQLException {

            super( resultSet );

            this.maxLength = resultSet.getInt( "MAX_LENGTH" );

            this.regexPattern = Optional.ofNullable( resultSet.getString( "REGEX_PATTERN" ) );

        }

        public final int maxLength;

        public final Optional<String> regexPattern;
    }

    /**
     * Data structure for boolean attribute type records.
     */
    private static class UuidAttributeTypeRecord
        extends AttributeTypeRecord {

        UuidAttributeTypeRecord( ResultSet resultSet ) throws SQLException {
            super( resultSet );
        }

    }

}
