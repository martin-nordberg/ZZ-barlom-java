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
class VertexTypeSpec
        extends Specification {

    UUID id = Uuids.makeUuid();

    String name = "Example";

    def "A top level vertex type can be constructed and read"() {

        given:
        IVertexType v
        StmTransactionContext.doInTransaction( 1 ) {
            v = new VertexType( id, name, Optional.empty() );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert v.getId() == id
            assert v.getName() == name
            assert !v.getSuperType().isPresent();
            assert v.isSubTypeOf( v );
        }

    }

    def "Vertex type subtypes can be detected correctly"() {

        given:
        IVertexType v1;
        IVertexType v2;
        IVertexType w;
        StmTransactionContext.doInTransaction( 1 ) {
            v1 = new VertexType( id, name, Optional.empty() );
            v2 = new VertexType( id, name, Optional.of( v1 ) );
            w = new VertexType( id, name, Optional.empty() );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert !v1.getSuperType().isPresent();
            assert v1.isSubTypeOf( v1 );
            assert v2.getSuperType().get() == v1;
            assert v2.isSubTypeOf( v1 );
            assert !v1.isSubTypeOf( v2 );
            assert !w.isSubTypeOf( v1 );
        }

    }

}
