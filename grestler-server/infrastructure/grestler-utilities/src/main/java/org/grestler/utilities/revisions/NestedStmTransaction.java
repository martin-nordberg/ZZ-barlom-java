//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for managing in-memory transactions. The code is similar to "versioned boxes", the concept behind JVSTM
 * for software transactional memory. However, this code is much more streamlined, though very experimental.
 */
final class NestedStmTransaction
    implements IStmTransaction {

    /**
     * Constructs a new transaction.
     *
     * @param writeability         the writeability of the new transaction (must be compatible with enclosing
     *                             transaction).
     * @param enclosingTransaction the outer transaction that encloses this nested one.
     */
    NestedStmTransaction( ETransactionWriteability writeability, IStmTransaction enclosingTransaction ) {

        if ( writeability == ETransactionWriteability.READ_WRITE ) {
            enclosingTransaction.ensureWriteable();
        }

        this.enclosingTransaction = enclosingTransaction;
        this.writeability = writeability;

    }

    @Override
    public void abort( Optional<Exception> e ) {
        throw new NestedStmTransactionAborted( e );
    }

    @Override
    public void addVersionedItemRead( AbstractVersionedItem versionedItem ) {
        this.enclosingTransaction.addVersionedItemRead( versionedItem );
    }

    @Override
    public void addVersionedItemWritten( AbstractVersionedItem versionedItem ) {
        this.enclosingTransaction.addVersionedItemWritten( versionedItem );
    }

    @Override
    public void commit() {
        // do nothing
    }

    @Override
    public void ensureWriteable() {
        this.enclosingTransaction.ensureWriteable();
    }

    @Override
    public IStmTransaction getEnclosingTransaction() {
        return this.enclosingTransaction;
    }

    @Override
    public long getSourceRevisionNumber() {
        return this.enclosingTransaction.getSourceRevisionNumber();
    }

    @Override
    public ETransactionStatus getStatus() {
        return this.enclosingTransaction.getStatus();
    }

    @Override
    public AtomicLong getTargetRevisionNumber() {
        return this.enclosingTransaction.getTargetRevisionNumber();
    }

    @Override
    public ETransactionWriteability getWriteability() {
        return this.writeability;
    }

    @Override
    public void setNewerRevisionSeen() {
        this.enclosingTransaction.setNewerRevisionSeen();
    }

    /** The outer transaction that encloses this nested one. */
    private final IStmTransaction enclosingTransaction;

    /** The writeability of this transaction. */
    private final ETransactionWriteability writeability;
}
