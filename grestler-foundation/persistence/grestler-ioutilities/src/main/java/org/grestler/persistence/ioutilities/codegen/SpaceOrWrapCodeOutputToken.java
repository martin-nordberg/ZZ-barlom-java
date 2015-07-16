//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing a space character or else a line separator when needed to make the overall line shorter.
 */
class SpaceOrWrapCodeOutputToken
    extends AbstractCodeOutputToken {

    SpaceOrWrapCodeOutputToken( String newLinePrefixChars ) {
        this.newLinePrefixChars = newLinePrefixChars;
    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );

        output.append( " " );

        return startingIndent;

    }

    @Override
    public int writeWrappedText( StringBuilder output, int startingIndent, int spacesPerIndent, int maxLineLength ) {

        output.append( SpaceOrWrapCodeOutputToken.LINE_SEPARATOR );
        this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );
        output.append( this.newLinePrefixChars );

        return startingIndent;

    }

    static final String LINE_SEPARATOR = System.getProperty( "line.separator" );

    private final String newLinePrefixChars;

}
