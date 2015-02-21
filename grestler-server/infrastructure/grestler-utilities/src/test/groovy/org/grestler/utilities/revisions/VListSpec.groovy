//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions

import spock.lang.Specification

/**
 * Specification for versioned boxes..
 */
class VListSpec
        extends Specification {

    def "Transactions allow a versioned list to be created and changed"() {

        given:
        VList<Integer> stuff
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff = new VList<>();
        }

        when:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.add( 2 );
            stuff.add( 3 );
        }

        and:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.remove( 2 );
            stuff.add( 4 );
        }

        and:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.add( 5 );
        }

        then:
        StmTransactionContext.beginReadOnlyTransaction();

        expect:
        stuff.get( 0 ) == 3;
        stuff.get( 1 ) == 4;
        stuff.get( 2 ) == 5;

        cleanup:
        StmTransactionContext.commitTransaction();

    }

}
