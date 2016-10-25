//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.exceptions;

import org.apache.logging.log4j.Logger;

/**
 * Exception class that logs its occurrences.
 */
@SuppressWarnings( { "AbstractClassExtendsConcreteClass", "AbstractClassWithoutAbstractMethods" } )
public abstract class LoggedException
    extends RuntimeException {

    /**
     * Constructs a new logged exception.
     *
     * @param log     the log to use.
     * @param message the message of this exception.
     */
    protected LoggedException( Logger log, String message ) {
        super( message );
        log.error( message, this );
    }

    /**
     * Constructs a new logged exception.
     *
     * @param log     the log to use.
     * @param message the message of this exception.
     * @param cause   the lower level cause of this exception.
     */
    protected LoggedException( Logger log, String message, Throwable cause ) {
        super( message, cause );
        log.error( message, this );
    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

}
