//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/impl/module
 */

import api_queries = require( '../api/queries' )
import queries = require( './queries' )
import spi_queries = require( '../spi/queries' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only metamodel repository. */
var theMetamodelRepository : api_queries.IMetamodelRepository;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export var metamodelImplModule = {

    /**
     * Creates or returns the one and only page selection, loading it when first requested.
     * @returns {PageSelection} the page selection.
     */
    provideMetamodelRepository: function provideMetamodelRepository(
        packageLoader : spi_queries.IPackageLoader,
        packageDependencyLoader : spi_queries.IPackageDependencyLoader,
        attributeTypeLoader : spi_queries.IAttributeTypeLoader,
        vertexTypeLoader : spi_queries.IVertexTypeLoader,
        edgeTypeLoader : spi_queries.IEdgeTypeLoader,
        attributeDeclLoader : spi_queries.IAttributeDeclLoader
    ) : api_queries.IMetamodelRepository {

        // Create the page selection first time through.
        if ( theMetamodelRepository == null ) {
            theMetamodelRepository = new queries.MetamodelRepository(
                packageLoader,
                packageDependencyLoader,
                attributeTypeLoader,
                vertexTypeLoader,
                edgeTypeLoader,
                attributeDeclLoader
            );
        }

        return theMetamodelRepository;

    }

};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

