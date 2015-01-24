//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.exceptions;

import org.apache.logging.log4j.Logger;

/**
 * Exception class that logs its occurrences.
 */
public class LoggedException
    extends RuntimeException {

    protected LoggedException( Logger log, String message ) {
        super( message );
        log.error( message, this );
    }

    protected LoggedException( Logger log, String message, Throwable cause ) {
        super( message, cause );
        log.error( message, this );
    }
}
