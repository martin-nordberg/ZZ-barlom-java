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

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    }

    /**
     * Finds a boolean attribute type in the metamodel repository or creates it if not yet there.
     *
     * @param record     the attributes of the attribute type.
     * @param repository the repository to look in or add to.
     *
     * @return the found or newly created attribute type.
     */
    private IAttributeType findOrCreateBooleanAttributeType(
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
    private IAttributeType findOrCreateDateTimeAttributeType(
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
            this.findOrCreateBooleanAttributeType( record, repository );
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
            this.findOrCreateDateTimeAttributeType( record, repository );
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

        final UUID id;

        final String name;

        final UUID parentPackageId;

    }

    /**
     * Custom instantiator for boolean attribute types.
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
     * Data structure for boolean attribute type records.
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

            // Get the attributes from the database result.
            return new DateTimeAttributeTypeRecord(
                UUID.fromString( (String) fields.getValues().get( 0 ) ),
                UUID.fromString( (String) fields.getValues().get( 1 ) ),
                (String) fields.getValues().get( 2 ),
                (Date) fields.getValues().get( 3 ),
                (Date) fields.getValues().get( 4 )
            );

        }

    }

    /**
     * Data structure for date/time attribute type records.
     */
    private static class DateTimeAttributeTypeRecord
        extends AttributeTypeRecord {

        DateTimeAttributeTypeRecord(
            UUID id, UUID parentPackageId, String name, Date minValue, Date maxValue
        ) {
            super( id, parentPackageId, name );
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        final Date maxValue;

        final Date minValue;
    }

}
