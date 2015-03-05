//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.dbutilities.api;

import javax.sql.DataSource;

/**
 * Auto-closeable data source.
 */
public interface IDataSource
    extends AutoCloseable, DataSource {

    @Override
    void close();

    /**
     * Creates a new connection from this data source.
     *
     * @return the newly opened connection.
     */
    IConnection openConnection();

}
