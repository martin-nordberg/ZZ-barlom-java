package org.grestler.h2database.impl;

import org.grestler.dbutilities.api.DatabaseException;
import org.grestler.dbutilities.impl.AbstractConnection;
import org.grestler.dbutilities.spi.IResultSetSpi;
import org.grestler.h2database.H2DatabaseException;

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
    protected void throwException( String message, Throwable cause ) throws DatabaseException {
        throw new H2DatabaseException( message, cause );
    }

}
