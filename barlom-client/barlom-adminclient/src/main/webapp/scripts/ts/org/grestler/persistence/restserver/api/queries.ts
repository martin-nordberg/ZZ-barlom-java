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
 * Service for loading all directed edge types into a metamodel repository.
 */
export class DirectedEdgeTypeLoader implements spi_queries.IDirectedEdgeTypeLoader {

    loadAllDirectedEdgeTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {

        // TODO: externally configured host & port
        const url = "http://localhost:8080/grestlerdata/metadata/directededgetypes";

        /**
         * Loads one directed edge type from its JSON representation.
         * @param etJson parsed JSON for the edge type.
         */
        var loadDirectedEdgeType = function ( etJson : any ) : api_elements.IDirectedEdgeType {

            var parentPackage = repository.findPackageById( etJson.parentPackageId );
            var superTypeId = etJson.superTypeId;
            var abstractness = api_elements.abstractnessFromString( etJson.abstractness );
            var cyclicity = api_elements.cyclicityFromString( etJson.cyclicity );
            var multiEdgedness = api_elements.multiEdgednessFromString( etJson.multiEdgedness );
            var selfLooping = api_elements.selfLoopingFromString( etJson.selfLooping );

            if ( superTypeId ) {
                var superType = repository.findOptionalDirectedEdgeTypeById( superTypeId );

                if ( superType ) {
                    var tailVertexType = repository.findVertexTypeById( etJson.tailVertexTypeId );
                    var headVertexType = repository.findVertexTypeById( etJson.headVertexTypeId );

                    return repository.loadDirectedEdgeType(
                        etJson.id,
                        parentPackage,
                        etJson.name,
                        superType,
                        abstractness,
                        cyclicity,
                        multiEdgedness,
                        selfLooping,
                        tailVertexType,
                        headVertexType,
                        etJson.tailRoleName,
                        etJson.headRoleName,
                        etJson.minTailOutDegree,
                        etJson.maxTailOutDegree,
                        etJson.minHeadInDegree,
                        etJson.maxHeadInDegree
                    );
                }
                else {
                    return null;
                }
            }
            else {
                return repository.loadRootDirectedEdgeType( etJson.id, parentPackage );
            }

        };

        /**
         * Loads all directed edge types from their representation as a JSON object.
         * @param etsJson parsed JSON for an array of edge types.
         */
        var loadDirectedEdgeTypes = function ( etsJson : any ) : values.ENothing {

            // Keep track of edge types that fail to create on this round becasue their parents are later in the list.
            var etsJsonToDo = {
                directedEdgeTypes: []
            };

            // Create each edge type from the JSON
            etsJson.directedEdgeTypes.forEach(
                function ( etJson : any ) : void {
                    var et = loadDirectedEdgeType( etJson );

                    // If not created, try again in next round (super type presumably later in the list).
                    if ( et == null ) {
                        etsJsonToDo.directedEdgeTypes.push( etJson );
                    }
                }
            );

            // Done when all edge types created.
            if ( etsJsonToDo.directedEdgeTypes.length == 0 ) {
                return values.nothing;
            }

            // Error if no progress
            if ( etsJsonToDo.directedEdgeTypes.length === etsJson.directedEdgeTypes.length ) {
                throw new Error( "Failed to load all directed edge types." );
            }

            // Recursively create whatever edge types remain.
            return loadDirectedEdgeTypes( etsJsonToDo );

        };

        // Perform the AJAX call and handle the response.
        return ajax.httpGet( url ).then( JSON.parse ).then( loadDirectedEdgeTypes );

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
            pkgsJson.packages.forEach(
                function ( pkgJson : any ) : void {
                    var pkg = loadPackage( pkgJson );

                    // If not created, try again in next round (parent presumably later in the list).
                    if ( pkg == null ) {
                        pkgsJsonToDo.packages.push( pkgJson );
                    }
                }
            );

            // Done when all packages created.
            if ( pkgsJsonToDo.packages.length == 0 ) {
                return values.nothing;
            }

            // Error if no progress
            if ( pkgsJsonToDo.packages.length === pkgsJson.packages.length ) {
                throw new Error( "Failed to load all packages." );
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
 * Service for loading all undirected edge types into a metamodel repository.
 */
export class UndirectedEdgeTypeLoader implements spi_queries.IUndirectedEdgeTypeLoader {

    loadAllUndirectedEdgeTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {

        // TODO: externally configured host & port
        const url = "http://localhost:8080/grestlerdata/metadata/undirectededgetypes";

        /**
         * Loads one undirected edge type from its JSON representation.
         * @param etJson parsed JSON for the edge type.
         */
        var loadUndirectedEdgeType = function ( etJson : any ) : api_elements.IUndirectedEdgeType {

            var parentPackage = repository.findPackageById( etJson.parentPackageId );
            var superTypeId = etJson.superTypeId;
            var abstractness = api_elements.abstractnessFromString( etJson.abstractness );
            var cyclicity = api_elements.cyclicityFromString( etJson.cyclicity );
            var multiEdgedness = api_elements.multiEdgednessFromString( etJson.multiEdgedness );
            var selfLooping = api_elements.selfLoopingFromString( etJson.selfLooping );

            if ( superTypeId ) {
                var superType = repository.findOptionalUndirectedEdgeTypeById( superTypeId );

                if ( superType ) {
                    var vertexType = repository.findVertexTypeById( etJson.vertexTypeId );

                    return repository.loadUndirectedEdgeType(
                        etJson.id,
                        parentPackage,
                        etJson.name,
                        superType,
                        abstractness,
                        cyclicity,
                        multiEdgedness,
                        selfLooping,
                        vertexType,
                        etJson.minDegree,
                        etJson.maxDegree
                    );
                }
                else {
                    return null;
                }
            }
            else {
                return repository.loadRootUndirectedEdgeType( etJson.id, parentPackage );
            }

        };

        /**
         * Loads all undirected edge types from their representation as a JSON object.
         * @param etsJson parsed JSON for an array of edge types.
         */
        var loadUndirectedEdgeTypes = function ( etsJson : any ) : values.ENothing {

            // Keep track of edge types that fail to create on this round becasue their parents are later in the list.
            var etsJsonToDo = {
                undirectedEdgeTypes: []
            };

            // Create each edge type from the JSON
            etsJson.undirectedEdgeTypes.forEach(
                function ( etJson : any ) : void {
                    var et = loadUndirectedEdgeType( etJson );

                    // If not created, try again in next round (super type presumably later in the list).
                    if ( et == null ) {
                        etsJsonToDo.undirectedEdgeTypes.push( etJson );
                    }
                }
            );

            // Done when all vertex types created.
            if ( etsJsonToDo.undirectedEdgeTypes.length == 0 ) {
                return values.nothing;
            }

            // Error if no progress
            if ( etsJsonToDo.undirectedEdgeTypes.length === etsJson.undirectedEdgeTypes.length ) {
                throw new Error( "Failed to load all undirected edge types." );
            }

            // Recursively create whatever vertex types remain.
            return loadUndirectedEdgeTypes( etsJsonToDo );

        };

        // Perform the AJAX call and handle the response.
        return ajax.httpGet( url ).then( JSON.parse ).then( loadUndirectedEdgeTypes );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service for loading all vertex types into a metamodel repository.
 */
export class VertexTypeLoader implements spi_queries.IVertexTypeLoader {

    loadAllVertexTypes( repository : spi_queries.IMetamodelRepositorySpi ) : Promise<values.ENothing> {

        // TODO: externally configured host & port
        const url = "http://localhost:8080/grestlerdata/metadata/vertextypes";

        /**
         * Loads one vertex type from its JSON representation.
         * @param vtJson parsed JSON for the vertex type.
         */
        var loadVertexType = function ( vtJson : any ) : api_elements.IVertexType {

            var parentPackage = repository.findPackageById( vtJson.parentPackageId );
            var superTypeId = vtJson.superTypeId;
            var abstractness = api_elements.abstractnessFromString( vtJson.abstractness );

            if ( superTypeId ) {
                var superType = repository.findOptionalVertexTypeById( superTypeId );

                if ( superType ) {
                    return repository.loadVertexType( vtJson.id, parentPackage, vtJson.name, superType, abstractness );
                }
                else {
                    return null;
                }
            }
            else {
                return repository.loadRootVertexType( vtJson.id, parentPackage );
            }

        };

        /**
         * Loads all vertex types from their representation as a JSON object.
         * @param vtsJson parsed JSON for an array of vertex types.
         */
        var loadVertexTypes = function ( vtsJson : any ) : values.ENothing {

            // Keep track of vertex types that fail to create on this round becasue their parents are later in the list.
            var vtsJsonToDo = {
                vertexTypes: []
            };

            // Create each vertex type from the JSON
            vtsJson.vertexTypes.forEach(
                function ( vtJson : any ) : void {
                    var vt = loadVertexType( vtJson );

                    // If not created, try again in next round (super type presumably later in the list).
                    if ( vt == null ) {
                        vtsJsonToDo.vertexTypes.push( vtJson );
                    }
                }
            );

            // Done when all vertex types created.
            if ( vtsJsonToDo.vertexTypes.length == 0 ) {
                return values.nothing;
            }

            // Error if no progress
            if ( vtsJsonToDo.vertexTypes.length === vtsJson.vertexTypes.length ) {
                throw new Error( "Failed to load all vertex types." );
            }

            // Recursively create whatever vertex types remain.
            return loadVertexTypes( vtsJsonToDo );

        };

        // Perform the AJAX call and handle the response.
        return ajax.httpGet( url ).then( JSON.parse ).then( loadVertexTypes );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
