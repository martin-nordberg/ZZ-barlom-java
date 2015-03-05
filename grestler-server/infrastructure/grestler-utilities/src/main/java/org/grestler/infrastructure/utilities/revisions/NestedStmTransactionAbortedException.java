//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.revisions;

import java.util.Optional;

/**
 * Exception thrown when a nested transaction is aborted.
 */
public class NestedStmTransactionAbortedException
    extends RuntimeException {

    /**
     * Constructs a new exception.
     */
    public NestedStmTransactionAbortedException( Optional<Exception> e ) {
        super( "Nested transaction aborted. (Partial recovery not supported.)", e.orElse( null ) );
    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

}
