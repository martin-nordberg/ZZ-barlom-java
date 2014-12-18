//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities;

/**
 * Factory/configurator for defining a data source and initializing it.
 */
public interface IDataSourceDefinition {

    /**
     * Creates a new data source.
     *
     * @return the new data source.
     */
    IDataSource makeDataSource();

}
