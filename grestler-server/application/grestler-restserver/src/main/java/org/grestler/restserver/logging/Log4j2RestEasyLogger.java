//
// (C) Copyright 2014-2015 Martin E. Nordberg III
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
        this.delegate = LogManager.getLogger( classname );
    }

    @Override
    public void debug( String message ) {
        if ( !this.delegate.isDebugEnabled() ) {
            return;
        }
        this.delegate.debug( message );
    }

    @Override
    public void debug( String message, Object... params ) {
        if ( !this.delegate.isDebugEnabled() ) {
            return;
        }
        String msg = MessageFormat.format( message, params );
        this.delegate.debug( msg );
    }

    @Override
    public void debug( String message, Throwable error ) {
        if ( !this.isDebugEnabled() ) {
            return;
        }
        this.delegate.debug( message, error );
    }

    @Override
    public void error( String message ) {
        this.delegate.warn( message );
    }

    @Override
    public void error( String message, Object... params ) {
        String msg = MessageFormat.format( message, params );
        this.delegate.error( msg );
    }

    @Override
    public void error( String message, Throwable error ) {
        this.delegate.warn( message, error );
    }

    @Override
    public void info( String message ) {
        if ( !this.delegate.isInfoEnabled() ) {
            return;
        }
        this.delegate.info( message );
    }

    @Override
    public void info( String message, Object... params ) {
        if ( !this.delegate.isInfoEnabled() ) {
            return;
        }
        String msg = MessageFormat.format( message, params );
        this.delegate.info( msg );
    }

    @Override
    public void info( String message, Throwable error ) {
        if ( !this.delegate.isInfoEnabled() ) {
            return;
        }
        this.delegate.info( message, error );
    }

    @Override
    public boolean isDebugEnabled() {
        return this.delegate.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.delegate.isTraceEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void trace( String message ) {
        if ( !this.delegate.isTraceEnabled() ) {
            return;
        }
        this.delegate.trace( message );
    }

    @Override
    public void trace( String message, Object... params ) {
        if ( !this.delegate.isTraceEnabled() ) {
            return;
        }
        String msg = MessageFormat.format( message, params );
        this.delegate.trace( msg );
    }

    @Override
    public void trace( String message, Throwable error ) {
        if ( !this.delegate.isTraceEnabled() ) {
            return;
        }
        this.delegate.trace( message, error );
    }

    @Override
    public void warn( String message ) {
        this.delegate.warn( message );
    }

    @Override
    public void warn( String message, Object... params ) {
        String msg = MessageFormat.format( message, params );
        this.delegate.warn( msg );
    }

    @Override
    public void warn( String message, Throwable error ) {
        this.delegate.warn( message, error );
    }

    private final transient org.apache.logging.log4j.Logger delegate;

}
