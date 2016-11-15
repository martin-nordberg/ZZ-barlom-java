//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.configuration;

import java.util.List;

/**
 * General purpose configuration settings from properties files.
 */
public interface IConfiguration {

    /**
     * Whether this condifuration has a value for the given key.
     * @param key the key to look for.
     * @return true if there is a value.
     */
    boolean hasValueForKey( String key );

    /**
     * Reads a boolean property for the given key.
     *
     * @param key the name of the property.
     *
     * @return the boolean value (true for "true", "yes", or "1" as the property value).
     */
    @SuppressWarnings( "BooleanMethodNameMustStartWithQuestion" )
    boolean readBoolean( String key );

    /**
     * Reads an integer property for the given key.
     *
     * @param key the name of the property.
     *
     * @return the integer value.
     *
     * @throws NumberFormatException if the property's value is not an integer.
     */
    int readInt( String key );

    /**
     * Reads the property for the given key.
     *
     * @param key the key of the property to read.
     *
     * @return the property value read.
     */
    String readString( String key );

    /**
     * Reads a list of values. The values are keyed by the base key plus ".1", ".2", etc.
     *
     * @param key the base key for the list.
     *
     * @return the list of values read.
     */
    List<String> readStrings( String key );

}
