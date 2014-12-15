//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database;

import org.grestler.dbutilities.IDataSource;
import org.grestler.dbutilities.IDataSourceDefinition;
import org.grestler.h2database.datasource.H2DataSource;

/**
 * Data source definition for an H2 back end..
 */
public class H2DataSourceDefinition
    implements IDataSourceDefinition {

    @Override
    public H2DataSource makeDataSource() {
        return new H2DataSource();
    }

}
