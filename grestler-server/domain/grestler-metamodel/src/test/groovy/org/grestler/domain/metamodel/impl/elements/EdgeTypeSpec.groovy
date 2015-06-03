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
        IVertexType v1 = new VertexType(
                new IVertexType.Record(
                        id,
                        root.id,
                        "v1",
                        EAbstractness.ABSTRACT,
                        v0.id
                ),
                root,
                v0
        );
        IEdgeType e0 = new RootDirectedEdgeType( id, root, v0 );
        IEdgeType e1 = new DirectedEdgeType(
                new IDirectedEdgeType.Record(
                        id,
                        root.id,
                        e0.id,
                        name,
                        EAbstractness.ABSTRACT,
                        ECyclicity.UNCONSTRAINED,
                        EMultiEdgedness.UNCONSTRAINED,
                        ESelfLooping.UNCONSTRAINED,
                        Optional.empty(),
                        v0.id,
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        Optional.empty(),
                        v1.id
                ),
                root,
                e0,
                v0,
                v1
        );
        IEdgeType e2 = new DirectedEdgeType(
                new IDirectedEdgeType.Record(
                        id,
                        root.id,
                        e1.id,
                        name,
                        EAbstractness.ABSTRACT,
                        ECyclicity.UNCONSTRAINED,
                        EMultiEdgedness.UNCONSTRAINED,
                        ESelfLooping.UNCONSTRAINED,
                        Optional.empty(),
                        v0.id,
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        Optional.empty(),
                        v1.id
                ),
                root,
                e1,
                v0,
                v1
        );
        IEdgeType e3 = new DirectedEdgeType(
                new IDirectedEdgeType.Record(
                        id,
                        root.id,
                        e0.id,
                        name,
                        EAbstractness.ABSTRACT,
                        ECyclicity.UNCONSTRAINED,
                        EMultiEdgedness.UNCONSTRAINED,
                        ESelfLooping.UNCONSTRAINED,
                        Optional.empty(),
                        v0.id,
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        OptionalInt.empty(),
                        Optional.empty(),
                        v1.id
                ),
                root,
                e0,
                v0,
                v1
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

