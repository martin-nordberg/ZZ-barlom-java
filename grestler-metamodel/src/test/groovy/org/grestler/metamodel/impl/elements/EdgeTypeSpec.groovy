//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.IEdgeType
import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.api.elements.IVertexType
import org.grestler.utilities.revisions.StmTransaction
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for edge types.
 */
class EdgeTypeSpec
        extends Specification {

    UUID id = Uuids.makeUuid();

    String name = "Example";

    StmTransaction tx;

    def setup() {
        tx = StmTransactionContext.beginTransaction();
    }

    def cleanup() {
        tx.close();
    }

    def "A top level edge type is constructed and can be read"() {

        given:
        IEdgeType e = IEdgeType.BASE_EDGE_TYPE;

        expect:
        e.name == "Edge";
        !e.superType.isPresent();
        e.isSubTypeOf( e );
        e.tailVertexType == IVertexType.BASE_VERTEX_TYPE;
        e.headVertexType == IVertexType.BASE_VERTEX_TYPE;

    }

    def "Edge type subtypes can be detected correctly"() {

        given:
        IVertexType v1 = IVertexType.BASE_VERTEX_TYPE;
        IVertexType v2 = new VertexType( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "v2", v1 );
        IEdgeType e1 = new EdgeType( id, IPackage.ROOT_PACKAGE, name, IEdgeType.BASE_EDGE_TYPE, v1, v2 );
        IEdgeType e2 = new EdgeType( id, IPackage.ROOT_PACKAGE, name, e1, v1, v2 );
        IEdgeType e3 = new EdgeType( id, IPackage.ROOT_PACKAGE, name, IEdgeType.BASE_EDGE_TYPE, v1, v2 );

        expect:
        e1.superType.get() == IEdgeType.BASE_EDGE_TYPE;
        e1.isSubTypeOf( e1 );
        e2.superType.get() == e1;
        e2.isSubTypeOf( e1 );
        !e1.isSubTypeOf( e2 );
        !e3.isSubTypeOf( e1 );

    }

}

