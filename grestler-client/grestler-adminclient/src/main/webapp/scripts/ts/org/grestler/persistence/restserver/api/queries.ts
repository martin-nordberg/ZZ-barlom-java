//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/persistence/restserver/api/queries
 */

import ajax = require( '../../../infrastructure/utilities/ajax' );
import api_elements = require( '../../../domain/metamodel/api/elements' );
import api_queries = require( '../../../domain/metamodel/api/queries' );
import spi_queries = require( '../../../domain/metamodel/spi/queries' );
import values = require( '../../../infrastructure/utilities/values' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all attribute declarations into a metamodel repository.
 */
export class AttributeDeclLoader implements spi_queries.IAttributeDeclLoader {

    loadAllAttributeDecls( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {
        return new Promise<values.ENothing>(
            function ( resolve : ( value? : values.ENothing ) => void, reject : ( error? : any ) => void ) {
                resolve( values.nothing );  // TODO
            }
        );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all attribute types into a metamodel repository.
 */
export class AttributeTypeLoader implements spi_queries.IAttributeTypeLoader {

    loadAllAttributeTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {

        // TODO: externally configured host & port
        const url = "http://localhost:8080/grestlerdata/metadata/attributetypes";

        /**
         * Loads one attribute type from its JSON representation.
         * @param attributeType parsed JSON for the attribute type.
         */
        var loadAttributeType = function ( attributeType : any ) : void {
            var parentPackage = repository.findPackageById( attributeType.parentPackageId );
            var dataType = attributeType.dataType;

            switch ( dataType ) {
                case 'BOOLEAN':
                    repository.loadBooleanAttributeType(
                        attributeType.id,
                        parentPackage,
                        attributeType.name,
                        attributeType.defaultValue
                    );
                    break;
                case 'DATETIME':
                    repository.loadDateTimeAttributeType(
                        attributeType.id,
                        parentPackage,
                        attributeType.name,
                        attributeType.minValue ? new Date( attributeType.minValue ) : null,
                        attributeType.maxValue ? new Date( attributeType.maxValue ) : null
                    );
                    break;
                case 'FLOAT64':
                    repository.loadFloat64AttributeType(
                        attributeType.id,
                        parentPackage,
                        attributeType.name,
                        attributeType.minValue ? parseFloat( attributeType.minValue ) : null,
                        attributeType.maxValue ? parseFloat( attributeType.maxValue ) : null,
                        attributeType.defaultValue ? parseFloat( attributeType.defaultValue ) : null
                    );
                    break;
                case 'INTEGER32':
                    repository.loadInteger32AttributeType(
                        attributeType.id,
                        parentPackage,
                        attributeType.name,
                        attributeType.minValue ? parseInt( attributeType.minValue ) : null,
                        attributeType.maxValue ? parseInt( attributeType.maxValue ) : null,
                        attributeType.defaultValue ? parseInt( attributeType.defaultValue ) : null
                    );
                    break;
                case 'STRING':
                    repository.loadStringAttributeType(
                        attributeType.id,
                        parentPackage,
                        attributeType.name,
                        attributeType.minLength ? parseInt( attributeType.minLength ) : null,
                        attributeType.maxLength ? parseInt( attributeType.maxLength ) : null,
                        attributeType.regexPattern
                    );
                    break;
                case 'UUID':
                    repository.loadUuidAttributeType(
                        attributeType.id,
                        parentPackage,
                        attributeType.name
                    );
                    break;
            }
        };

        /**
         * Loads all attribute types from their representation as a JSON object.
         * @param attributeTypesJson parsed JSON for an array of attribute types.
         */
        var loadAttributeTypes = function ( attributeTypesJson : any ) : values.ENothing {
            attributeTypesJson.attributeTypes.forEach( loadAttributeType );
            return values.nothing;
        };

        // Perform the AJAX call and handle the response.
        return ajax.httpGet( url ).then( JSON.parse ).then( loadAttributeTypes );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all edge types into a metamodel repository.
 */
export class EdgeTypeLoader implements spi_queries.IEdgeTypeLoader {

    loadAllEdgeTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {
        return new Promise<values.ENothing>(
            function ( resolve : ( value? : values.ENothing ) => void, reject : ( error? : any ) => void ) {
                resolve( values.nothing );  // TODO
            }
        );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all package dependencies into a metamodel repository.
 */
export class PackageDependencyLoader implements spi_queries.IPackageDependencyLoader {

    loadAllPackageDependencies( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {
        return new Promise<values.ENothing>(
            function ( resolve : ( value? : values.ENothing ) => void, reject : ( error? : any ) => void ) {
                resolve( values.nothing );  // TODO
            }
        );
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
    loadAllPackages( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {

        // TODO: externally configured host & port
        const url = "http://localhost:8080/grestlerdata/metadata/packages";

        /**
         * Loads one package from its JSON representation.
         * @param pkgJson parsed JSON for the package.
         */
        var loadPackage = function ( pkgJson : any ) : api_elements.IPackage {

            var parentPackageId = pkgJson.parentPackageId;

            if ( parentPackageId ) {
                var parentPackage = repository.findOptionalPackageById( parentPackageId );

                if ( parentPackage ) {
                    return repository.loadPackage( pkgJson.id, parentPackage, pkgJson.name );
                }
                else {
                    return null;
                }
            }
            else {
                return repository.loadRootPackage( pkgJson.id );
            }

        };

        /**
         * Loads all packages from their representation as a JSON object.
         * @param pkgsJson parsed JSON for an array of packages.
         */
        var loadPackages = function ( pkgsJson : any ) : values.ENothing {

            // Keep track of packages that fail to create on this round becasue their parents are later in the list.
            var pkgsJsonToDo = {
                packages: []
            };

            // Create each package from the JSON
            pkgsJson.packages.forEach( function( pkgJson : any ) : void {
                var pkg = loadPackage( pkgJson );

                // If not created, try again in next round (parent presumably later in the list).
                if ( pkg == null ) {
                    pkgsJsonToDo.packages.push( pkgJson );
                }
            } );

            // Done when all packages created.
            if ( pkgsJsonToDo.packages.length == 0 ) {
                return values.nothing;
            }

            // Recursively create whatever packages remain.
            return loadPackages( pkgsJsonToDo );

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

    loadAllVertexTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {
        return new Promise<values.ENothing>(
            function ( resolve : ( value? : values.ENothing ) => void, reject : ( error? : any ) => void ) {
                resolve( values.nothing );  // TODO
            }
        );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
