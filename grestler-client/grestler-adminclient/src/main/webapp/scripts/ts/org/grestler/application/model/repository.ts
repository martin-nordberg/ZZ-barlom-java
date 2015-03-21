//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/application/model/repository
 */

import api_queries = require( '../../domain/metamodel/api/queries' );
import impl_queries = require( '../../domain/metamodel/impl/queries' );
import restserver_api_queries = require( '../../persistence/restserver/api/queries' );
import spi_queries = require( '../../domain/metamodel/spi/queries' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The singleton repository for the metamodel being viewed
 */
export var metamodelRepository : api_queries.IMetamodelRepository;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Creates the metamodel repository.
 * TODO: dependency injection needed
 */
export function loadMetamodelRepository() : void {

    var packageLoader : spi_queries.IPackageLoader;
    var packageDependencyLoader : spi_queries.IPackageDependencyLoader;
    var attributeTypeLoader : spi_queries.IAttributeTypeLoader;
    var vertexTypeLoader : spi_queries.IVertexTypeLoader;
    var edgeTypeLoader : spi_queries.IEdgeTypeLoader;
    var attributeDeclLoader : spi_queries.IAttributeDeclLoader;

    packageLoader = new restserver_api_queries.PackageLoader();
    packageDependencyLoader = new restserver_api_queries.PackageDependencyLoader();
    attributeTypeLoader = new restserver_api_queries.AttributeTypeLoader();
    vertexTypeLoader = new restserver_api_queries.VertexTypeLoader();
    edgeTypeLoader = new restserver_api_queries.EdgeTypeLoader();
    attributeDeclLoader = new restserver_api_queries.AttributeDeclLoader();

    metamodelRepository = new impl_queries.MetamodelRepository(
        packageLoader,
        packageDependencyLoader,
        attributeTypeLoader,
        vertexTypeLoader,
        edgeTypeLoader,
        attributeDeclLoader
    );

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

