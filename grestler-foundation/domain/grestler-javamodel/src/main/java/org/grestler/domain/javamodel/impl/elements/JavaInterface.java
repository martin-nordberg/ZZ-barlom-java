//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaInterface;

/**
 * An interface.
 */
public class JavaInterface
    extends JavaComponent
    implements IJavaInterface {

    /**
     * Constructs a new class.
     */
    JavaInterface( JavaPackage parent, String name, String description, boolean isExternal ) {
        super( parent, name, description, isExternal );

        parent.onAddChild( this );
    }

}
