//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements

import org.grestler.metamodel.api.elements.EAbstractness
import org.grestler.metamodel.api.elements.IPackage
import org.grestler.metamodel.api.elements.IVertexType
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
        IVertexType v1 = new VertexType( id, root, name, v, EAbstractness.ABSTRACT );
        IVertexType v2 = new VertexType( id, root, name, v1, EAbstractness.ABSTRACT );
        IVertexType w = new VertexType( id, root, name, v, EAbstractness.ABSTRACT );

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
