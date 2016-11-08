//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.injections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class using reflection to instantiate a Dagger component interface.
 */
public class InjectionsFactory {

    /**
     * Calls the create method (by reflection) of the Dagger synthesized class that implements a given component
     * interface. (Avoids IDE errors for direct use of the synthesized class.)
     *
     * @param componentInterface the @Component interface to be created.
     *
     * @return the concrete component.
     */
    @SuppressWarnings( "unchecked" )
    public <T> T create( Class<T> componentInterface )
        throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String componentInterfaceName = componentInterface.getName();

        int lastDot = componentInterfaceName.lastIndexOf( '.' ) + 1;

        String componentClassName = componentInterfaceName.substring( 0, lastDot ) + "Dagger" +
            componentInterfaceName.substring( lastDot ).replace( '$', '_' );

        Class<?> injClass = Class.forName( componentClassName );

        Method method = injClass.getMethod( "create" );

        return (T) method.invoke( null );

    }

}
