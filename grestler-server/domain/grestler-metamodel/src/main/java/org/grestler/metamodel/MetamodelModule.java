//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel;

import dagger.Module;
import dagger.Provides;
import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.metamodel.impl.MetamodelRepository;
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;
import org.grestler.metamodel.spi.elements.IPackageLoader;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;

import javax.inject.Singleton;

/**
 * Dagger dependency injection module.
 */
@Module(
    complete = false,
    library = true )
public class MetamodelModule {

    /**
     * Constructs a new metamodel repository.
     *
     * @param packageLoader       the source for loading packages.
     * @param attributeTypeLoader the source for loading attribute types.
     * @param vertexTypeLoader    the source for loading vertex types.
     * @param edgeTypeLoader      the source for loading edge types.
     *
     * @return the newly created repository.
     */
    @Provides
    @Singleton
    public IMetamodelRepository provideMetamodel(
        IPackageLoader packageLoader,
        IAttributeTypeLoader attributeTypeLoader,
        IVertexTypeLoader vertexTypeLoader,
        IEdgeTypeLoader edgeTypeLoader
    ) {
        return new MetamodelRepository( packageLoader, attributeTypeLoader, vertexTypeLoader, edgeTypeLoader );
    }

}
