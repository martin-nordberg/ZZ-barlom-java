//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for managing in-memory transactions. The code is similar to "versioned boxes", the concept behind JVSTM
 * for software transactional memory. However, this code is much more streamlined, though very experimental.
 */
interface IStmTransaction {

    /**
     * Aborts this transaction; abandons the revisions made by the transaction.
     */
    void abort();

    /**
     * Tracks all versioned items read by this transaction. The transaction will confirm that all these items remain
     * unwritten by some other transaction before this transaction commits.
     *
     * @param versionedItem the item that has been read.
     */
    void addVersionedItemRead( AbstractVersionedItem versionedItem );

    /**
     * Tracks all versioned items written by this transaction. The versions written by this transaction will be cleaned
     * up after the transaction aborts. Any earlier versions will be cleaned up after all transactions using any earlier
     * versions and their source have completed.
     *
     * @param versionedItem the item that has been written.
     */
    void addVersionedItemWritten( AbstractVersionedItem versionedItem );

    /**
     * Commits this transaction.
     *
     * @throws WriteConflictException if some other transaction has concurrently written values read during this
     *                                transaction.
     */
    void commit();

    /**
     * Ensures that the transaction is writeable.
     */
    void ensureWriteable();

    /**
     * @return the outer transaction of a nested transaction or null for the outermost transaction.
     */
    IStmTransaction getEnclosingTransaction();

    /**
     * @return the revision number of information to be read by this transaction.
     */
    long getSourceRevisionNumber();

    /**
     * Determines the status of this transaction from its target revision number.
     * <p>
     * TBD: this seems to have no use
     *
     * @return the transaction status (IN_PROGRESS, COMMITTED, or ABORTED).
     */
    ETransactionStatus getStatus();

    /**
     * @return the revision number of information written by this transaction (negative while transaction is running;
     * positive after committed.
     */
    AtomicLong getTargetRevisionNumber();

    /**
     * Takes note that some read operation has seen a newer version and will certainly fail with a write conflict if
     * this transaction writes anything. Fails immediately if this transaction has already written anything.
     */
    void setNewerRevisionSeen();

}
