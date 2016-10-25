//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.dbutilities.spi;

import org.barlom.persistence.dbutilities.api.IResultSet;

/**
 * Service provider interface for a result set.
 */
@SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
public interface IResultSetSpi
    extends AutoCloseable, IResultSet {

    /**
     * @throws org.barlom.persistence.dbutilities.api.DatabaseException if closing fails.
     */
    @Override
    void close();

    @SuppressWarnings( "BooleanMethodNameMustStartWithQuestion" )
    boolean next();

}
