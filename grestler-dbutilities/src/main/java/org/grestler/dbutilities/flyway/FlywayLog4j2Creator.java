//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.dbutilities.flyway;

import org.flywaydb.core.internal.util.logging.Log;
import org.flywaydb.core.internal.util.logging.LogCreator;
import org.grestler.dbutilities.flyway.FlywayLog4j2Adapter;

/**
 * Flyway logging adapter factory.
 */
public class FlywayLog4j2Creator
    implements LogCreator {

    @Override
    public Log createLogger( Class<?> clazz ) {
        return new FlywayLog4j2Adapter( clazz );
    }

}
