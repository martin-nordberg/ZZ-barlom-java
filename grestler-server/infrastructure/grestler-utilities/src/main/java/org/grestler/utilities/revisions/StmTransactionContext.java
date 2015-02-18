//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

import java.util.Objects;

/**
 * Utility class for managing STM transactions.
 */
public final class StmTransactionContext {

    private StmTransactionContext() {
        throw new UnsupportedOperationException( "Static utility class only." );
    }

    /**
     * Aborts the current transaction.
     */
    public static void abortTransaction() {

        StmTransaction transaction = StmTransactionContext.getTransactionOfCurrentThread();

        try {
            // Abort the changes.
            transaction.abort();
        }
        finally {
            // Clear the thread's transaction.
            // TODO: nested transactions
            StmTransactionContext.transactionOfCurrentThread.set( null );
        }

    }

    /**
     * Creates a new read-only transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commitTransaction or abortTransaction.
     */
    public static void beginReadOnlyTransaction() {
        StmTransactionContext.beginTransaction( ETransactionWriteability.READ_ONLY );
    }

    /**
     * Creates a new read-write transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commitTransaction or abortTransaction.
     */
    public static void beginReadWriteTransaction() {
        StmTransactionContext.beginTransaction( ETransactionWriteability.READ_WRITE );
    }

    /**
     * Commits the current transaction.
     */
    public static void commitTransaction() {

        StmTransaction transaction = StmTransactionContext.getTransactionOfCurrentThread();

        try {
            // Commit the changes.
            transaction.commit();
        }
        catch ( Throwable e ) {
            // On any error abort the transaction.
            transaction.abort();
            throw e;
        }
        finally {
            // Clear the thread's transaction.
            // TODO: nested transactions
            StmTransactionContext.transactionOfCurrentThread.set( null );
        }

    }

    /**
     * Performs the work of the given read-only callback inside a newly created transaction.
     *
     * @param task the work to be done inside a transaction.
     */
    public static void doInReadOnlyTransaction( Runnable task ) {

        // TODO: nested transactions

        // If a transaction is already in progress, just run the task directly; otherwise create one.
        if ( StmTransactionContext.transactionOfCurrentThread.get() != null ) {
            task.run();
        }
        else {
            StmTransactionContext.doInTransaction( ETransactionWriteability.READ_ONLY, 0, task );
        }

    }

    /**
     * Performs the work of the given read-write callback inside a newly created transaction.
     *
     * @param task       the work to be done inside a transaction.
     * @param maxRetries the maximum number of times to retry the transaction if write conflicts are encountered (must
     *                   be zero or more, zero meaning try but don't retry); ignored for nested transactions.
     *
     * @throws MaximumRetriesExceededException if the transaction fails even after the specified number of retries.
     */
    public static void doInReadWriteTransaction( int maxRetries, Runnable task ) {

        // TODO: nested transactions

        // If a transaction is already in progress, just run the task directly.
        if ( StmTransactionContext.transactionOfCurrentThread.get() != null ) {
            StmTransactionContext.transactionOfCurrentThread.get().ensureWriteable();
            task.run();
        }
        else {
            StmTransactionContext.doInTransaction( ETransactionWriteability.READ_WRITE, maxRetries, task );
        }
    }

    /**
     * @return the transaction that has been established for the currently running thread
     */
    static StmTransaction getTransactionOfCurrentThread() {

        // Get the thread-local transaction.
        StmTransaction result = StmTransactionContext.transactionOfCurrentThread.get();

        // If there is none, then it's a programming error.
        if ( result == null ) {
            throw new IllegalStateException( "Attempted to complete a transactional operation without a transaction." );
        }

        return result;

    }

    /**
     * Creates a new read-only transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commit or abort on the result.
     */
    private static void beginTransaction( ETransactionWriteability writeability ) {

        // Force transactions to be one per thread.
        if ( StmTransactionContext.transactionOfCurrentThread.get() != null ) {
            throw new IllegalStateException( "Transaction already in progress for this thread." );
        }

        StmTransactionContext.transactionOfCurrentThread.set( new StmTransaction( writeability ) );

    }

    /**
     * Performs the work of the given callback inside a newly created transaction.
     *
     * @param task       the work to be done inside a transaction.
     * @param maxRetries the maximum number of times to retry the transaction if write conflicts are encountered (must
     *                   be zero or more, zero meaning try but don't retry).
     *
     * @throws MaximumRetriesExceededException if the transaction fails even after the specified number of retries.
     */
    private static void doInTransaction( ETransactionWriteability writeability, int maxRetries, Runnable task ) {

        // Sanity check the input.
        Objects.requireNonNull( task );
        if ( maxRetries < 0 ) {
            throw new IllegalArgumentException( "Retry count must be greater than or equal to zero." );
        }

        // TODO: nested transactions

        // Force transactions to be one per thread.
        if ( StmTransactionContext.transactionOfCurrentThread.get() != null ) {
            throw new IllegalStateException( "Transaction already in progress for this thread." );
        }

        try {

            for ( int retry = 0; retry <= maxRetries; retry += 1 ) {

                try {
                    StmTransaction transaction = new StmTransaction( writeability );

                    try {
                        StmTransactionContext.transactionOfCurrentThread.set( transaction );

                        // Execute the transactional task.
                        task.run();

                        // Commit the changes.
                        transaction.commit();

                        // If succeeded, no more retries are needed.
                        return;
                    }
                    catch ( Throwable e ) {
                        // On any error abort the transaction.
                        transaction.abort();
                        throw e;
                    }
                    finally {
                        // Clear the thread's transaction.
                        // TODO: nested transactions
                        StmTransactionContext.transactionOfCurrentThread.set( null );
                    }
                }
                catch ( WriteConflictException e ) {
                    // Ignore the exception; go around the loop again....

                    // Increment the thread priority for a better chance on next try.
                    if ( Thread.currentThread().getPriority() < Thread.MAX_PRIORITY ) {
                        Thread.currentThread().setPriority( Thread.currentThread().getPriority() + 1 );
                    }
                }

            }

            // If we dropped out of the loop, then we exceeded the retry count.
            throw new MaximumRetriesExceededException();

        }
        finally {
            // Restore the thread priority after any retries.
            Thread.currentThread().setPriority( Thread.NORM_PRIORITY );
        }

    }

    /**
     * Thread-local storage for the transaction in use by the current thread (can be only one per thread).
     */
    @SuppressWarnings( "FieldMayBeFinal" )
    private static ThreadLocal<StmTransaction> transactionOfCurrentThread = new ThreadLocal<>();

}
