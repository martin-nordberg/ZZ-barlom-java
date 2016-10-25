//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

/**
 * Enumeration of Java accessibility levels.
 */
public enum EJavaAccessibility {

    PUBLIC( "public" ),

    PROTECTED( "protected" ),

    DEFAULT( "/*default*/" ),

    PRIVATE( "private" );

    EJavaAccessibility( String keyWord ) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    private final String keyWord;

}
