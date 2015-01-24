//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.impl;

import org.grestler.dbutilities.api.DatabaseException;
import org.grestler.dbutilities.impl.AbstractResultSet;
import org.grestler.h2database.H2DatabaseException;

import java.sql.ResultSet;

/**
 * Result set wrapper for H2 database.
 */
public class H2ResultSet
    extends AbstractResultSet {

    public H2ResultSet( ResultSet resultSet ) {
        super( resultSet );
    }

    @Override
    protected void throwException( String message, Throwable cause ) throws DatabaseException {
        throw new H2DatabaseException( message, cause );
    }

}
