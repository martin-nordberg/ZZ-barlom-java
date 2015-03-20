//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements

import org.grestler.domain.metamodel.api.elements.*
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.infrastructure.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for edge types.
 */
class EdgeTypeSpec
        extends Specification {

    UUID id = Uuids.makeUuid();

    String name = "Example";

    def setup() {
        StmTransactionContext.beginReadWriteTransaction();
    }

    def cleanup() {
        StmTransactionContext.commitTransaction();
    }

    def "A top level edge type is constructed and can be read"() {

        given:
        IPackage root = new RootPackage( id );
        IVertexType baseVertexType = new RootVertexType( id, root );
        IEdgeType e = new RootDirectedEdgeType( id, root, baseVertexType );

        expect:
        e.name == "Directed Edge";
        !e.superType.isPresent();
        e.isSubTypeOf( e );
        e.tailVertexType == baseVertexType;
        e.headVertexType == baseVertexType;

    }

    def "Edge type subtypes can be detected correctly"() {

        given:
        IPackage root = new RootPackage( id );
        IVertexType v0 = new RootVertexType( id, root );
        IVertexType v1 = new VertexType( id, root, "v1", v0, EAbstractness.ABSTRACT );
        IEdgeType e0 = new RootDirectedEdgeType( id, root, v0 );
        IEdgeType e1 = new DirectedEdgeType(
                id,
                root,
                name,
                e0,
                EAbstractness.ABSTRACT,
                ECyclicity.UNCONSTRAINED,
                EMultiEdgedness.UNCONSTRAINED,
                ESelfLooping.UNCONSTRAINED,
                v0,
                v1,
                Optional.empty(),
                Optional.empty(),
                OptionalInt.empty(),
                OptionalInt.empty(),
                OptionalInt.empty(),
                OptionalInt.empty()
        );
        IEdgeType e2 = new DirectedEdgeType(
                id,
                root,
                name,
                e1,
                EAbstractness.ABSTRACT,
                ECyclicity.UNCONSTRAINED,
                EMultiEdgedness.UNCONSTRAINED,
                ESelfLooping.UNCONSTRAINED,
                v0,
                v1,
                Optional.empty(),
                Optional.empty(),
                OptionalInt.empty(),
                OptionalInt.empty(),
                OptionalInt.empty(),
                OptionalInt.empty()
        );
        IEdgeType e3 = new DirectedEdgeType(
                id,
                root,
                name,
                e0,
                EAbstractness.ABSTRACT,
                ECyclicity.UNCONSTRAINED,
                EMultiEdgedness.UNCONSTRAINED,
                ESelfLooping.UNCONSTRAINED,
                v0,
                v1,
                Optional.empty(),
                Optional.empty(),
                OptionalInt.empty(),
                OptionalInt.empty(),
                OptionalInt.empty(),
                OptionalInt.empty()
        );

        expect:
        e1.superType.get() == e0;
        e1.isSubTypeOf( e0 );
        e1.isSubTypeOf( e1 );
        e2.superType.get() == e1;
        e2.isSubTypeOf( e0 );
        e2.isSubTypeOf( e1 );
        e2.isSubTypeOf( e2 );
        !e1.isSubTypeOf( e2 );
        !e3.isSubTypeOf( e1 );

    }

}
