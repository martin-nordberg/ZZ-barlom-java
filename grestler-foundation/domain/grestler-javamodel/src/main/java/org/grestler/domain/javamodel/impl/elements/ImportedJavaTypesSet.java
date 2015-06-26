//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaType;

import java.util.TreeSet;

/**
 * Specialized set type for ordering unique imports.
 */
@SuppressWarnings( "ClassExtendsConcreteCollection" )
public class ImportedJavaTypesSet
    extends TreeSet<IJavaType> {

    public ImportedJavaTypesSet() {
        super(
            ( IJavaType t1, IJavaType t2 ) -> {
                return t1.getFullyQualifiedJavaName().compareTo( t2.getFullyQualifiedJavaName() );
            }
        );
    }

    @Override
    public boolean add( IJavaType javaType ) {
        return javaType.isImplicitlyImported() || super.add( javaType );
    }

    /** TBD */
    private static final long serialVersionUID = 1L;

}
