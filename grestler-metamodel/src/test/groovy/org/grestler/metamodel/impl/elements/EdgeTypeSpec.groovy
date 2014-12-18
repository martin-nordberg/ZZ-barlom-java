package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.IEdgeType
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

    def "A top level edge type can be constructed and read"() {

        given:
        IVertexType v1;
        IVertexType v2;
        IEdgeType e
        StmTransactionContext.doInTransaction( 1 ) {
            v1 = new VertexType( Uuids.makeUuid(), "v1", Optional.empty() );
            v2 = new VertexType( Uuids.makeUuid(), "v2", Optional.of( v1 ) );

            e = new EdgeType( id, name, Optional.empty(), v1, v2 );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert e.id == id
            assert e.name == name
            assert !e.superType.isPresent();
            assert e.isSubTypeOf( e );
            assert e.fromVertexType == v1;
            assert e.toVertexType == v2;
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
            v1 = new VertexType( Uuids.makeUuid(), "v1", Optional.empty() );
            v2 = new VertexType( Uuids.makeUuid(), "v2", Optional.of( v1 ) );

            e1 = new EdgeType( id, name, Optional.empty(), v1, v2 );
            e2 = new EdgeType( id, name, Optional.of( e1 ), v1, v2 );
            e3 = new EdgeType( id, name, Optional.empty(), v1, v2 );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert !e1.superType.isPresent();
            assert e1.isSubTypeOf( e1 );
            assert e2.superType.get() == e1;
            assert e2.isSubTypeOf( e1 );
            assert !e1.isSubTypeOf( e2 );
            assert !e3.isSubTypeOf( e1 );
        }

    }

}

