//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.postgresql.api.exceptions;

import org.apache.logging.log4j.Logger;
import org.barlom.infrastructure.utilities.exceptions.EValidationType;
import org.barlom.persistence.dbutilities.api.DatabaseException;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete exception for PostgreSQL database errors.
 */
public class PostgreSqlDatabaseException
    extends DatabaseException {

    /**
     * Constructs a new H2 database exception.
     *
     * @param log     the logger for logging the exception.
     * @param message the message for the error.
     */
    public PostgreSqlDatabaseException( Logger log, String message ) {

        super( log, message );

        this.validationError = new ValidationError( EValidationType.NONSPECIFIC, message );

    }

    /**
     * Constructs a new H2 database exception wrapping a lower level exception.
     *
     * @param log     the logger for logging the exception.
     * @param message the message for the error.
     * @param cause   the lower level exception.
     */
    public PostgreSqlDatabaseException( Logger log, String message, Throwable cause ) {

        super( log, message, cause );

        this.validationError = PostgreSqlDatabaseException.determineValidationError( cause );

    }

    @Override
    public String getValidationMessage() {
        return this.validationError.validationMessage;
    }

    @Override
    public EValidationType getValidationType() {
        return this.validationError.validationType;
    }

    /**
     * Class combining the attributes of a validation error.
     */
    private static final class ValidationError {

        private ValidationError( EValidationType validationType, String validationMessage ) {
            this.validationType = validationType;
            this.validationMessage = validationMessage;
        }

        public final String validationMessage;

        public final EValidationType validationType;

    }

    /**
     * Determines a more useful determination of a validation error by parsing fragments of the SQL exception message.
     *
     * @param cause the SQL exception to look at.
     *
     * @return the validation error message and type.
     */
    private static ValidationError determineValidationError( Throwable cause ) {

        String msg = cause.getMessage();

        EValidationType vtype = EValidationType.NONSPECIFIC;
        StringBuilder vmsg = new StringBuilder();

        if ( msg == null ) {
            vmsg.append( "Unknown error." );
        }
        // TODO: rough parse error messages to determine vtype
        else {
            vmsg.append( msg );
        }

        return new ValidationError( vtype, vmsg.toString() );

    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

    /** The underlying validation error represented by this exception. */
    private final ValidationError validationError;

}
