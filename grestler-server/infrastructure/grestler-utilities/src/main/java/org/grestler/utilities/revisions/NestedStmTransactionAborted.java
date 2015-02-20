//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

/**
 * Exception thrown when a nested transaction is aborted.
 */
public class NestedStmTransactionAborted
    extends RuntimeException {

    /**
     * Constructs a new exception.
     */
    public NestedStmTransactionAborted() {
        super( "Nested transaction aborted. (Partial recovery not supported.)" );
    }

}
