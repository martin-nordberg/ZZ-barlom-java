//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements

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
        IVertexType v = IVertexType.BASE_VERTEX_TYPE;

        expect:
        v.name == "Vertex";
        !v.superType.isPresent();
        v.isSubTypeOf( v );

    }

    def "Vertex type subtypes can be detected correctly"() {

        given:
        IVertexType v1 = new VertexType( id, IPackage.ROOT_PACKAGE, name, IVertexType.BASE_VERTEX_TYPE );
        IVertexType v2 = new VertexType( id, IPackage.ROOT_PACKAGE, name, v1 );
        IVertexType w = new VertexType( id, IPackage.ROOT_PACKAGE, name, IVertexType.BASE_VERTEX_TYPE );

        expect:
        v1.superType.get() == IVertexType.BASE_VERTEX_TYPE;
        v1.isSubTypeOf( v1 );
        v2.superType.get() == v1;
        v2.isSubTypeOf( v1 );
        !v1.isSubTypeOf( v2 );
        !w.isSubTypeOf( v1 );

    }

}
