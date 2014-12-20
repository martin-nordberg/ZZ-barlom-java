//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface to a package of vertex types and edge types.
 */
public interface IPackage {

    /**
     * @return the packages that are children of this one.
     */
    List<IPackage> getChildPackages();

    /**
     * @return the unique ID of this package.
     */
    UUID getId();

    /**
     * @return the name of this package.
     */
    String getName();

    /**
     * @return the parent of this package.
     */
    Optional<IPackage> getParentPackage();

    /**
     * @return the fully qualified path to this package.
     */
    default String getPath() {

        String result = this.getParentPackage().get().getPath();

        if ( !result.isEmpty() ) {
            return result + "." + this.getName();
        }

        return this.getName();

    }

    /**
     * Determines whether this package is a direct or indirect child of the given package.
     *
     * @param parentPackage the potential parent.
     *
     * @return true if this package is a child or grandchild of the given parent package.
     */
    default boolean isChildOf( IPackage parentPackage ) {

        IPackage parentPkg = this.getParentPackage().get();

        return parentPkg == parentPackage || parentPkg.isChildOf( parentPackage );

    }

    /**
     * Top level root package (constant).
     */
    static final IPackage ROOT_PACKAGE = new IPackage() {
        @Override
        public List<IPackage> getChildPackages() {
            // TODO
            return null;
        }

        @Override
        public UUID getId() {
            return UUID.fromString( "00000000-7a26-11e4-a545-08002741a702" );
        }

        @Override
        public String getName() {
            return "$";
        }

        @Override
        public Optional<IPackage> getParentPackage() {
            return Optional.empty();
        }

        @Override
        public String getPath() {
            return "";
        }

        @Override
        public boolean isChildOf( IPackage parentPackage ) {
            return false;
        }
    };

}
