//
// (C) Copyright 2014-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.dbutilities.api;

import javax.sql.DataSource;

/**
 * Auto-closeable data source.
 */
public interface IDataSource
    extends AutoCloseable, DataSource {

    @Override
    void close();

    /**
     * @return the name of this data source.
     */
    String getName();

    /**
     * Creates a new connection from this data source.
     *
     * @return the newly opened connection.
     */
    IConnection openConnection();

}
