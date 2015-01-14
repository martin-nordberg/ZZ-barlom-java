package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IElement;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.utilities.revisions.V;

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
        this.parentPackage = new V<>( parentPackage );
        this.name = new V<>( name );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.getId().toString() )
            .write( "parentPackageId", this.getParentPackage().getId().toString() )
            .write( "name", this.getName() )
            .write( "path", this.getPath() );
    }

    @Override
    public final UUID getId() {
        return this.id;
    }

    @Override
    public final String getName() {
        return this.name.get();
    }

    @Override
    public final IPackage getParentPackage() {
        return this.parentPackage.get();
    }

    /** The unique ID of this element. */
    private final UUID id;

    /** The name of this element. */
    private final V<String> name;

    /** The package containing this element. */
    private final V<IPackage> parentPackage;

}
