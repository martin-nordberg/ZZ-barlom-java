//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.instrumentation;

import org.apache.logging.log4j.Logger;

/**
 * Timer that logs its duration from creation to closing.
 */
public class OperationTimeLogger
    implements AutoCloseable {

    /**
     * Constructs a new operation time logger that will log a given message with the elapsed time once it is closed.
     *
     * @param log     the logger to log to.
     * @param message the message to log - must include an "{}" substring where the time should go.
     */
    public OperationTimeLogger(
        Logger log, String message
    ) {
        this.log = log;
        this.message = message;
        this.startTime = System.nanoTime();
    }

    @Override
    public void close() {
        long elapsedTime = System.nanoTime() - this.startTime;

        String elapsedTimeStr;
        if ( elapsedTime < 1000L ) {
            elapsedTimeStr = elapsedTime + " ns";
        }
        else if ( elapsedTime < 1000000L ) {
            elapsedTimeStr = String.format( "%1$.2f", (double) elapsedTime / 1000.0D ) + " us";
        }
        else if ( elapsedTime < 1000000000L ) {
            elapsedTimeStr = String.format( "%1$.2f", (double) elapsedTime / 1000000.0D ) + " ms";
        }
        else {
            elapsedTimeStr = String.format( "%1$.2f", (double) elapsedTime / 1000000000.0D ) + " s";
        }

        this.log.info( this.message, elapsedTimeStr );
    }

    private final Logger log;

    private final String message;

    private final long startTime;

}
