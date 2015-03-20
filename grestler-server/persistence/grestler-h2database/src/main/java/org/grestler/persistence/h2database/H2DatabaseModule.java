//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database;

import dagger.Module;
import dagger.Provides;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriterFactory;
import org.grestler.domain.metamodel.spi.queries.IAttributeDeclLoader;
import org.grestler.domain.metamodel.spi.queries.IAttributeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IEdgeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IPackageDependencyLoader;
import org.grestler.domain.metamodel.spi.queries.IPackageLoader;
import org.grestler.domain.metamodel.spi.queries.IVertexTypeLoader;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.api.commands.MetamodelCommandWriterFactory;
import org.grestler.persistence.h2database.api.queries.AttributeDeclLoader;
import org.grestler.persistence.h2database.api.queries.AttributeTypeLoader;
import org.grestler.persistence.h2database.api.queries.EdgeTypeLoader;
import org.grestler.persistence.h2database.api.queries.PackageDependencyLoader;
import org.grestler.persistence.h2database.api.queries.PackageLoader;
import org.grestler.persistence.h2database.api.queries.VertexTypeLoader;
import org.grestler.persistence.h2database.impl.H2DataSource;

/**
 * Dagger module providing H2 data sources..
 */
@Module(
    library = true )
public class H2DatabaseModule {

    /**
     * Provides an attribute declaration loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed attribute declaration loader.
     */
    @Provides
    public IAttributeDeclLoader provideAttributeDeclLoader( IDataSource dataSource ) {
        return new AttributeDeclLoader( dataSource );
    }

    /**
     * Provides an attribute type loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed attribute loader.
     */
    @Provides
    public IAttributeTypeLoader provideAttributeTypeLoader( IDataSource dataSource ) {
        return new AttributeTypeLoader( dataSource );
    }

    /**
     * Provides an H2 data source.
     *
     * @return the newly created data source.
     */
    @Provides
    public IDataSource provideDataSource() {
        // TODO: make the data source name configurable in the injection
        return new H2DataSource( "tmp" );
    }

    /**
     * Provides a edge type loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed edge type loader.
     */
    @Provides
    public IEdgeTypeLoader provideEdgeTypeLoader( IDataSource dataSource ) {
        return new EdgeTypeLoader( dataSource );
    }

    /**
     * Provides a factory for command writers.
     *
     * @param dataSource the data source to write to.
     *
     * @return the constructed factory.
     */
    @Provides
    public IMetamodelCommandWriterFactory provideMetamodelCommandWriterFactory( IDataSource dataSource ) {
        return new MetamodelCommandWriterFactory( dataSource );
    }

    /**
     * Provides a package dependency loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed package dependency loader.
     */
    @Provides
    public IPackageDependencyLoader providePackageDependencyLoader( IDataSource dataSource ) {
        return new PackageDependencyLoader( dataSource );
    }

    /**
     * Provides a package loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed package loader.
     */
    @Provides
    public IPackageLoader providePackageLoader( IDataSource dataSource ) {
        return new PackageLoader( dataSource );
    }

    /**
     * Provides a vertex type loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed vertex type loader.
     */
    @Provides
    public IVertexTypeLoader provideVertexTypeLoader( IDataSource dataSource ) {
        return new VertexTypeLoader( dataSource );
    }

}