//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.revisions.V;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for vertex types.
 */
public final class VertexType
    extends Element
    implements IVertexType {

    /**
     * Constructs a new vertex type.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type.
     */
    public VertexType(
        UUID id, IPackage parentPackage, String name, IVertexType superType
    ) {
        super( id, parentPackage, name );

        this.superType = new V<>( superType );
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        return Optional.of( this.superType.get() );
    }

    /** The super type of this vertex type. */
    private final V<IVertexType> superType;

}
