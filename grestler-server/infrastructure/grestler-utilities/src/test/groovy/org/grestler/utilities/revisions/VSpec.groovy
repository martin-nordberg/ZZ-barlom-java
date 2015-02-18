//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions

import spock.lang.Specification

/**
 * Specification for versioned boxes..
 */
class VSpec
        extends Specification {

    def "Transactions allow a versioned item to be created and changed"() {

        given:
        V<Integer> stuff
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff = new V<>( 1 );
        }

        when:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.set( 2 );
        }

        then:
        StmTransactionContext.doInReadOnlyTransaction {
            assert stuff.get() == 2
        }

    }

}
