package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.INamedElement;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IPackagedElement;
import org.grestler.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IVertexType;

import java.util.Comparator;

/**
 * General purpose comparators corresponding to EESortOrder.
 */
public final class Comparators {

    private Comparators() {
    }

    /**
     * Comparator for sorting directed edge types by their super/sub type hierarchy.
     */
    public static final Comparator<IDirectedEdgeType> DIRECTED_EDGE_SUPER_SUBTYPE = ( e1, e2 ) -> {
        if ( e1 == e2 ) {
            return 0;
        }
        if ( e1.isSubTypeOf( e2 ) ) {
            return 1;
        }
        if ( e2.isSubTypeOf( e1 ) ) {
            return -1;
        }
        return e1.getName().compareTo( e2.getName() );
    };

    /**
     * Comparator for sorting elements by name.
     */
    public static final Comparator<INamedElement> NAME = (
        n1, n2
    ) -> n1.getName().compareTo( n2.getName() );

    /**
     * Comparator for sorting packages by parent/child.
     */
    public static final Comparator<IPackage> PACKAGE_PARENT_CHILD = ( p1, p2 ) -> {
        if ( p1.isChildOf( p2 ) ) {
            return 1;
        }
        if ( p2.isChildOf( p1 ) ) {
            return -1;
        }
        return p1.getName().compareTo( p2.getName() );
    };

    /**
     * Comparator for sorting elements by path.
     */
    public static final Comparator<IPackagedElement> PATH = (
        p1, p2
    ) -> p1.getPath().compareTo( p2.getPath() );

    /**
     * Comparator for sorting undirected edge types by their super/sub type hierarchy.
     */
    public static final Comparator<IUndirectedEdgeType> UNDIRECTED_EDGE_SUPER_SUBTYPE = ( e1, e2 ) -> {
        if ( e1 == e2 ) {
            return 0;
        }
        if ( e1.isSubTypeOf( e2 ) ) {
            return 1;
        }
        if ( e2.isSubTypeOf( e1 ) ) {
            return -1;
        }
        return e1.getName().compareTo( e2.getName() );
    };

    /**
     * Comparator for sorting vertex types by their super/sub type hierarchy.
     */
    public static final Comparator<IVertexType> VERTEX_SUPER_SUBTYPE = ( v1, v2 ) -> {
        if ( v1 == v2 ) {
            return 0;
        }
        if ( v1.isSubTypeOf( v2 ) ) {
            return 1;
        }
        if ( v2.isSubTypeOf( v1 ) ) {
            return -1;
        }
        return v1.getName().compareTo( v2.getName() );
    };

}
