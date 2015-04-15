//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/impl/module
 */

import restserver_api_queries = require( '../api/queries' )
import spi_queries = require( '../../../domain/metamodel/spi/queries' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export var restserverApiModule = {

    /**
     * Service for loading all attribute declarations into a metamodel repository.
     */
    provideAttributeDeclLoader: function provideAttributeDeclLoader() : spi_queries.IAttributeDeclLoader {
        return new restserver_api_queries.AttributeDeclLoader();
    },

    /**
     * Service for loading all attribute types into a metamodel repository.
     */
    provideAttributeTypeLoader: function provideAttributeTypeLoader() : spi_queries.IAttributeTypeLoader {
        return new restserver_api_queries.AttributeTypeLoader();
    },

    /**
     * Service for loading all edge types into a metamodel repository.
     */
    provideEdgeTypeLoader: function provideEdgeTypeLoader() : spi_queries.IEdgeTypeLoader {
        return new restserver_api_queries.EdgeTypeLoader();
    },

    /**
     * Service for loading all package dependencies into a metamodel repository.
     */
    providePackageDependencyLoader: function providePackageDependencyLoader() : spi_queries.IPackageDependencyLoader {
        return new restserver_api_queries.PackageDependencyLoader();
    },

    /**
     * Service for loading all packages into a metamodel repository.
     */
    providePackageLoader: function providePackageLoader() : spi_queries.IPackageLoader {
        return new restserver_api_queries.PackageLoader();
    },

    /**
     * Service for loading all vertex types into a metamodel repository.
     */
    provideVertexTypeLoader: function provideVertexTypeLoader() : spi_queries.IVertexTypeLoader {
        return new restserver_api_queries.VertexTypeLoader();
    }

};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

