//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.persistence.dbutilities.api.DatabaseException;
import org.barlom.persistence.dbutilities.impl.AbstractConnection;
import org.barlom.persistence.dbutilities.spi.IResultSetSpi;
import org.barlom.persistence.h2database.api.exceptions.H2DatabaseException;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Concrete connection implementation for the H2 database.
 */
public class H2Connection
    extends AbstractConnection {

    /**
     * Constructs a new H2 connection wrapping the given JDBC connection.
     *
     * @param connection the JDBC connection to wrap.
     */
    public H2Connection( Connection connection ) {
        super( connection );
    }

    @Override
    protected IResultSetSpi makeResultSet( ResultSet resultSet ) {
        return new H2ResultSet( resultSet );
    }

    @Override
    protected int throwException( String message ) throws DatabaseException {
        throw new H2DatabaseException( H2Connection.LOG, message );
    }

    @Override
    protected int throwException( String message, Throwable cause ) throws DatabaseException {
        throw new H2DatabaseException( H2Connection.LOG, message, cause );
    }

    /** The logger for this class. */
    @SuppressWarnings( "FieldNameHidesFieldInSuperclass" )
    private static final Logger LOG = LogManager.getLogger();

}
