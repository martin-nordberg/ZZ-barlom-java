//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlModelElement;
import org.barlom.domain.sqlmodel.api.elements.ISqlNamedModelElement;
import org.barlom.infrastructure.utilities.collections.IIndexable;
import org.barlom.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract model element with a name.
 */
public abstract class SqlNamedModelElement
    extends SqlModelElement
    implements ISqlNamedModelElement {

    /**
     * Constructs a new named SQL model element.
     *
     * @param parent the parent of the new element.
     * @param name   the name of the new element.
     */
    protected SqlNamedModelElement( ISqlNamedModelElement parent, String name, String description ) {
        super( parent, description );

        assert name != null && !name.isEmpty();

        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public IIndexable<ISqlModelElement> getChildren() {
        return new ReadOnlyListAdapter<>( this.children );
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        String result = this.getParent().getPath();
        if ( !result.isEmpty() ) {
            result += ".";
        }
        return result + this.getSqlName();
    }

    @Override
    public String getPathWithSchema() {
        String result = this.getParent().getPathWithSchema();
        if ( !result.isEmpty() ) {
            result += ".";
        }
        return result + this.getSqlName();
    }

    /** Ensures that a given identifier is short enough to be a valid SQL identifier (<= 30 chars). */
    protected static String makeSqlName( String identifier ) {

        // convert to upper case with underscores
        String result = identifier.toUpperCase();

        // convert dashes and spaces to underscores
        result = SqlNamedModelElement.SPACE_PATTERN.matcher( result ).replaceAll( "_" );
        result = SqlNamedModelElement.DASH_PATTERN.matcher( result ).replaceAll( "_" );

        // done if identifier is short enough
        if ( result.length() <= 30 ) {
            return result;
        }

        // remove vowels from the end so long as they are not word-leading and not "ID"
        while ( result.length() > 30 ) {

            Matcher matcher = SqlNamedModelElement.VOWEL_PATTERN.matcher( result );

            if ( !matcher.matches() ) {
                break;
            }

            result = matcher.replaceFirst( "$1$2" );
        }

        // remove underscores, starting from the end
        while ( result.length() > 30 ) {

            Matcher matcher = SqlNamedModelElement.UNDERSCORE_PATTERN.matcher( result );

            if ( !matcher.matches() ) {
                break;
            }

            result = matcher.replaceFirst( "$1$2" );
        }

        // remove near ending chars and add hash code digits to get short enough & artificially unique
        // (hopefully)
        if ( result.length() > 30 ) {
            Integer hashCode = identifier.hashCode();
            String hashCodeStr = Integer.toHexString( hashCode ).toUpperCase();
            result = result.substring( 0, 30 - hashCodeStr.length() ) + hashCodeStr;
        }

        return result;
    }

    /** Returns the SQL name of this element with a suffix appended. */
    protected static String makeSqlName( String fragment1, String fragment2 ) {

        assert fragment1 != null && !fragment1.isEmpty() : "SQL fragment should not be empty.";
        assert fragment2 != null && !fragment2.isEmpty() : "SQL fragment should not be empty.";

        return SqlNamedModelElement.makeSqlName( fragment1 + "_" + fragment2 );

    }

    /** Returns the SQL name of this element with both a prefix and suffix appended. */
    protected static String makeSqlName( String fragment1, String fragment2, String fragment3 ) {

        assert fragment1 != null && !fragment1.isEmpty() : "SQL fragment should not be empty.";
        assert fragment2 != null && !fragment2.isEmpty() : "SQL fragment should not be empty.";
        assert fragment3 != null && !fragment3.isEmpty() : "SQL fragment should not be empty.";

        return SqlNamedModelElement.makeSqlName( fragment1 + "_" + fragment2 + "_" + fragment3 );

    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( ISqlModelElement child ) {
        this.children.add( child );
    }

    private static final Pattern DASH_PATTERN = Pattern.compile( "-" );

    private static final Pattern SPACE_PATTERN = Pattern.compile( " " );

    private static final Pattern UNDERSCORE_PATTERN = Pattern.compile( "(.*)_(.*)" );

    private static final Pattern VOWEL_PATTERN = Pattern.compile( "(.*[^_])[AEIOU]([^D].*)" );

    private final List<ISqlModelElement> children;

    private final String name;

}
