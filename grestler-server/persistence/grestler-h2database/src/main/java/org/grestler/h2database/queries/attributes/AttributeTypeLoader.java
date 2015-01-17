//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.queries.attributes;

import fi.evident.dalesbred.Database;
import fi.evident.dalesbred.instantiation.Instantiator;
import fi.evident.dalesbred.instantiation.InstantiatorArguments;
import org.grestler.dbutilities.IDataSource;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
            record.id,
            parentPackage.get(),
            record.name,
            Optional.ofNullable( record.minValue ),
            Optional.ofNullable( record.maxValue )
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
            record.id,
            parentPackage.get(),
            record.name,
            record.minValue == null ? OptionalDouble.empty() : OptionalDouble.of( record.minValue ),
            record.maxValue == null ? OptionalDouble.empty() : OptionalDouble.of( record.maxValue )
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
            record.id,
            parentPackage.get(),
            record.name,
            record.minValue == null ? OptionalInt.empty() : OptionalInt.of( record.minValue ),
            record.maxValue == null ? OptionalInt.empty() : OptionalInt.of( record.maxValue )
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
            record.id,
            parentPackage.get(),
            record.name,
            record.maxLength,
            record.regexPattern == null ? Optional.empty() : Optional.of( record.regexPattern )
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

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( BooleanAttributeTypeRecord.class, new BooleanAttributeTypeInstantiator() );

        // Perform the raw query.
        List<BooleanAttributeTypeRecord> records = database.findAll(
            BooleanAttributeTypeRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME FROM GRESTLER_VIEW_BOOLEAN_ATTRIBUTE_TYPE"
        );

        // Copy the results into the repository.
        for ( BooleanAttributeTypeRecord record : records ) {
            AttributeTypeLoader.findOrCreateBooleanAttributeType( record, repository );
        }

    }

    /**
     * Loads all the date/time attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllDateTimeAttributeTypes( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( DateTimeAttributeTypeRecord.class, new DateTimeAttributeTypeInstantiator() );

        // Perform the raw query.
        List<DateTimeAttributeTypeRecord> records = database.findAll(
            DateTimeAttributeTypeRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME, MIN_VALUE, MAX_VALUE FROM GRESTLER_VIEW_DATETIME_ATTRIBUTE_TYPE"
        );

        // Copy the results into the repository.
        for ( DateTimeAttributeTypeRecord record : records ) {
            AttributeTypeLoader.findOrCreateDateTimeAttributeType( record, repository );
        }

    }

    /**
     * Loads all the 64-bit floating point attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllFloat64AttributeTypes( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( Float64AttributeTypeRecord.class, new Float64AttributeTypeInstantiator() );

        // Perform the raw query.
        List<Float64AttributeTypeRecord> records = database.findAll(
            Float64AttributeTypeRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME, MIN_VALUE, MAX_VALUE FROM GRESTLER_VIEW_FLOAT64_ATTRIBUTE_TYPE"
        );

        // Copy the results into the repository.
        for ( Float64AttributeTypeRecord record : records ) {
            AttributeTypeLoader.findOrCreateFloat64AttributeType( record, repository );
        }

    }

    /**
     * Loads all the 32-bit integer attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllInteger32AttributeTypes( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( Integer32AttributeTypeRecord.class, new Integer32AttributeTypeInstantiator() );

        // Perform the raw query.
        List<Integer32AttributeTypeRecord> records = database.findAll(
            Integer32AttributeTypeRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME, MIN_VALUE, MAX_VALUE FROM GRESTLER_VIEW_INTEGER32_ATTRIBUTE_TYPE"
        );

        // Copy the results into the repository.
        for ( Integer32AttributeTypeRecord record : records ) {
            AttributeTypeLoader.findOrCreateInteger32AttributeType( record, repository );
        }

    }

    /**
     * Loads all the string attribute types from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllStringAttributeTypes( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( StringAttributeTypeRecord.class, new StringAttributeTypeInstantiator() );

        // Perform the raw query.
        List<StringAttributeTypeRecord> records = database.findAll(
            StringAttributeTypeRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME, MAX_LENGTH, REGEX_PATTERN FROM GRESTLER_VIEW_STRING_ATTRIBUTE_TYPE"
        );

        // Copy the results into the repository.
        for ( StringAttributeTypeRecord record : records ) {
            AttributeTypeLoader.findOrCreateStringAttributeType( record, repository );
        }

    }

    /**
     * Loads all the UUID attributes from the database.
     *
     * @param repository the repository to load into.
     */
    private void loadAllUuidAttributeTypes( IMetamodelRepositorySpi repository ) {

        // Set up the database wrapper.
        Database database = Database.forDataSource( this.dataSource );
        database.getInstantiatorRegistry()
                .registerInstantiator( UuidAttributeTypeRecord.class, new UuidAttributeTypeInstantiator() );

        // Perform the raw query.
        List<UuidAttributeTypeRecord> records = database.findAll(
            UuidAttributeTypeRecord.class,
            "SELECT TO_CHAR(ID), TO_CHAR(PARENT_PACKAGE_ID), NAME FROM GRESTLER_VIEW_UUID_ATTRIBUTE_TYPE"
        );

        // Copy the results into the repository.
        for ( UuidAttributeTypeRecord record : records ) {
            AttributeTypeLoader.findOrCreateUuidAttributeType( record, repository );
        }

    }

    /** The data source for queries. */
    private final IDataSource dataSource;

    /**
     * Data structure for boolean attribute type records.
     */
    private static class AttributeTypeRecord {

        AttributeTypeRecord(
            UUID id, UUID parentPackageId, String name
        ) {
            this.id = id;
            this.parentPackageId = parentPackageId;
            this.name = name;
        }

        public final UUID id;

        public final String name;

        public final UUID parentPackageId;

    }

    /**
     * Custom instantiator for UUID attribute types.
     */
    private static class BooleanAttributeTypeInstantiator
        implements Instantiator<BooleanAttributeTypeRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public BooleanAttributeTypeRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new BooleanAttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 )
            );

        }

    }

    /**
     * Data structure for UUID attribute type records.
     */
    private static class BooleanAttributeTypeRecord
        extends AttributeTypeRecord {

        BooleanAttributeTypeRecord(
            UUID id, UUID parentPackageId, String name
        ) {
            super( id, parentPackageId, name );
        }

    }

    /**
     * Custom instantiator for date/time attribute types.
     */
    private static class DateTimeAttributeTypeInstantiator
        implements Instantiator<DateTimeAttributeTypeRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public DateTimeAttributeTypeRecord instantiate( InstantiatorArguments fields ) {

            LocalDateTime minValue = null;
            if ( fields.getValues().get( 3 ) != null ) {
                minValue = ( (Timestamp) fields.getValues().get( 3 ) ).toLocalDateTime();
            }
            LocalDateTime maxValue = null;
            if ( fields.getValues().get( 4 ) != null ) {
                maxValue = ( (Timestamp) fields.getValues().get( 4 ) ).toLocalDateTime();
            }

            // Get the attributes from the database result.
            return new DateTimeAttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                minValue,
                maxValue
            );

        }

    }

    /**
     * Data structure for date/time attribute type records.
     */
    private static class DateTimeAttributeTypeRecord
        extends AttributeTypeRecord {

        DateTimeAttributeTypeRecord(
            UUID id, UUID parentPackageId, String name, LocalDateTime minValue, LocalDateTime maxValue
        ) {
            super( id, parentPackageId, name );
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public final LocalDateTime maxValue;

        public final LocalDateTime minValue;
    }

    /**
     * Custom instantiator for 64-bit floating point attribute types.
     */
    private static class Float64AttributeTypeInstantiator
        implements Instantiator<Float64AttributeTypeRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public Float64AttributeTypeRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new Float64AttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                (Double) fields.getValues().get( 3 ),
                (Double) fields.getValues().get( 4 )
            );

        }

    }

    /**
     * Data structure for 64-bit floating point attribute type records.
     */
    private static class Float64AttributeTypeRecord
        extends AttributeTypeRecord {

        Float64AttributeTypeRecord(
            UUID id, UUID parentPackageId, String name, Double minValue, Double maxValue
        ) {
            super( id, parentPackageId, name );
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public final Double maxValue;

        public final Double minValue;
    }

    /**
     * Custom instantiator for 32-bit integer attribute types.
     */
    private static class Integer32AttributeTypeInstantiator
        implements Instantiator<Integer32AttributeTypeRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public Integer32AttributeTypeRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new Integer32AttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                (Integer) fields.getValues().get( 3 ),
                (Integer) fields.getValues().get( 4 )
            );

        }

    }

    /**
     * Data structure for 32-bit integer attribute type records.
     */
    private static class Integer32AttributeTypeRecord
        extends AttributeTypeRecord {

        Integer32AttributeTypeRecord(
            UUID id, UUID parentPackageId, String name, Integer minValue, Integer maxValue
        ) {
            super( id, parentPackageId, name );
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public final Integer maxValue;

        public final Integer minValue;
    }

    /**
     * Custom instantiator for string attribute types.
     */
    private static class StringAttributeTypeInstantiator
        implements Instantiator<StringAttributeTypeRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public StringAttributeTypeRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new StringAttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                (Integer) fields.getValues().get( 3 ),
                (String) fields.getValues().get( 4 )
            );

        }

    }

    /**
     * Data structure for 32-bit integer attribute type records.
     */
    private static class StringAttributeTypeRecord
        extends AttributeTypeRecord {

        StringAttributeTypeRecord(
            UUID id, UUID parentPackageId, String name, Integer maxLength, String regexPattern
        ) {
            super( id, parentPackageId, name );
            this.maxLength = maxLength;
            this.regexPattern = regexPattern;
        }

        public final Integer maxLength;

        public final String regexPattern;
    }

    /**
     * Custom instantiator for boolean attribute types.
     */
    private static class UuidAttributeTypeInstantiator
        implements Instantiator<UuidAttributeTypeRecord> {

        /**
         * Instantiates a boolean attribute type either by finding it in the registry or else creating it and adding it
         * to the registry.
         *
         * @param fields the fields from the database query.
         *
         * @return the new attribute type.
         */
        @SuppressWarnings( "NullableProblems" )
        @Override
        public UuidAttributeTypeRecord instantiate( InstantiatorArguments fields ) {

            // Get the attributes from the database result.
            return new UuidAttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 )
            );

        }

    }

    /**
     * Data structure for boolean attribute type records.
     */
    private static class UuidAttributeTypeRecord
        extends AttributeTypeRecord {

        UuidAttributeTypeRecord(
            UUID id, UUID parentPackageId, String name
        ) {
            super( id, parentPackageId, name );
        }

    }

}
