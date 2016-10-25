//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.ioutilities.codegen;

/**
 * Token representing some output (in a derived class) or else a line separator when needed to make the overall line
 * shorter.
 */
abstract class AbstractWrapCodeOutputToken
    extends AbstractCodeOutputToken {

    protected AbstractWrapCodeOutputToken( String newLinePrefixChars ) {
        this.newLinePrefixChars = newLinePrefixChars;
    }

    @Override
    public final int writeWrappedText(
        StringBuilder output,
        int startingIndent,
        int spacesPerIndent,
        int maxLineLength
    ) {

        output.append( AbstractWrapCodeOutputToken.LINE_SEPARATOR );
        this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );
        output.append( this.newLinePrefixChars );

        return startingIndent;

    }

    static final String LINE_SEPARATOR = System.getProperty( "line.separator" );

    private final String newLinePrefixChars;

}
