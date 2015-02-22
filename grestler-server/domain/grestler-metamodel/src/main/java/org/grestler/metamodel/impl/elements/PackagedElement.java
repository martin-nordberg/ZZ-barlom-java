package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackagedElement;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation of abstract element.
 */
public abstract class PackagedElement
    extends NamedElement
    implements IPackagedElement {

    /**
     * Constructs a new element.
     *
     * @param id            the unique ID of the element.
     * @param parentPackage the parent package.
     * @param name          the name of the element.
     */
    protected PackagedElement( UUID id, IPackage parentPackage, String name ) {

        super( id, name );

        this.parentPackage = new V<>( parentPackage );

        // TODO: unique name per parent package

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "parentPackageId", this.getParentPackage().getId().toString() );

    }

    @Override
    public IPackage getParentPackage() {
        return this.parentPackage.get();
    }

    /** The parent package containing this packaged element. */
    private final V<IPackage> parentPackage;

}
