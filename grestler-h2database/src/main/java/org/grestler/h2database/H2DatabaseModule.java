//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database;

import dagger.Module;
import dagger.Provides;
import org.grestler.dbutilities.IDataSource;
import org.grestler.h2database.datasource.H2DataSource;

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


}
