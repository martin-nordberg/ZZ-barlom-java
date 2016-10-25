//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.persistence.dbutilities.api.DatabaseException;
import org.barlom.persistence.dbutilities.impl.AbstractResultSet;
import org.barlom.persistence.h2database.api.exceptions.H2DatabaseException;

import java.sql.ResultSet;

/**
 * Result set wrapper for H2 database.
 */
public class H2ResultSet
    extends AbstractResultSet {

    /**
     * Constructs a new H2 result set wrapping the given JDBC result set.
     *
     * @param resultSet the JDBC result set to encapsulate.
     */
    public H2ResultSet( ResultSet resultSet ) {
        super( resultSet );
    }

    @Override
    protected void throwException( String message, Throwable cause ) throws DatabaseException {
        throw new H2DatabaseException( H2ResultSet.LOG, message, cause );
    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

}
