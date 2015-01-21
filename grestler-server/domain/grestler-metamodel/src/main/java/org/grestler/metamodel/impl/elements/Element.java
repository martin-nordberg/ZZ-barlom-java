package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IElement;
import org.grestler.metamodel.api.elements.IPackage;

import javax.json.stream.JsonGenerator;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of abstract element.
 */
public abstract class Element
    implements IElement {

    /**
     * Constructs a new element.
     *
     * @param id            the unique ID of the element.
     * @param parentPackage the parent element.
     * @param name          the name of the element.
     */
    protected Element( UUID id, IPackage parentPackage, String name ) {

        Objects.requireNonNull( id, "Missing ID" );

        // TODO: unique name per parent package

        this.id = id;
        this.parentPackage = parentPackage;
        this.name = name;

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() )
            .write( "parentPackageId", this.parentPackage.getId().toString() )
            .write( "name", this.name )
            .write( "path", this.getPath() );
    }

    @Override
    public final UUID getId() {
        return this.id;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final IPackage getParentPackage() {
        return this.parentPackage;
    }

    /** The unique ID of this element. */
    private final UUID id;

    /** The name of this element. */
    private final String name;

    /** The package containing this element. */
    private final IPackage parentPackage;

}
