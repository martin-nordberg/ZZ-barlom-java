//
// (C) Copyright 2014-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.persistence.dbutilities.api.DatabaseException;
import org.barlom.persistence.dbutilities.impl.AbstractResultSet;
import org.barlom.persistence.postgresql.api.PostgreSqlDatabaseException;

import java.sql.ResultSet;

/**
 * Result set wrapper for PostgreSQL database.
 */
class PostgreSqlResultSet
    extends AbstractResultSet {

    /**
     * Constructs a new PostgreSQL result set wrapping the given JDBC result set.
     *
     * @param resultSet the JDBC result set to encapsulate.
     */
    PostgreSqlResultSet( ResultSet resultSet ) {
        super( resultSet );
    }

    @Override
    protected void throwException( String message, Throwable cause ) throws DatabaseException {
        throw new PostgreSqlDatabaseException( PostgreSqlResultSet.LOG, message, cause );
    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

}
