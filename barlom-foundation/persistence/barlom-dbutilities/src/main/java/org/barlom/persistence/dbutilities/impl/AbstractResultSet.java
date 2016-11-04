//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.dbutilities.impl;

import org.barlom.persistence.dbutilities.api.DatabaseException;
import org.barlom.persistence.dbutilities.spi.IResultSetSpi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Abstract implementation of IResultSet.
 */
public abstract class AbstractResultSet
    implements IResultSetSpi {

    /**
     * Constructs a new result set wrapper.
     *
     * @param resultSet the result set to wrap.
     */
    protected AbstractResultSet( ResultSet resultSet ) {
        this.resultSet = resultSet;
    }

    @Override
    public void close() {
        try {
            this.resultSet.close();
        }
        catch ( SQLException e ) {
            this.throwException( "Error closing result set.", e );
        }
    }

    @Override
    @SuppressWarnings( "BooleanMethodNameMustStartWithQuestion" )
    public boolean getBoolean( String columnName ) {
        try {
            boolean result = this.resultSet.getBoolean( columnName );
            if ( this.resultSet.wasNull() ) {
                this.throwException( "Unexpectedly null boolean column " + columnName + "." );
            }
            return result;
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading boolean column " + columnName + ".", e );
            return false;
        }
    }

    @Override
    public Instant getDateTime( String columnName ) {
        try {
            Timestamp result = this.resultSet.getTimestamp( columnName );
            if ( result == null ) {
                this.throwException( "Unexpectedly null date/time column " + columnName + "." );
            }
            else {
                return result.toInstant();
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading boolean column " + columnName + ".", e );
        }
        return Instant.now();
    }

    @Override
    public double getDouble( String columnName ) {
        try {
            double result = this.resultSet.getDouble( columnName );
            if ( this.resultSet.wasNull() ) {
                this.throwException( "Unexpectedly null double column " + columnName + "." );
            }
            return result;
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading double column " + columnName + ".", e );
            return 0.0;
        }
    }

    @Override
    public int getInt( String columnName ) {
        try {
            int result = this.resultSet.getInt( columnName );
            if ( this.resultSet.wasNull() ) {
                this.throwException( "Unexpectedly null integer column " + columnName + "." );
            }
            return result;
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading integer column " + columnName + ".", e );
            return 0;
        }
    }

    @Override
    public Optional<Boolean> getOptionalBoolean( String columnName ) {
        try {
            boolean result = this.resultSet.getBoolean( columnName );
            if ( !this.resultSet.wasNull() ) {
                return Optional.of( result );
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading boolean column " + columnName + ".", e );
        }
        return Optional.empty();
    }

    @Override
    public Optional<Instant> getOptionalDateTime( String columnName ) {
        try {
            Timestamp result = this.resultSet.getTimestamp( columnName );
            if ( result != null ) {
                return Optional.of( result.toInstant() );
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading boolean column " + columnName + ".", e );
        }
        return Optional.empty();
    }

    @Override
    public OptionalDouble getOptionalDouble( String columnName ) {
        try {
            double result = this.resultSet.getDouble( columnName );
            if ( !this.resultSet.wasNull() ) {
                return OptionalDouble.of( result );
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading double column " + columnName + ".", e );
        }
        return OptionalDouble.empty();
    }

    @Override
    public OptionalInt getOptionalInt( String columnName ) {
        try {
            int result = this.resultSet.getInt( columnName );
            if ( !this.resultSet.wasNull() ) {
                return OptionalInt.of( result );
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading integer column " + columnName + ".", e );
        }
        return OptionalInt.empty();
    }

    @Override
    public Optional<String> getOptionalString( String columnName ) {
        try {
            return Optional.ofNullable( this.resultSet.getString( columnName ) );
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading string column " + columnName + ".", e );
            return Optional.empty();
        }
    }

    @Override
    public Optional<UUID> getOptionalUuid( String columnName ) {
        try {
            String result = this.resultSet.getString( columnName );
            if ( result != null ) {
                return Optional.of( UUID.fromString( result ) );
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading UUID column " + columnName + ".", e );
        }
        return Optional.empty();
    }

    @Override
    public String getString( String columnName ) {
        try {
            String result = this.resultSet.getString( columnName );
            if ( result == null ) {
                this.throwException( "Unexpectedly null string column " + columnName + "." );
            }
            return result;
        }
        catch ( SQLException e ) {
            // TODO: Move inside throwException for use elsewhere
            String columns = "Columns [";
            try {
                columns += this.resultSet.getMetaData().getColumnCount();
                columns += "] are: (";

                String delimiter = "";
                for ( int col = 1; col <= this.resultSet.getMetaData().getColumnCount() ; col+=1 ) {
                    columns += delimiter;
                    columns += this.resultSet.getMetaData().getColumnLabel( col );
                    delimiter = ", ";
                }

                columns += ").";
            }
            catch ( SQLException e2 ) {
                //ignore
            }
            this.throwException( "Error reading string column " + columnName + ". " + columns , e );
            return "";
        }
    }

    @Override
    public UUID getUuid( String columnName ) {
        try {
            String result = this.resultSet.getString( columnName );
            if ( result == null ) {
                this.throwException( "Unexpectedly null UUID column " + columnName + "." );
            }
            else {
                return UUID.fromString( result );
            }
        }
        catch ( SQLException e ) {
            this.throwException( "Error reading UUID column " + columnName + ".", e );
        }
        return UUID.randomUUID();
    }

    @Override
    public boolean next() {
        try {
            return this.resultSet.next();
        }
        catch ( SQLException e ) {
            this.throwException( "Error advancing through result set.", e );
            return false;
        }
    }

    /**
     * Throws an exception wrapping the underlying SQLException from JDBC.
     *
     * @param message the message for the exception.
     *
     * @throws DatabaseException always thrown by this method.
     */
    protected void throwException( String message ) throws DatabaseException {
        this.throwException( message, null );
    }

    /**
     * Throws an exception wrapping the underlying SQLException from JDBC.
     *
     * @param message the message for the exception.
     * @param cause   the internal exception causing the problem.
     *
     * @throws DatabaseException always thrown by this method.
     */
    protected abstract void throwException( String message, Throwable cause ) throws DatabaseException;

    /** The encapsulated result set. */
    private final ResultSet resultSet;

}
