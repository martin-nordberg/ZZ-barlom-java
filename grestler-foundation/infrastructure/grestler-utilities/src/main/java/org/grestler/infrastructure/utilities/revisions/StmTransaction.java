//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.revisions;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Utility class for managing in-memory transactions. The code is similar to "versioned boxes", the concept behind JVSTM
 * for software transactional memory. However, this code is much more streamlined, though very experimental.
 */
final class StmTransaction
    implements IStmTransaction {

    /**
     * Constructs a new transaction.
     *
     * @param writeability whether the transaction will allow writing changes.
     */
    StmTransaction( ETransactionWriteability writeability ) {

        this.writeability = writeability;

        // Spin until we get a next rev number and put it in the queue of rev numbers in use w/o concurrent change.
        // (We avoid concurrent change because if another thread bumped the revisions in use, it might also have
        // cleaned up the revision before we said we were using it.)
        while ( true ) {
            long sourceRevNumber = StmTransaction.lastCommittedRevisionNumber.get();
            StmTransaction.sourceRevisionsInUse.add( sourceRevNumber );
            if ( sourceRevNumber == StmTransaction.lastCommittedRevisionNumber.get() ) {
                this.sourceRevisionNumber = sourceRevNumber;
                break;
            }
            StmTransaction.sourceRevisionsInUse.remove( sourceRevNumber );
        }

        // Use the next negative pending revision number to mark our writes.
        this.targetRevisionNumber = new AtomicLong( StmTransaction.lastPendingRevisionNumber.decrementAndGet() );

        // Track the versioned items read and written by this transaction.
        this.versionedItemsRead = new HashSet<>();
        this.versionedItemsWritten = new HashSet<>();

        // Flag a write conflict as early as possible.
        this.newerRevisionSeen = false;

        // Establish a link for putting this transaction in a linked list of completed transactions.
        this.nextTransactionAwaitingCleanUp = new AtomicReference<>( null );

    }

    @Override
    public void abort( Optional<Exception> e ) {

        // Revision number = 0 indicates an aborted transaction.
        this.targetRevisionNumber.set( 0L );

        // Clean up aborted revisions ...
        this.versionedItemsWritten.forEach( org.grestler.infrastructure.utilities.revisions.AbstractVersionedItem::removeAbortedRevision );

        this.versionedItemsRead.clear();
        this.versionedItemsWritten.clear();

        // Trigger any clean up that is possible from no longer needing our source version.
        this.cleanUpOlderRevisions();

    }

    @Override
    public void addVersionedItemRead( AbstractVersionedItem versionedItem ) {

        // Sanity check the input.
        Objects.requireNonNull( versionedItem );

        // Track versioned items read by this transaction.
        this.versionedItemsRead.add( versionedItem );

    }

    @Override
    public void addVersionedItemWritten( AbstractVersionedItem versionedItem ) {

        // Sanity check the input.
        Objects.requireNonNull( versionedItem );

        // Track all versioned items written by this transaction.
        this.versionedItemsWritten.add( versionedItem );

        // If we have already seen a write conflict, fail early.
        if ( this.newerRevisionSeen ) {
            throw new WriteConflictException();
        }

    }

    @Override
    public void commit() {

        // TBD: notify observers of read & written items inside transaction -- use a callback interface

        // Make the synchronized changed to make the transaction permanent.
        if ( !this.versionedItemsWritten.isEmpty() ) {
            StmTransaction.writeTransaction( this );
        }

        // TBD: notify observers of read & written items outside the transaction -- use a callback interface

        // No longer hang on to the items read.
        this.versionedItemsRead.clear();

        // Add this transaction (with its written revisions) to a queue awaiting clean up when no longer needed.
        this.awaitCleanUp();

        // Trigger any clean up that is possible from no longer needing our source version.
        this.cleanUpOlderRevisions();

    }

    @Override
    public void ensureWriteable() {
        if ( this.writeability != ETransactionWriteability.READ_WRITE ) {
            throw new IllegalStateException( "Attempted to write a value during a read-only transaction." );
        }
    }

    @SuppressWarnings( "ReturnOfNull" )
    @Override
    public IStmTransaction getEnclosingTransaction() {
        return null;
    }

    @Override
    public long getSourceRevisionNumber() {
        return this.sourceRevisionNumber;
    }

    @Override
    public ETransactionStatus getStatus() {
        long targetRevNumber = this.targetRevisionNumber.get();
        if ( targetRevNumber < 0L ) {
            return ETransactionStatus.IN_PROGRESS;
        }
        if ( targetRevNumber == 0 ) {
            return ETransactionStatus.ABORTED;
        }
        return ETransactionStatus.COMMITTED;
    }

    @Override
    public AtomicLong getTargetRevisionNumber() {
        return this.targetRevisionNumber;
    }

    @Override
    public ETransactionWriteability getWriteability() {
        return this.writeability;
    }

    @Override
    public void setNewerRevisionSeen() {

        // If we have previously written something, then we've detected a write conflict; fail early.
        if ( !this.versionedItemsWritten.isEmpty() ) {
            throw new WriteConflictException();
        }

        // Track the newer revision number to fail early if we subsequently write something.
        this.newerRevisionSeen = true;

    }

    /**
     * Atomically commits the given transaction.
     *
     * @param transaction the transaction to commit
     *
     * @throws WriteConflictException if some other transaction has written some value the given transaction read.
     */
    private static synchronized void writeTransaction( StmTransaction transaction ) {

        // Check for conflicts.
        transaction.versionedItemsRead.forEach( AbstractVersionedItem::ensureNotWrittenByOtherTransaction );

        // Set the revision number to a committed value.
        transaction.targetRevisionNumber.set( StmTransaction.lastCommittedRevisionNumber.incrementAndGet() );

    }

    /**
     * Puts this transaction at the head of a list of all transactions awaiting clean up.
     */
    private void awaitCleanUp() {

        // Get the first transaction awaiting clean up.
        StmTransaction firstTransAwaitingCleanUp = StmTransaction.firstTransactionAwaitingCleanUp.get();

        // Link this transaction into the head of the list.
        this.nextTransactionAwaitingCleanUp.set( firstTransAwaitingCleanUp );

        // Spin until we do both atomically.
        while ( !StmTransaction.firstTransactionAwaitingCleanUp.compareAndSet( firstTransAwaitingCleanUp, this ) ) {
            firstTransAwaitingCleanUp = StmTransaction.firstTransactionAwaitingCleanUp.get();
            this.nextTransactionAwaitingCleanUp.set( firstTransAwaitingCleanUp );
        }

    }

    /**
     * Removes the source revision number of this transaction from those in use. Cleans up older revisions if not in use
     * by other transactions.
     */
    private void cleanUpOlderRevisions() {

        // We're no longer using the source revision.
        final long priorOldestRevisionInUse = StmTransaction.sourceRevisionsInUse.peek();
        StmTransaction.sourceRevisionsInUse.remove( this.sourceRevisionNumber );

        // Determine the oldest revision still needed.
        Long oldestRevisionInUse = StmTransaction.sourceRevisionsInUse.peek();
        if ( oldestRevisionInUse == null ) {
            oldestRevisionInUse = priorOldestRevisionInUse;
        }

        //  Remove each transaction awaiting clean up that has a target revision number older than needed.
        AtomicReference<StmTransaction> tref = StmTransaction.firstTransactionAwaitingCleanUp;
        StmTransaction t = tref.get();
        if ( t == null ) {
            return;
        }

        AtomicReference<StmTransaction> trefNext = t.nextTransactionAwaitingCleanUp;
        StmTransaction tNext = trefNext.get();

        while ( true ) {
            if ( t.targetRevisionNumber.get() <= oldestRevisionInUse ) {
                if ( tref.compareAndSet( t, tNext ) ) {
                    // Remove revisions older than the now unused revision number.
                    t.removeUnusedRevisions();
                    t.nextTransactionAwaitingCleanUp.set( null );
                }
            }
            else {
                tref = trefNext;
            }

            // Advance through the list of transactions awaiting clean up.
            t = tref.get();
            if ( t == null ) {
                return;
            }
            trefNext = t.nextTransactionAwaitingCleanUp;
            tNext = trefNext.get();
        }

    }

    /**
     * Cleans up all the referenced versioned items written by this transaction.
     */
    private void removeUnusedRevisions() {

        // Remove all revisions older than the one written by this transaction.
        final long oldestUsableRevNumber = this.targetRevisionNumber.get();
        for ( AbstractVersionedItem versionedItem : this.versionedItemsWritten ) {
            versionedItem.removeUnusedRevisions( oldestUsableRevNumber );
        }

        // Stop referencing the versioned items.
        this.versionedItemsWritten.clear();

    }

    /**
     * Head of a linked list of transactions awaiting clean up.
     */
    @SuppressWarnings( "FieldMayBeFinal" )
    private static AtomicReference<StmTransaction> firstTransactionAwaitingCleanUp = new AtomicReference<>( null );

    /**
     * Monotone increasing revision number incremented whenever a transaction is successfully committed.
     */
    @SuppressWarnings( "FieldMayBeFinal" )
    private static AtomicLong lastCommittedRevisionNumber = new AtomicLong( 0L );

    /**
     * Monotone decreasing revision number decremented whenever a transaction is started. Negative value indicates a
     * transaction in progress.
     */
    @SuppressWarnings( "FieldMayBeFinal" )
    private static AtomicLong lastPendingRevisionNumber = new AtomicLong( 0L );

    /**
     * Priority queue of revision numbers currently in use as the source revision for some transaction.
     */
    @SuppressWarnings( "FieldMayBeFinal" )
    private static Queue<Long> sourceRevisionsInUse = new PriorityBlockingQueue<>();

    /**
     * A newer revision number seen during reading will cause a write conflict if anything writes through this
     * transaction.
     */
    private boolean newerRevisionSeen;

    /**
     * The next transaction in a linked list of transactions awaiting clean up.
     */
    private final AtomicReference<StmTransaction> nextTransactionAwaitingCleanUp;

    /**
     * The revision number being read by this transaction.
     */
    private final long sourceRevisionNumber;

    /**
     * The revision number being written by this transaction. Negative while the transaction is running; zero if the
     * transaction is aborted; positive after the transaction has been committed.
     */
    private final AtomicLong targetRevisionNumber;

    /**
     * The versioned items read by this transaction.
     */
    private final Set<AbstractVersionedItem> versionedItemsRead;

    /**
     * The versioned item written by this transaction.
     */
    private final Set<AbstractVersionedItem> versionedItemsWritten;

    /**
     * Whether this transaction is allowed to write changes.
     */
    private final ETransactionWriteability writeability;

}
