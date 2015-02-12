//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel;

import dagger.Module;
import dagger.Provides;
import org.grestler.metamodel.api.metamodel.IMetamodelRepository;
import org.grestler.metamodel.impl.metamodel.MetamodelRepository;
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader;
import org.grestler.metamodel.spi.elements.IAttributeDeclLoader;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;
import org.grestler.metamodel.spi.elements.IPackageDependencyLoader;
import org.grestler.metamodel.spi.elements.IPackageLoader;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;

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

}
