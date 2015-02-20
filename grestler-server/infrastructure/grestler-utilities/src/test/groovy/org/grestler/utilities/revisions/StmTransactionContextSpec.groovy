package org.grestler.utilities.revisions

import spock.lang.Specification

/**
 * Specification for started and ending transactions.
 */
class StmTransactionContextSpec
        extends Specification {

    def "A read-only transaction can be started and committed"() {
        given:
        StmTransactionContext.beginReadOnlyTransaction();
        StmTransactionContext.commitTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-write transaction can be started and committed"() {
        given:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.commitTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-only transaction can be started and aborted"() {
        given:
        StmTransactionContext.beginReadOnlyTransaction();
        StmTransactionContext.abortTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-write transaction can be started and aborted"() {
        given:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.abortTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A nested read-write transaction can be started and committed"() {
        given:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.commitTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        and:
        StmTransactionContext.commitTransaction();
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-only transaction can be nested in a read-write transaction"() {
        given:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.beginReadOnlyTransaction();
        StmTransactionContext.commitTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        and:
        StmTransactionContext.commitTransaction();
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-write transaction cannot be nested in a read-only transaction"() {
        setup:
        StmTransactionContext.beginReadOnlyTransaction();

        when:
        StmTransactionContext.beginReadWriteTransaction();

        then:
        thrown IllegalStateException;
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        cleanup:
        StmTransactionContext.abortTransaction();
    }

    def "Aborting a nested transaction is unrecoverable"() {
        setup:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.beginReadWriteTransaction();

        when:
        StmTransactionContext.abortTransaction();

        then:
        thrown NestedStmTransactionAborted;
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        cleanup:
        StmTransactionContext.abortTransaction();
    }

}
