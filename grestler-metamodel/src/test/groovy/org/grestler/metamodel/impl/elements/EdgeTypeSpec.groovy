package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.IEdgeType
import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.api.elements.IVertexType
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specificaton for edge types.
 */
class EdgeTypeSpec
        extends Specification {

    UUID id = Uuids.makeUuid();

    String name = "Example";

    def "A top level edge type is constructed and can be read"() {

        given:
        IEdgeType e = IEdgeType.BASE_EDGE_TYPE;

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert e.name == "Edge";
            assert !e.superType.isPresent();
            assert e.isSubTypeOf( e );
            assert e.fromVertexType == IVertexType.BASE_VERTEX_TYPE;
            assert e.toVertexType == IVertexType.BASE_VERTEX_TYPE;
        }

    }

    def "Edge type subtypes can be detected correctly"() {

        given:
        IVertexType v1;
        IVertexType v2;
        IEdgeType e1;
        IEdgeType e2;
        IEdgeType e3;
        StmTransactionContext.doInTransaction( 1 ) {
            v1 = IVertexType.BASE_VERTEX_TYPE;
            v2 = new VertexType( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "v2", v1 );

            e1 = new EdgeType( id, IPackage.ROOT_PACKAGE, name, IEdgeType.BASE_EDGE_TYPE, v1, v2 );
            e2 = new EdgeType( id, IPackage.ROOT_PACKAGE, name, e1, v1, v2 );
            e3 = new EdgeType( id, IPackage.ROOT_PACKAGE, name, IEdgeType.BASE_EDGE_TYPE, v1, v2 );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert e1.superType.get() == IEdgeType.BASE_EDGE_TYPE;
            assert e1.isSubTypeOf( e1 );
            assert e2.superType.get() == e1;
            assert e2.isSubTypeOf( e1 );
            assert !e1.isSubTypeOf( e2 );
            assert !e3.isSubTypeOf( e1 );
        }

    }

}

