package org.grestler.infrastructure.utilities.revisions

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
        StmTransactionContext.abortTransaction( Optional.empty() );

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-write transaction can be started and aborted"() {
        given:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.abortTransaction( Optional.empty() );

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

    def "A read-only transaction can be nested in a read-nested write transaction"() {
        given:
        StmTransactionContext.beginReadNestedWriteTransaction();
        StmTransactionContext.beginReadOnlyTransaction();
        StmTransactionContext.commitTransaction();

        expect:
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        and:
        StmTransactionContext.commitTransaction();
        StmTransactionContext.getStatus() == ETransactionStatus.NO_TRANSACTION;
    }

    def "A read-write transaction can be nested in a read-nested write transaction"() {
        given:
        StmTransactionContext.beginReadNestedWriteTransaction();
        StmTransactionContext.beginReadWriteTransaction();
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
        StmTransactionContext.abortTransaction( Optional.empty() );
    }

    def "A read-nested write transaction cannot be nested in a read-only transaction"() {
        setup:
        StmTransactionContext.beginReadOnlyTransaction();

        when:
        StmTransactionContext.beginReadNestedWriteTransaction();

        then:
        thrown IllegalStateException;
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        cleanup:
        StmTransactionContext.abortTransaction( Optional.empty() );
    }

    def "Aborting a nested transaction is unrecoverable"() {
        setup:
        StmTransactionContext.beginReadWriteTransaction();
        StmTransactionContext.beginReadWriteTransaction();

        when:
        StmTransactionContext.abortTransaction( Optional.empty() );

        then:
        thrown NestedStmTransactionAbortedException;
        StmTransactionContext.getStatus() == ETransactionStatus.IN_PROGRESS;

        cleanup:
        StmTransactionContext.abortTransaction( Optional.empty() );
    }

}
