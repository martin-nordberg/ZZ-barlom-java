//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import javax.json.stream.JsonGenerator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface to a package of vertex types and edge types.
 */
public interface IPackage
    extends IElement {

    /**
     * @return the packages that are children of this one.
     */
    List<IPackage> getChildPackages();

    /**
     * @return the parent of this element.
     */
    Optional<IPackage> getParentPackage();

    /**
     * @return the fully qualified path to this element.
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
        public void generateJson( JsonGenerator json ) {
            json.writeStartObject()
                .write( "id", ROOT_PACKAGE_ID.toString() )
                .writeNull( "parentPackageId" )
                .write( "name", "$" )
                .write( "path", "" )
                .writeEnd();
        }

        @Override
        public List<IPackage> getChildPackages() {
            // TODO
            return null;
        }

        @Override
        public UUID getId() {
            return ROOT_PACKAGE_ID;
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

    /**
     * The fixed ID for the root package.
     */
    final UUID ROOT_PACKAGE_ID = UUID.fromString( "00000000-7a26-11e4-a545-08002741a702" );
}
