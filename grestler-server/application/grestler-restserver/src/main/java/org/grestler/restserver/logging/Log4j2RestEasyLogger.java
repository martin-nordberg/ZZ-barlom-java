//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.logging;

import org.apache.logging.log4j.LogManager;
import org.jboss.resteasy.logging.Logger;

import java.text.MessageFormat;

/**
 * RESTEasy logger for Apache Log4j2 (to be hacked into RESTEasy by reflection).
 */
public class Log4j2RestEasyLogger
    extends Logger {

    public Log4j2RestEasyLogger( String classname ) {
        delegate = LogManager.getLogger( classname );
    }

    @Override
    public void debug( String message ) {
        if ( !delegate.isDebugEnabled() ) {
            return;
        }
        delegate.debug( message );
    }

    @Override
    public void debug( String message, Object... params ) {
        if ( !delegate.isDebugEnabled() ) {
            return;
        }
        String msg = MessageFormat.format( message, params );
        delegate.debug( msg );
    }

    @Override
    public void debug( String message, Throwable error ) {
        if ( !isDebugEnabled() ) {
            return;
        }
        delegate.debug( message, error );
    }

    @Override
    public void error( String message ) {
        delegate.warn( message );
    }

    @Override
    public void error( String message, Object... params ) {
        String msg = MessageFormat.format( message, params );
        delegate.error( msg );
    }

    @Override
    public void error( String message, Throwable error ) {
        delegate.warn( message, error );
    }

    @Override
    public void info( String message ) {
        if ( !( delegate.isInfoEnabled() ) ) {
            return;
        }
        delegate.info( message );
    }

    @Override
    public void info( String message, Object... params ) {
        if ( !delegate.isInfoEnabled() ) {
            return;
        }
        String msg = MessageFormat.format( message, params );
        delegate.info( msg );
    }

    @Override
    public void info( String message, Throwable error ) {
        if ( !delegate.isInfoEnabled() ) {
            return;
        }
        delegate.info( message, error );
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return delegate.isTraceEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void trace( String message ) {
        if ( !delegate.isTraceEnabled() ) {
            return;
        }
        delegate.trace( message );
    }

    @Override
    public void trace( String message, Object... params ) {
        if ( !delegate.isTraceEnabled() ) {
            return;
        }
        String msg = MessageFormat.format( message, params );
        delegate.trace( msg );
    }

    @Override
    public void trace( String message, Throwable error ) {
        if ( !delegate.isTraceEnabled() ) {
            return;
        }
        delegate.trace( message, error );
    }

    @Override
    public void warn( String message ) {
        delegate.warn( message );
    }

    @Override
    public void warn( String message, Object... params ) {
        String msg = MessageFormat.format( message, params );
        delegate.warn( msg );
    }

    @Override
    public void warn( String message, Throwable error ) {
        delegate.warn( message, error );
    }

    private transient org.apache.logging.log4j.Logger delegate;
}
