//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.IVertexType
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for vertex types.
 */
class VertexTypeSpec extends Specification {

    def id = Uuids.makeUuid();
    def name = "Example";

    def "A top level vertex type can be constructed and read"() {

        given:
        IVertexType v
        StmTransactionContext.doInTransaction(1) {
            v = new VertexType( id, name );
        }

        expect:
        StmTransactionContext.doInTransaction(1) {
            assert v.getId() == id
            assert v.getName() == name
            assert !v.getSuperType().isPresent();
            assert v.isSubTypeOf( v );
        }

    }

}
