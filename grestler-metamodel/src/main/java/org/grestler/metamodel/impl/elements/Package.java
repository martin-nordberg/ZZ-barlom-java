//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.utilities.revisions.V;

import javax.json.stream.JsonGenerator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for Grestler packages.
 */
public class Package
    implements IPackage {

    /**
     * Constructs a new package.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package.
     * @param name          the name of the package.
     */
    public Package( UUID id, IPackage parentPackage, String name ) {

        Objects.requireNonNull( id, "Missing ID" );

        // TODO: unique name per parent package

        this.id = id;
        this.parentPackage = new V<>( parentPackage );
        this.name = new V<>( name );

    }

    @Override
    public void generateJson( JsonGenerator json ) {
        json.writeStartObject()
            .write( "id", this.getId().toString() )
            .write( "parentPackageId", this.getParentPackage().get().getId().toString() )
            .write( "name", this.getName() )
            .write( "path", this.getPath() )
            .writeEnd();
    }

    @Override
    public List<IPackage> getChildPackages() {
        // TODO
        return null;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public Optional<IPackage> getParentPackage() {
        return Optional.of( this.parentPackage.get() );
    }

    private final UUID id;

    private final V<String> name;

    private final V<IPackage> parentPackage;

}
