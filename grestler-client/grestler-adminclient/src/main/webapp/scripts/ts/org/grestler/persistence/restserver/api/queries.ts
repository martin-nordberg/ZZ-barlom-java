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

import api_elements = require( '../../../domain/metamodel/api/elements' );
import api_queries = require( '../../../domain/metamodel/api/queries' );
import spi_queries = require( '../../../domain/metamodel/spi/queries' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Make an AJAX GET call returning a promise.  TODO: Move to utility class
 * @param url the URL to get.
 * @returns {Promise}
 */
function httpGet( url : string ) : Promise<string> {

    // Return a new promise.
    return new Promise<string>(
        function ( resolve : ( value? : string ) => void, reject : ( error? : any ) => void ) {

            // Do the usual XHR stuff
            var req = new XMLHttpRequest();
            req.open( 'GET', url );

            req.onload = function () {
                // Check the status
                if ( req.status == 200 ) {
                    // Resolve the promise with the response text
                    resolve( req.response );
                }
                else {
                    // Otherwise reject with the status text
                    // which will hopefully be a meaningful error
                    reject( Error( req.statusText ) );
                }
            };

            // Handle network errors
            req.onerror = function () {
                reject( Error( "Network Error" ) );
            };

            // Make the request
            req.send();

        }
    );

}

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
        var url = "http://localhost:8080/grestlerdata/metadata/packages";

        /**
         * Loads one package from its JSON representation.   -- TODO: find or load parent package before child
         * @param pkg parsed JSON for the package.           -- TODO: load root package
         */
        var loadPackage = function ( pkg : any ) : void {
            repository.loadPackage( pkg.id, null, pkg.name );
        };

        /**
         * Loads all packages from their representation as a JSON object.
         * @param pkgsJson
         */
        var loadPackages = function ( pkgsJson : any ) : void {
            pkgsJson.packages.forEach( loadPackage );
        };

        /**
         * Parses a JSON string to an object.  -- TODO: centralize this simple utility
         * @param jsonStr the string to parse.
         * @returns {any} the parsed object.
         */
        var parseJson = function ( jsonStr : string ) : any {
            return JSON.parse( jsonStr );
        };

        // Perform the AJAX call and handle the response.
        return httpGet( url ).then( parseJson ).then( loadPackages );

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

