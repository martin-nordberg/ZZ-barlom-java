//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/persistence/restserver/api/queries
 */

/*
 // TODO: not used after all, but useful to know:
 /// <reference path="../../../../../../lib/jquery-2.1.3/jquery.d.ts"/>
 /// <amd-dependency path="jquery"/>
 import $ = require( 'jquery' );
 */

import ajax = require( '../../../infrastructure/utilities/ajax' );
import api_elements = require( '../../../domain/metamodel/api/elements' );
import api_queries = require( '../../../domain/metamodel/api/queries' );
import spi_queries = require( '../../../domain/metamodel/spi/queries' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all attribute declarations into a metamodel repository.
 */
export class AttributeDeclLoader implements spi_queries.IAttributeDeclLoader {

    loadAllAttributeDecls( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<any> {
        // TODO
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all attribute types into a metamodel repository.
 */
export class AttributeTypeLoader implements spi_queries.IAttributeTypeLoader {

    loadAllAttributeTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<any> {
        // TODO
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all edge types into a metamodel repository.
 */
export class EdgeTypeLoader implements spi_queries.IEdgeTypeLoader {

    loadAllEdgeTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<any> {
        // TODO
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all package dependencies into a metamodel repository.
 */
export class PackageDependencyLoader implements spi_queries.IPackageDependencyLoader {

    loadAllPackageDependencies( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<any> {
        // TODO
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all packages into a metamodel repository.
 */
export class PackageLoader implements spi_queries.IPackageLoader {

    /**
     * Loads all packages into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllPackages( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<any> {

        // TODO: externally configured host & port
        const url = "http://localhost:8080/grestlerdata/metadata/packages";

        /**
         * Loads one package from its JSON representation.
         * @param pkg parsed JSON for the package.
         */
        var loadPackage = function ( pkg : any ) : void {
            var parentPackageId = pkg.parentPackageId;

            if ( parentPackageId ) {
                var parentPackage = repository.findOptionalPackageById( parentPackageId );
                repository.loadPackage( pkg.id, parentPackage, pkg.name );
            }
            else {
                repository.loadRootPackage( pkg.id );
            }

        };

        /**
         * Loads all packages from their representation as a JSON object.
         * @param pkgsJson parsed JSON for an array of packages.
         */
        var loadPackages = function ( pkgsJson : any ) : void {
            pkgsJson.packages.forEach( loadPackage );
        };

        // Perform the AJAX call and handle the response.
        return ajax.httpGet( url ).then( JSON.parse ).then( loadPackages );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all vertex types into a metamodel repository.
 */
export class VertexTypeLoader implements spi_queries.IVertexTypeLoader {

    loadAllVertexTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<any> {
        // TODO
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

