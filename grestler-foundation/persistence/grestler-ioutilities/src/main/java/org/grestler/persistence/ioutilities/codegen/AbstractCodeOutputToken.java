//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Abstract code output token implementing common capabilities for token output.
 */
abstract class AbstractCodeOutputToken
    implements ICodeOutputToken {

    /** Constructs a new abstract code output token. */
    protected AbstractCodeOutputToken() {
    }

    @Override
    public int writeWrappedText( StringBuilder output, int startingIndent, int spacesPerIndent, int maxLineLength ) {
        return this.writeText( output, startingIndent, spacesPerIndent );
    }

    /**
     * Writes the spaces needed at the start of a line to get the needed indent level.
     *
     * @param output          the string builder to output to.
     * @param startingIndent  the indent level needed.
     * @param spacesPerIndent the number of space characters per indent level.
     */
    @SuppressWarnings( { "HardcodedLineSeparator", "ImplicitNumericConversion" } )
    protected void appendIndentSpacesIfNeeded( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        if ( output.length() == 0 || output.charAt( output.length() - 1 ) == '\n' ) {
            int spaces = Math.min( startingIndent * spacesPerIndent, AbstractCodeOutputToken.SPACES.length() );
            output.append( AbstractCodeOutputToken.SPACES.substring( 0, spaces ) );
        }

    }

    /** A string of spaces to be used (via substring) for indenting. */
    private static final String SPACES = "                                        ";

}
