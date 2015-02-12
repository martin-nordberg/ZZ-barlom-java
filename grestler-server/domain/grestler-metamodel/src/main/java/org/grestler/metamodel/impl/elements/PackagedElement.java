package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackagedElement;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

/**
 * Implementation of abstract element.
 */
public abstract class PackagedElement
    extends Element
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

        this.parentPackage = parentPackage;

        // TODO: unique name per parent package

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "parentPackageId", this.parentPackage.getId().toString() );

    }

    @Override
    public IPackage getParentPackage() {
        return this.parentPackage;
    }

    /** The parent package containing this packaged element. */
    private final IPackage parentPackage;

}
