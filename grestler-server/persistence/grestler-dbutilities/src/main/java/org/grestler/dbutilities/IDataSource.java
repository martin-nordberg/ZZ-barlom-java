//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities;

import javax.sql.DataSource;

/**
 * Auto-closeable data source.
 */
public interface IDataSource
    extends AutoCloseable, DataSource {

}
