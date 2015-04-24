//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/impl/module
 */

import api_commands = require( '../api/commands' )
import api_queries = require( '../api/queries' )
import impl_commands = require( './commands' )
import impl_queries = require( './queries' )
import spi_commands = require( '../spi/commands' )
import spi_queries = require( '../spi/queries' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only factory for metamodel commands. */
var theMetamodelCommandFactory : api_commands.IMetamodelCommandFactory;

/** The one and only metamodel repository. */
var theMetamodelRepository : api_queries.IMetamodelRepository;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export var metamodelImplModule = {

    /**
     * Creates a command factory for metamodel changes.
     * @param metamodelRepository the repository being controlled.
     * @param metamodelCommandWriterFactory the corresponding persistence factory.
     * @returns {api_commands.IMetamodelCommandFactory} the new command factory.
     */
    provideMetamodelCommandFactory: function provideMetamodelCommandFactory(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi,
        metamodelCommandWriterFactory : spi_commands.IMetamodelCommandWriterFactory
    ) : api_commands.IMetamodelCommandFactory {
        if ( theMetamodelCommandFactory == null ) {
            theMetamodelCommandFactory = new impl_commands.MetamodelCommandFactory( metamodelRepository, metamodelCommandWriterFactory );

        return theMetamodelCommandFactory;        }
    },

    /**
     * Creates or returns the one and only metamodel repository, loading it when first requested.
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

        // Create the metamodel repository first time through.
        if ( theMetamodelRepository == null ) {
            theMetamodelRepository = new impl_queries.MetamodelRepository(
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

