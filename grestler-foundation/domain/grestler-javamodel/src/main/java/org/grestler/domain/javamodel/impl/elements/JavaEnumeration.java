//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaEnumConstant;
import org.grestler.domain.javamodel.api.elements.IJavaEnumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * An enumeration.
 */
public class JavaEnumeration
    extends JavaConcreteComponent
    implements IJavaEnumeration {

    /**
     * Constructs a new class.
     */
    JavaEnumeration( JavaPackage parent, String name, String description, boolean isExternal ) {
        super( parent, name, description, isExternal );

        this.enumConstants = new ArrayList<>();

        parent.onAddChild( this );
    }

    /** Creates an new enum constant within this enumeration. */
    @Override
    public IJavaEnumConstant addEnumConstant(
        String name, String description, Integer uniqueId, String parametersCode, String referencePrefix
    ) {
        return new JavaEnumConstant( this, name, description, uniqueId, parametersCode, referencePrefix );
    }

    /** @return the enum constants within this enumeration. */
    @Override
    public List<IJavaEnumConstant> getEnumConstants() {
        return this.enumConstants;
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaEnumConstant child ) {
        super.onAddChild( child );
        this.enumConstants.add( child );
    }

    private final List<IJavaEnumConstant> enumConstants;
}
