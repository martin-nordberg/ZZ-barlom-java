//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

import javax.json.stream.JsonGenerator;
import java.util.List;
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
     * The fixed ID for the root package.
     */
    final UUID ROOT_PACKAGE_ID = UUID.fromString( "00000000-7a26-11e4-a545-08002741a702" );

    /**
     * Top level root package (constant).
     */
    static final IPackage ROOT_PACKAGE = new IPackage() {
        @Override
        public void generateJsonAttributes( JsonGenerator json ) {
            json.write( "id", ROOT_PACKAGE_ID.toString() )
                .writeNull( "parentPackageId" )
                .write( "name", "$" )
                .write( "path", "" );
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
        public IPackage getParentPackage() {
            return this;
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
