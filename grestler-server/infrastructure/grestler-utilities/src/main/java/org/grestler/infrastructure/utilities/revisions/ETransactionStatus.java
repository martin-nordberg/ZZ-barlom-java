//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.revisions;

/**
 * Status of a transaction.
 */
public enum ETransactionStatus {
    NO_TRANSACTION,
    IN_PROGRESS,
    COMMITTED,
    ABORTED;
}
