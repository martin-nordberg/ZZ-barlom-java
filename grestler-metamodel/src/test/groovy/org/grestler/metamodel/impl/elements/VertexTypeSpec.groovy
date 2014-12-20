//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.IPackage
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

    def "A top level vertex type is constructed and read"() {

        given:
        IVertexType v = IVertexType.BASE_VERTEX_TYPE;

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert v.name == "Vertex";
            assert !v.superType.isPresent();
            assert v.isSubTypeOf( v );
        }

    }

    def "Vertex type subtypes can be detected correctly"() {

        given:
        IVertexType v1;
        IVertexType v2;
        IVertexType w;
        StmTransactionContext.doInTransaction( 1 ) {
            v1 = new VertexType( id, IPackage.ROOT_PACKAGE, name, IVertexType.BASE_VERTEX_TYPE );
            v2 = new VertexType( id, IPackage.ROOT_PACKAGE, name, v1 );
            w = new VertexType( id, IPackage.ROOT_PACKAGE, name, IVertexType.BASE_VERTEX_TYPE );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert v1.superType.get() == IVertexType.BASE_VERTEX_TYPE;
            assert v1.isSubTypeOf( v1 );
            assert v2.superType.get() == v1;
            assert v2.isSubTypeOf( v1 );
            assert !v1.isSubTypeOf( v2 );
            assert !w.isSubTypeOf( v1 );
        }

    }

}
