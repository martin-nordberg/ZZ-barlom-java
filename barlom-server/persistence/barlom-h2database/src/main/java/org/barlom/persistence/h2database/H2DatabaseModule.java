//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database;

import dagger.Module;
import dagger.Provides;
import org.barlom.domain.metamodel.spi.commands.IMetamodelCommandWriterFactory;
import org.barlom.domain.metamodel.spi.queries.IAttributeDeclLoader;
import org.barlom.domain.metamodel.spi.queries.IAttributeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IDirectedEdgeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IPackageDependencyLoader;
import org.barlom.domain.metamodel.spi.queries.IPackageLoader;
import org.barlom.domain.metamodel.spi.queries.IUndirectedEdgeTypeLoader;
import org.barlom.domain.metamodel.spi.queries.IVertexTypeLoader;
import org.barlom.infrastructure.utilities.functions.IAction;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.h2database.api.actions.DataDumpAction;
import org.barlom.persistence.h2database.api.commands.MetamodelCommandWriterFactory;
import org.barlom.persistence.h2database.api.queries.AttributeDeclLoader;
import org.barlom.persistence.h2database.api.queries.AttributeTypeLoader;
import org.barlom.persistence.h2database.api.queries.DirectedEdgeTypeLoader;
import org.barlom.persistence.h2database.api.queries.PackageDependencyLoader;
import org.barlom.persistence.h2database.api.queries.PackageLoader;
import org.barlom.persistence.h2database.api.queries.UndirectedEdgeTypeLoader;
import org.barlom.persistence.h2database.api.queries.VertexTypeLoader;
import org.barlom.persistence.h2database.impl.H2DataSource;

import javax.inject.Named;

/**
 * Dagger module providing H2 data sources..
 */
@Module( library = true )
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
     * Provides a data dump action for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed data dump action.
     */
    @Provides
    @Named( "DataDumpAction" )
    public IAction provideDataDumpAction( IDataSource dataSource ) {
        return new DataDumpAction( dataSource );
    }

    /**
     * Provides an H2 data source.
     *
     * @return the newly created data source.
     */
    @Provides
    public IDataSource provideDataSource() {
        // TODO: make the data source name configurable in the injection
        return new H2DataSource( "steamflake" );
    }

    /**
     * Provides a directed edge type loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed edge type loader.
     */
    @Provides
    public IDirectedEdgeTypeLoader provideDirectedEdgeTypeLoader( IDataSource dataSource ) {
        return new DirectedEdgeTypeLoader( dataSource );
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
     * Provides an undirected edge type loader for H2.
     *
     * @param dataSource the H2 data source.
     *
     * @return the constructed edge type loader.
     */
    @Provides
    public IUndirectedEdgeTypeLoader provideUndirectedEdgeTypeLoader( IDataSource dataSource ) {
        return new UndirectedEdgeTypeLoader( dataSource );
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
