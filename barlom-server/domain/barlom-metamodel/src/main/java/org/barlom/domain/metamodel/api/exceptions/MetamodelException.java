//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.exceptions;

import org.apache.logging.log4j.Logger;
import org.barlom.infrastructure.utilities.exceptions.LoggedException;

/**
 * General exception originating from the metamodel subsystem..
 */
public class MetamodelException
    extends LoggedException {

    /**
     * Constructs an exception for the metamodel subsystem itself.
     *
     * @param log     the log to write the exception to.
     * @param message a message describing the error.
     */
    public MetamodelException( Logger log, String message ) {
        super( log, message );
    }

    /**
     * Constructs an exception for the metamodel subsystem that wraps a lower level exception.
     *
     * @param log     the log to write the exception to.
     * @param message a message describing the error.
     * @param cause   the lower level exception that caused this problem.
     */
    public MetamodelException( Logger log, String message, Throwable cause ) {
        super( log, message, cause );
    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

}
