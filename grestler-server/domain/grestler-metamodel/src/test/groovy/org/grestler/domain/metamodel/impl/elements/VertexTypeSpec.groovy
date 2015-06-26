//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements

import org.grestler.domain.metamodel.api.elements.EAbstractness
import org.grestler.domain.metamodel.api.elements.IPackage
import org.grestler.domain.metamodel.api.elements.IVertexType
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext
import org.grestler.infrastructure.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for vertex types.
 */
class VertexTypeSpec
    extends Specification {

    UUID id = Uuids.makeUuid();

    String name = "Example";

    def setup() {
        StmTransactionContext.beginReadWriteTransaction();
    }

    def cleanup() {
        StmTransactionContext.commitTransaction();
    }

    def "A top level vertex type is constructed and read"() {

        given:
        IVertexType v = new RootVertexType( id, new RootPackage( Uuids.makeUuid() ) );

        expect:
        v.id == id;
        v.name == "Vertex";
        !v.superType.isPresent();
        v.isSubTypeOf( v );

    }

    def "Vertex type subtypes can be detected correctly"() {

        given:
        IPackage root = new RootPackage( id );
        IVertexType v = new RootVertexType( id, root );
        IVertexType v1 = new VertexType(
            new IVertexType.Record( id, root.id, name, EAbstractness.ABSTRACT, v.id ),
            root,
            v
        );
        IVertexType v2 = new VertexType(
            new IVertexType.Record( id, root.id, name, EAbstractness.ABSTRACT, v1.id ),
            root,
            v1
        );
        IVertexType w = new VertexType(
            new IVertexType.Record( id, root.id, name, EAbstractness.ABSTRACT, v.id ),
            root,
            v
        );

        expect:
        v1.superType.get() == v;
        v1.isSubTypeOf( v );
        v1.isSubTypeOf( v1 );
        v2.superType.get() == v1;
        v2.isSubTypeOf( v );
        v2.isSubTypeOf( v1 );
        v2.isSubTypeOf( v2 );
        !v1.isSubTypeOf( v2 );
        w.isSubTypeOf( v );
        !w.isSubTypeOf( v1 );

    }

}
