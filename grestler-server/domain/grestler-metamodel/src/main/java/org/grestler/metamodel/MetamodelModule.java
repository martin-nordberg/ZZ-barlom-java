//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel;

import dagger.Module;
import dagger.Provides;
import org.grestler.metamodel.api.commands.IMetamodelCommandFactory;
import org.grestler.metamodel.api.queries.IMetamodelRepository;
import org.grestler.metamodel.impl.commands.MetamodelCommandFactory;
import org.grestler.metamodel.impl.queries.MetamodelRepository;
import org.grestler.metamodel.spi.commands.IMetamodelCommandWriterFactory;
import org.grestler.metamodel.spi.queries.IAttributeDeclLoader;
import org.grestler.metamodel.spi.queries.IAttributeTypeLoader;
import org.grestler.metamodel.spi.queries.IEdgeTypeLoader;
import org.grestler.metamodel.spi.queries.IPackageDependencyLoader;
import org.grestler.metamodel.spi.queries.IPackageLoader;
import org.grestler.metamodel.spi.queries.IVertexTypeLoader;

import javax.inject.Singleton;

/**
 * Dagger dependency injection module.
 */
@SuppressWarnings( "ClassNamePrefixedWithPackageName" )
@Module(
    complete = false,
    library = true )
public class MetamodelModule {

    /**
     * Constructs a new metamodel repository.
     *
     * @param packageLoader           the source for loading packages.
     * @param packageDependencyLoader the source for loading package dependencies.
     * @param attributeTypeLoader     the source for loading attribute types.
     * @param vertexTypeLoader        the source for loading vertex types.
     * @param edgeTypeLoader          the source for loading edge types.
     * @param attributeDeclLoader     the source for loading attribute declarations.
     *
     * @return the newly created repository.
     */
    @Provides
    @Singleton
    public IMetamodelRepository provideMetamodel(
        IPackageLoader packageLoader,
        IPackageDependencyLoader packageDependencyLoader,
        IAttributeTypeLoader attributeTypeLoader,
        IVertexTypeLoader vertexTypeLoader,
        IEdgeTypeLoader edgeTypeLoader,
        IAttributeDeclLoader attributeDeclLoader
    ) {
        return new MetamodelRepository(
            packageLoader,
            packageDependencyLoader,
            attributeTypeLoader,
            vertexTypeLoader,
            edgeTypeLoader,
            attributeDeclLoader
        );
    }

    /**
     * Constructs a new metamodel command factory.
     *
     * @param metamodelRepository           the repository the commands will act upon.
     * @param metamodelCommandWriterFactory factory for associated command writers.
     *
     * @return the newly constructed factory.
     */
    @Provides
    public IMetamodelCommandFactory provideMetamodelCommandFactory(
        IMetamodelRepository metamodelRepository,
        IMetamodelCommandWriterFactory metamodelCommandWriterFactory
    ) {
        return new MetamodelCommandFactory( metamodelRepository, metamodelCommandWriterFactory );
    }
}
