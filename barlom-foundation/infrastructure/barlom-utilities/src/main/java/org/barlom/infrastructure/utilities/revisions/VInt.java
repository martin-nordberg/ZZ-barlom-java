//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.revisions;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A version-managing handle to an integer value with transactional revisions.
 */
@SuppressWarnings( "ClassNamingConvention" )
public final class VInt
    extends AbstractVersionedItem {

    /**
     * Constructs a new versioned integer with given starting value for the current transaction's revision.
     *
     * @param value the initial value.
     */
    public VInt( int value ) {

        // Sanity check the input.
        Objects.requireNonNull( value );

        // Track everything through the current transaction.
        IStmTransaction currentTransaction = StmTransactionContext.getTransactionOfCurrentThread();

        // Make sure we have a read/write transaction
        currentTransaction.ensureWriteable();

        this.latestRevision = new AtomicReference<>( null );
        this.latestRevision.set(
            new Revision(
                value, currentTransaction.getTargetRevisionNumber(), this.latestRevision.get()
            )
        );

        // Keep track of everything we've written.
        currentTransaction.addVersionedItemWritten( this );

    }

    /**
     * Decrement the integer value by one.
     */
    public void decrement() {
        this.set( this.get() - 1 );
    }

    /**
     * Reads the version of the integer relevant for the transaction active in the currently running thread.
     *
     * @return the value as of the start of the transaction or else as written by the transaction
     */
    public int get() {

        // Track everything through the current transaction.
        IStmTransaction currentTransaction = StmTransactionContext.getTransactionOfCurrentThread();

        // Work within the transaction of the current thread.
        long sourceRevisionNumber = currentTransaction.getSourceRevisionNumber();
        long targetRevisionNumber = currentTransaction.getTargetRevisionNumber().get();

        // Loop through the revisions.
        for (
            Revision revision = this.latestRevision.get();
            revision != null;
            revision = revision.priorRevision.get()
            ) {

            final long revisionNumber = revision.revisionNumber.get();

            // If written by the current transaction, read back the written value.
            if ( revisionNumber == targetRevisionNumber ) {
                return revision.value;
            }

            // If written and committed by some other transaction, note that our transaction is already poised for
            // a write conflict if it writes anything. I.e. fail early for a write conflict.
            if ( revisionNumber > sourceRevisionNumber ) {
                currentTransaction.setNewerRevisionSeen();
            }

            // If revision is committed and older or equal to our source revision, read it.
            if ( revisionNumber <= sourceRevisionNumber && revisionNumber > 0L ) {
                // Keep track of everything we've read.
                currentTransaction.addVersionedItemRead( this );

                // Return the value found for the source revision or earlier.
                return revision.value;
            }

        }

        throw new NullPointerException( "No revision found for transaction." );

    }

    /**
     * Increment the integer value by one.
     */
    public void increment() {
        this.set( this.get() + 1 );
    }

    /**
     * Writes a new revision of the integer managed by this handle.
     *
     * @param value The new raw value to become the next revision of this item.
     */
    public void set( int value ) {

        // Sanity check the input
        Objects.requireNonNull( value );

        // Work within the transaction of the current thread.
        IStmTransaction currentTransaction = StmTransactionContext.getTransactionOfCurrentThread();

        // Make sure we have a read/write transaction
        currentTransaction.ensureWriteable();

        long sourceRevisionNumber = currentTransaction.getSourceRevisionNumber();
        long targetRevisionNumber = currentTransaction.getTargetRevisionNumber().get();

        // Loop through the revisions ...
        for (
            Revision revision = this.latestRevision.get();
            revision != null;
            revision = revision.priorRevision.get()
            ) {

            final long revisionNumber = revision.revisionNumber.get();

            // If previously written by the current transaction, just update to the newer value.
            if ( revisionNumber == targetRevisionNumber ) {
                revision.value = value;
                return;
            }

            // If revision is committed and older or equal to our source revision, need a new one.
            if ( revisionNumber <= sourceRevisionNumber && revisionNumber > 0L ) {

                // ... except if not changed, treat as a read.
                if ( value == revision.value ) {
                    currentTransaction.addVersionedItemRead( this );
                    return;
                }

                break;

            }

        }

        // Create the new revision at the front of the chain.
        this.latestRevision.set(
            new Revision(
                value, currentTransaction.getTargetRevisionNumber(), this.latestRevision.get()
            )
        );

        // Keep track of everything we've written.
        currentTransaction.addVersionedItemWritten( this );

    }

    /**
     * Internal record structure for revisions in the linked list of revisions.
     */
    private static class Revision {

        Revision( int value, AtomicLong revisionNumber, Revision priorRevision ) {
            this.priorRevision = new AtomicReference<>( priorRevision );
            this.revisionNumber = revisionNumber;
            this.value = value;
        }

        /**
         * A reference to the previous revision of the versioned item.
         */
        public final AtomicReference<Revision> priorRevision;

        /**
         * The revision number of this revision (uniquely from the transaction that wrote it).
         */
        public final AtomicLong revisionNumber;

        /**
         * The value of the versioned item at this revision.
         */
        public int value;

    }

    @Override
    void ensureNotWrittenByOtherTransaction() {

        // Work within the transaction of the current thread.
        IStmTransaction currentTransaction = StmTransactionContext.getTransactionOfCurrentThread();

        long sourceRevisionNumber = currentTransaction.getSourceRevisionNumber();

        // Loop through the revisions ...
        for (
            Revision revision = this.latestRevision.get();
            revision != null;
            revision = revision.priorRevision.get()
            ) {

            final long revisionNumber = revision.revisionNumber.get();

            // If find something newer, then transaction conflicts.
            if ( revisionNumber > sourceRevisionNumber ) {
                throw new WriteConflictException();
            }

            // If revision is committed and older or equal to our source revision, then done.
            if ( revisionNumber <= sourceRevisionNumber && revisionNumber > 0L ) {
                break;
            }

        }

    }

    @SuppressWarnings( "AssignmentToNull" )
    @Override
    void removeAbortedRevision() {

        // First check the latest revision.
        Revision revision = this.latestRevision.get();

        while ( revision.revisionNumber.get() == 0L ) {
            if ( this.latestRevision.compareAndSet( revision, revision.priorRevision.get() ) ) {
                return;
            }
        }

        // Loop through the revisions.
        Revision priorRevision = revision.priorRevision.get();
        while ( priorRevision != null ) {

            final long revisionNumber = priorRevision.revisionNumber.get();

            if ( revisionNumber == 0L ) {
                // If found & removed w/o concurrent change, then done.
                if ( revision.priorRevision.compareAndSet( priorRevision, priorRevision.priorRevision.get() ) ) {
                    return;
                }

                // If concurrent change, abandon this call and try again from the top.
                priorRevision = null;
                this.removeAbortedRevision();
            }
            else {
                // Advance through the list.
                revision = priorRevision;
                priorRevision = revision.priorRevision.get();
            }
        }

    }

    @Override
    void removeUnusedRevisions( long oldestUsableRevisionNumber ) {

        // Loop through the revisions.
        for (
            Revision revision = this.latestRevision.get();
            revision != null;
            revision = revision.priorRevision.get()
            ) {

            final long revisionNumber = revision.revisionNumber.get();

            // Truncate revisions older than the oldest usable revision.
            if ( revisionNumber == oldestUsableRevisionNumber ) {
                revision.priorRevision.set( null );
                break;
            }

        }

    }

    /**
     * Reference to the latest revision. Revisions are kept in a custom linked list with the newest revision at the head
     * of the list.
     */
    private final AtomicReference<Revision> latestRevision;

}
