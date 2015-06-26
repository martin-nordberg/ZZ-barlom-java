//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.infrastructure.utilities.revisions

import spock.lang.Specification

/**
 * Specification for versioned arrays.
 */
class VArraySpec
    extends Specification {

    def "Transactions allow a versioned list to be created and changed"() {

        given:
        VArray<Integer> stuff
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff = new VArray<>();
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
        stuff.size() == 3;
        stuff.getAt( 0 ) == 3;
        stuff.getAt( 1 ) == 4;
        stuff.getAt( 2 ) == 5;

        cleanup:
        StmTransactionContext.commitTransaction();

    }

    def "A versioned array grows and shrinks as needed"() {

        given:
        VArray<Integer> stuff
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff = new VArray<>();
        }

        when:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            for ( int i = 0; i < 100; i += 1 ) {
                stuff.add( i );
            }
        }

        and:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            for ( int i = 0; i < 100; i += 2 ) {
                stuff.remove( i );
            }
        }

        then:
        StmTransactionContext.beginReadOnlyTransaction();

        expect:
        stuff.size() == 50;

        cleanup:
        StmTransactionContext.commitTransaction();

    }

}
