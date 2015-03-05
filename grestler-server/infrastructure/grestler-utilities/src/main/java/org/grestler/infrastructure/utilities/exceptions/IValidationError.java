//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.exceptions;

/**
 * Interface defining a validation problem (associated with an exception).
 */
public interface IValidationError {

    /**
     * @return a message describing the problem.
     */
    String getValidationMessage();

    /**
     * @return the general kind of validation problem.
     */
    EValidationType getValidationType();

}
