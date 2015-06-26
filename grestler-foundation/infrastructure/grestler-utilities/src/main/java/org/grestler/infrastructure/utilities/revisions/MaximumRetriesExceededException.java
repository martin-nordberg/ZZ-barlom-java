//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.revisions;

/**
 * Exception thrown when a transaction fails within its specified number of retries..
 */
public final class MaximumRetriesExceededException
    extends RuntimeException {

    /**
     * Constructs a new exception.
     */
    public MaximumRetriesExceededException() {
        super( "Maximum retries exceeded." );
    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

}
