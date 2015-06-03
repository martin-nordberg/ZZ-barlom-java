package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IPackagedElement;
import org.grestler.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;

/**
 * Implementation of abstract element.
 */
public abstract class PackagedElement
    extends NamedElement
    implements IPackagedElement {

    /**
     * Constructs a new element.
     *
     * @param record        the attributes of the element.
     * @param parentPackage the parent package.
     */
    protected PackagedElement( IPackagedElement.Record record, IPackage parentPackage ) {

        super( record );

        this.parentPackage = new V<>( parentPackage );

        // TODO: unique name per parent package

        ( (IPackageUnderAssembly) parentPackage ).addChildElement( this );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "parentPackageId", this.getParentPackage().getId().toString() );

    }

    @Override
    public final IPackage getParentPackage() {
        return this.parentPackage.get();
    }

    /**
     * Changes the package containing this element.
     *
     * @param parentPackage the new parent package.
     */
    public final void setParentPackage( IPackage parentPackage ) {

        ( (IPackageUnderAssembly) this.parentPackage.get() ).removeChildElement( this );

        this.parentPackage.set( parentPackage );

        ( (IPackageUnderAssembly) parentPackage ).addChildElement( this );

    }

    /** The parent package containing this packaged element. */
    private final V<IPackage> parentPackage;

}
