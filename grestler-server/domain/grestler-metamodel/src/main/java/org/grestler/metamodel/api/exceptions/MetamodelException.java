//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.exceptions;

import org.apache.logging.log4j.Logger;
import org.grestler.utilities.exceptions.LoggedException;

/**
 * General exception originating from the metamodel subsystem..
 */
public class MetamodelException
    extends LoggedException {

    public MetamodelException( Logger log, String message ) {
        super( log, message );
    }

    public MetamodelException( Logger log, String message, Throwable cause ) {
        super( log, message, cause );
    }

}
