//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel;

import dagger.Module;
import dagger.Provides;
import org.barlom.domain.metamodel.api.commands.IMetamodelCommandFactory;
import org.barlom.domain.metamodel.api.queries.IMetamodelRepository;
import org.barlom.domain.metamodel.impl.commands.MetamodelCommandFactory;
import org.barlom.domain.metamodel.impl.queries.MetamodelRepository;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriterFactory;
import org.barlom.domain.metamodel.spi.queries.IAttributeDeclLoader;
import org.barlom.domain.metamodel.spi.queries.IAttributeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IDirectedEdgeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.barlom.domain.metamodel.spi.queries.IPackageDependencyLoader;
import org.barlom.domain.metamodel.spi.queries.IPackageLoader;
import org.barlom.domain.metamodel.spi.queries.IUndirectedEdgeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IVertexTypeLoader;

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
     * @param packageLoader            the source for loading packages.
     * @param packageDependencyLoader  the source for loading package dependencies.
     * @param attributeTypeLoader      the source for loading attribute types.
     * @param vertexTypeLoader         the source for loading vertex types.
     * @param directedEdgeTypeLoader   the source for loading directed edge types.
     * @param undirectedEdgeTypeLoader the source for loading undirected edge types.
     * @param attributeDeclLoader      the source for loading attribute declarations.
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
        IDirectedEdgeTypeLoader directedEdgeTypeLoader,
        IUndirectedEdgeTypeLoader undirectedEdgeTypeLoader,
        IAttributeDeclLoader attributeDeclLoader
    ) {
        return new MetamodelRepository(
            packageLoader,
            packageDependencyLoader,
            attributeTypeLoader,
            vertexTypeLoader,
            directedEdgeTypeLoader,
            undirectedEdgeTypeLoader,
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
        IMetamodelRepository metamodelRepository, IMetamodelCommandWriterFactory metamodelCommandWriterFactory
    ) {
        return new MetamodelCommandFactory(
            (IMetamodelRepositorySpi) metamodelRepository, metamodelCommandWriterFactory
        );
    }
}
