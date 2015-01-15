//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database;

import dagger.Module;
import dagger.Provides;
import org.grestler.dbutilities.IDataSource;
import org.grestler.h2database.datasource.H2DataSource;
import org.grestler.h2database.queries.elements.EdgeTypeLoader;
import org.grestler.h2database.queries.elements.PackageLoader;
import org.grestler.h2database.queries.elements.VertexTypeLoader;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;
import org.grestler.metamodel.spi.elements.IPackageLoader;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;

/**
 * Dagger module providing H2 data sources..
 */
@Module(
    library = true
)
public class H2DatabaseModule {

    /**
     * Provides an H2 data source.
     * @return the newly created data source.
     */
    @Provides
    public IDataSource provideDataSource() {
        return new H2DataSource();
    }

    /**
     * Provides a package loader for H2.
     * @param dataSource the H2 data source.
     * @return the constructed package loader.
     */
    @Provides
    public IPackageLoader providePackageLoader( IDataSource dataSource ) {
        return new PackageLoader( dataSource );
    }

    /**
     * Provides a vertex type loader for H2.
     * @param dataSource the H2 data source.
     * @return the constructed vertex type loader.
     */
    @Provides
    public IVertexTypeLoader provideVertexTypeLoader( IDataSource dataSource ) {
        return new VertexTypeLoader( dataSource );
    }

    /**
     * Provides a edge type loader for H2.
     * @param dataSource the H2 data source.
     * @return the constructed edge type loader.
     */
    @Provides
    public IEdgeTypeLoader provideEdgeTypeLoader( IDataSource dataSource ) {
        return new EdgeTypeLoader( dataSource );
    }

}
