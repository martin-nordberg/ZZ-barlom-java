//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * General purpose configuration settings from environment variables.
 */
public class SystemEnvironmentConfiguration
    implements IConfiguration {

    /**
     * Constructs a configuration object that will read environment variables.
     */
    public SystemEnvironmentConfiguration() {
    }

    @Override
    public boolean hasValueForKey( String key ) {
        return System.getenv().containsKey( key );
    }

    @Override
    public boolean readBoolean( String key ) {
        String strValue = this.readString( key ).toLowerCase();
        return "true".equals( strValue ) || "yes".equals( strValue ) || "1".equals( strValue );
    }

    @Override
    public int readInt( String key ) {
        String strValue = this.readString( key );
        return Integer.parseInt( strValue );
    }

    @Override
    public String readString( String key ) {

        String result = System.getenv( key );

        if ( result == null ) {
            return "";
        }

        return result.trim();

    }

    @Override
    public List<String> readStrings( String key ) {

        List<String> result = new ArrayList<>();

        // Read in values until an empty string is seen.
        for ( int i = 1; true; i += 1 ) {
            String value = this.readString( key + "." + i );
            if ( value.isEmpty() ) {
                break;
            }
            result.add( value );
        }

        return result;

    }

}
