//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.dbutilities.DatabaseException;

/**
 * Concrete exception for H2 database errors.
 */
public class H2DatabaseException extends DatabaseException {

    public H2DatabaseException( String message ) {
        super( H2DatabaseException.LOG, message );
    }

    public H2DatabaseException( String message, Throwable cause ) {
        super( H2DatabaseException.LOG, message, cause );
    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

}
