//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * General purpose configuration settings from properties files.
 */
@SuppressWarnings( "ClassWithTooManyConstructors" )
public class PropertiesFileConfiguration
    implements IConfiguration {

    /**
     * Constructs a configuration object using the properties file corresponding to a given class.
     *
     * @param classInPkgWithConfig the class with a corresponding .properties file.
     */
    public PropertiesFileConfiguration( Class<?> classInPkgWithConfig ) {
        this( classInPkgWithConfig, null, null );
    }

    /**
     * Constructs a configuration object using the properties file corresponding to a given class.
     *
     * @param classInPkgWithConfig the class with a corresponding .properties file.
     * @param overridingConfiguration a client configuration that is consulted to override values in this configuration.
     */
    public PropertiesFileConfiguration( Class<?> classInPkgWithConfig, IConfiguration overridingConfiguration ) {
        this( classInPkgWithConfig, overridingConfiguration, null );
    }

    /**
     * Constructs a configuration object using the properties file corresponding to a given class.
     *
     * @param classInPkgWithConfig the class with a corresponding .properties file.
     * @param prefix a prefix to be prepended to every use of a key.
     */
    public PropertiesFileConfiguration( Class<?> classInPkgWithConfig, String prefix ) {
        this( classInPkgWithConfig, null, prefix );
    }

    /**
     * Constructs a configuration object using the properties file corresponding to a given class.
     *
     * @param classInPkgWithConfig the class with a corresponding .properties file.
     * @param overridingConfiguration a client configuration that is consulted to override values in this configuration.
     * @param prefix a prefix to be prepended to every use of a key.
     */
    public PropertiesFileConfiguration( Class<?> classInPkgWithConfig, IConfiguration overridingConfiguration, String prefix ) {

        this.properties = new Properties();
        this.overridingConfiguration = overridingConfiguration;
        this.prefix = prefix == null ? "" : prefix + ".";
        this.overridingPrefix = classInPkgWithConfig.getName() + "." + prefix;

        try {

            // Build the properties file name from the class name.
            String fileName = classInPkgWithConfig.getSimpleName() + ".properties";

            // Open the properties file as a classpath resource.
            InputStream stream = classInPkgWithConfig.getResourceAsStream( fileName );

            // Must be present.
            if ( stream == null ) {
                throw new IllegalArgumentException( "No properties file found for class " + classInPkgWithConfig.getName() );
            }

            // Load the properties file.
            this.properties.load( stream );

        }
        catch ( IOException e ) {

            throw new IllegalArgumentException( "Failed to load properties file for class " + classInPkgWithConfig.getName() );

        }

    }

    @Override
    public boolean hasValueForKey( String key ) {
        return this.hasOverridingValueForKey( this.getOverridingKey( key ) ) ||
            this.properties.containsKey( this.getPrefixedKey( key ) );
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

        String overridingKey = this.getOverridingKey( key );

        if ( this.hasOverridingValueForKey( overridingKey ) ) {
            return this.overridingConfiguration.readString( overridingKey );
        }

        return this.properties.getProperty( this.getPrefixedKey( key ), "" ).trim();

    }

    @Override
    public List<String> readStrings( String key ) {

        String overridingKey = this.getOverridingKey( key );

        if ( this.hasOverridingValueForKey( overridingKey + ".1" ) ) {
            return this.overridingConfiguration.readStrings( overridingKey );
        }

        List<String> result = new ArrayList<>();

        // Read in values until an empty string is seen.
        for ( int i = 1; true; i += 1 ) {
            String value = this.readString( this.getPrefixedKey( key ) + "." + i );
            if ( value.isEmpty() ) {
                break;
            }
            result.add( value );
        }

        return result;

    }

    /**
     * Adds the overriding prefix to the given key if it is not already so prefixed.
     *
     * @param key the base key to be prefixed.
     *
     * @return the fully prefixed key.
     */
    private String getOverridingKey( String key ) {

        // Don't prefix an already prefixed key.
        if ( key.startsWith( "org." ) ) {
            return key;
        }

        return this.overridingPrefix + key;

    }

    /**
     * Adds the extra prefix to the given key.
     *
     * @param key the base key to be prefixed.
     *
     * @return the prefixed key.
     */
    private String getPrefixedKey( String key ) {
        return this.prefix + key;
    }

    /**
     * Determines whether the overriding configuration exists and has a value for the given prefixed key.
     *
     * @param overridingKey the fully prefixed key to see if overridden
     *
     * @return true if there is an overriding configuration with a value for the key.
     */
    private boolean hasOverridingValueForKey( String overridingKey ) {
        return this.overridingConfiguration != null && this.overridingConfiguration.hasValueForKey( overridingKey );
    }

    /**
     * A prefix to be applied to every key in the properties file and in the overriding configuration.
     */
    private final String prefix;

    /**
     * An outer client configuration that is to be tried before the configuration in the properties file.
     */
    private final IConfiguration overridingConfiguration;

    /**
     * Prefix to use when looking up overriding confiruation values.
     */
    private final String overridingPrefix;

    /**
     * The properties file where the configuration comes from.
     */
    private final Properties properties;

}
