//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;

import java.util.List;
import java.util.UUID;

/**
 * Implementation class for Grestler packages.
 */
public final class Package
    extends Element
    implements IPackage {

    /**
     * Constructs a new package.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package.
     * @param name          the name of the package.
     */
    public Package( UUID id, IPackage parentPackage, String name ) {
        super( id, parentPackage, name );
    }

    @Override
    public List<IPackage> getChildPackages() {
        // TODO
        return null;
    }

}
