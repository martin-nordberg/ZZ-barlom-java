//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.revisions;

import java.util.Objects;
import java.util.Optional;

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
    public static void abortTransaction( Optional<Exception> e ) {

        IStmTransaction transaction = StmTransactionContext.getTransactionOfCurrentThread();

        try {
            // Abort the changes.
            transaction.abort( e );
        }
        finally {
            // Clear the thread's transaction.
            StmTransactionContext.transactionOfCurrentThread.set( transaction.getEnclosingTransaction() );
        }

    }

    /**
     * Creates a new read-nested write transaction. The lifecycle of the transaction must be managed by the client,
     * which is responsible for calling either commitTransaction or abortTransaction.
     */
    public static void beginReadNestedWriteTransaction() {
        StmTransactionContext.beginTransaction( ETransactionWriteability.READ_WITH_NESTED_WRITES );
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

        IStmTransaction transaction = StmTransactionContext.getTransactionOfCurrentThread();

        try {
            // Commit the changes.
            transaction.commit();
        }
        catch ( Exception e ) {
            // On any error abort the transaction.
            transaction.abort( Optional.of( e ) );
            throw e;
        }
        finally {
            // Clear the thread's transaction.
            StmTransactionContext.transactionOfCurrentThread.set( transaction.getEnclosingTransaction() );
        }

    }

    /**
     * Performs the work of the given read-nested write callback inside a newly created transaction.
     *
     * @param task       the work to be done inside a transaction.
     * @param maxRetries the maximum number of times to retry the transaction if write conflicts are encountered (must
     *                   be zero or more, zero meaning try but don't retry); ignored for nested transactions.
     *
     * @throws MaximumRetriesExceededException if the transaction fails even after the specified number of retries.
     */
    public static void doInReadNestedWriteTransaction( int maxRetries, Runnable task ) {

        StmTransactionContext.doInTransaction( ETransactionWriteability.READ_WITH_NESTED_WRITES, maxRetries, task );

    }

    /**
     * Performs the work of the given read-only callback inside a newly created transaction.
     *
     * @param task the work to be done inside a transaction.
     */
    public static void doInReadOnlyTransaction( Runnable task ) {

        StmTransactionContext.doInTransaction( ETransactionWriteability.READ_ONLY, 0, task );

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

        StmTransactionContext.doInTransaction( ETransactionWriteability.READ_WRITE, maxRetries, task );

    }

    /**
     * @return the status of the currently active transaction or NO_TRANSACTION if there is none.
     */
    public static ETransactionStatus getStatus() {

        // Get the thread-local transaction.
        IStmTransaction transaction = StmTransactionContext.transactionOfCurrentThread.get();

        // If no transaction, done.
        if ( transaction == null ) {
            return ETransactionStatus.NO_TRANSACTION;
        }

        // Ask the transaction for its status.
        return transaction.getStatus();

    }

    /**
     * Creates a new read-only transaction. The lifecycle of the transaction must be managed by the client, which is
     * responsible for calling either commit or abort on the result.
     */
    private static void beginTransaction( ETransactionWriteability writeability ) {

        IStmTransaction transaction = StmTransactionContext.transactionOfCurrentThread.get();
        if ( transaction == null ) {
            transaction = new StmTransaction( writeability );
        }
        else {
            transaction = new NestedStmTransaction( writeability, transaction );
        }

        StmTransactionContext.transactionOfCurrentThread.set( transaction );

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

        try {

            for ( int retry = 0; retry <= maxRetries; retry += 1 ) {

                try {
                    IStmTransaction transaction = StmTransactionContext.transactionOfCurrentThread.get();
                    if ( transaction == null ) {
                        transaction = new StmTransaction( writeability );
                    }
                    else {
                        transaction = new NestedStmTransaction( writeability, transaction );
                    }

                    try {
                        StmTransactionContext.transactionOfCurrentThread.set( transaction );

                        // Execute the transactional task.
                        task.run();

                        // Commit the changes.
                        transaction.commit();

                        // If succeeded, no more retries are needed.
                        return;
                    }
                    catch ( Exception e ) {
                        // On any error abort the transaction.
                        transaction.abort( Optional.of( e ) );
                        throw e;
                    }
                    finally {
                        // Clear the thread's transaction.
                        StmTransactionContext.transactionOfCurrentThread.set( transaction.getEnclosingTransaction() );
                    }
                }
                catch ( WriteConflictException e ) {
                    // Do not retry nested transactions
                    if ( StmTransactionContext.transactionOfCurrentThread.get() != null ) {
                        break;
                    }

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
     * @return the transaction that has been established for the currently running thread
     */
    static IStmTransaction getTransactionOfCurrentThread() {

        // Get the thread-local transaction.
        IStmTransaction result = StmTransactionContext.transactionOfCurrentThread.get();

        // If there is none, then it's a programming error.
        if ( result == null ) {
            throw new IllegalStateException( "Attempted to complete a transactional operation without a transaction." );
        }

        return result;

    }

    /**
     * Thread-local storage for the transaction in use by the current thread (can be only one per thread).
     */
    @SuppressWarnings( "FieldMayBeFinal" )
    private static ThreadLocal<IStmTransaction> transactionOfCurrentThread = new ThreadLocal<>();

}
