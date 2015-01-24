//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities.api;

import org.apache.logging.log4j.Logger;
import org.grestler.utilities.exceptions.LoggedException;

/**
 * Abstract base exception for database errors.
 */
public class DatabaseException
    extends LoggedException {

    protected DatabaseException( Logger log, String message ) {
        super( log, message );
    }

    protected DatabaseException( Logger log, String message, Throwable cause ) {
        super( log, message, cause );
    }

}
