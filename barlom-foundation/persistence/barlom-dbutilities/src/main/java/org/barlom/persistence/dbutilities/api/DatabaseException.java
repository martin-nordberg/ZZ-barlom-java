//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.dbutilities.api;

import org.apache.logging.log4j.Logger;
import org.barlom.infrastructure.utilities.exceptions.IValidationError;
import org.barlom.infrastructure.utilities.exceptions.LoggedException;

/**
 * Abstract base exception for database errors.
 */
public abstract class DatabaseException
    extends LoggedException
    implements IValidationError {

    /**
     * Constructs a new database-related exception with no inner cause.
     *
     * @param log     the log for logging the exception (usually the log for the concrete exception type).
     * @param message the error that occurred.
     */
    protected DatabaseException( Logger log, String message ) {
        super( log, message );
    }

    /**
     * Constructs a new database-related exception with an inner cause.
     *
     * @param log     the log for logging the exception (usually the log for the concrete exception type).
     * @param message the error that occurred.
     * @param cause   the lower level exception that caused this one.
     */
    protected DatabaseException( Logger log, String message, Throwable cause ) {
        super( log, message, cause );
    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

}
