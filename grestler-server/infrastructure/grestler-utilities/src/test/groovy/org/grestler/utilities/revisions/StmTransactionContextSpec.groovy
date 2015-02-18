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

}
