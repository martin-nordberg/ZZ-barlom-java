//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities.api;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Interface to a record-based result set with methods to read fields from the current record by column name.
 */
public interface IResultSet {

    /**
     * Reads a required boolean column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value is unexpectedly null or an error occurs reading the given column.
     */
    @SuppressWarnings( "BooleanMethodNameMustStartWithQuestion" )
    boolean getBoolean( String columnName );

    /**
     * Reads a required date/time (timestamp) column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value is unexpectedly null or an error occurs reading the given column.
     */
    Instant getDateTime( String columnName );

    /**
     * Reads a required double column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value is unexpectedly null or an error occurs reading the given column.
     */
    double getDouble( String columnName );

    /**
     * Reads a required integer column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value is unexpectedly null or an error occurs reading the given column.
     */
    int getInt( String columnName );

    /**
     * Reads an optional boolean column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value an error occurs reading the given column.
     */
    Optional<Boolean> getOptionalBoolean( String columnName );

    /**
     * Reads an optional date/time column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value an error occurs reading the given column.
     */
    Optional<Instant> getOptionalDateTime( String columnName );

    /**
     * Reads an optional double column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value an error occurs reading the given column.
     */
    OptionalDouble getOptionalDouble( String columnName );

    /**
     * Reads an optional integer column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value an error occurs reading the given column.
     */
    OptionalInt getOptionalInt( String columnName );

    /**
     * Reads an optional string column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value an error occurs reading the given column.
     */
    Optional<String> getOptionalString( String columnName );

    /**
     * Reads an optional UUID column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value an error occurs reading the given column.
     */
    Optional<UUID> getOptionalUuid( String columnName );

    /**
     * Reads a required string column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value is unexpectedly null or an error occurs reading the given column.
     */
    String getString( String columnName );

    /**
     * Reads a required UUID column.
     *
     * @param columnName the name of the column to read.
     *
     * @return the value read
     *
     * @throws DatabaseException if the value is unexpectedly null or an error occurs reading the given column.
     */
    UUID getUuid( String columnName );

}
