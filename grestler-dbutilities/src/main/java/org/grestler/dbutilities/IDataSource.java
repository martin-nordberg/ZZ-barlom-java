package org.grestler.dbutilities;

import javax.sql.DataSource;

/**
 * Auto-closeable data source.
 */
public interface IDataSource
    extends AutoCloseable, DataSource {

}
