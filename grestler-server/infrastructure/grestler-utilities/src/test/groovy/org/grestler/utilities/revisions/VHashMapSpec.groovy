//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions

import spock.lang.Specification

/**
 * Specification for VHashMap.
 */
class VHashMapSpec
        extends Specification {

    def "Transactions allow a versioned map to be created and changed"() {

        given:
        VHashMap<String, Integer> stuff
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff = new VHashMap<>( 100 );
        }

        when:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.put( "two", 2 );
            stuff.put( "three", 3 );
            stuff.put( "four", 4 );
            stuff.put( "three", 3 );
            stuff.put( "five", 5 );
        }

        and:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.remove( "two" );
            stuff.put( "one", 1 );
        }

        and:
        StmTransactionContext.doInReadWriteTransaction( 1 ) {
            stuff.put( "zero", 0 );
        }

        then:
        StmTransactionContext.beginReadOnlyTransaction();

        expect:
        stuff.size() == 5;
        stuff.get( "zero" ).get() == 0;
        stuff.get( "one" ).get() == 1;
        !stuff.get( "two" ).isPresent();
        stuff.get( "three" ).get() == 3;
        stuff.get( "four" ).get() == 4;
        stuff.get( "five" ).get() == 5;

        cleanup:
        StmTransactionContext.commitTransaction();

    }

}
