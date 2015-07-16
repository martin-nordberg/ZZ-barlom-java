//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

import java.util.regex.Pattern;

/**
 * Token representing a sequence of words to be separated by spaces with line separators only where needed.
 */
class ProseCodeOutputToken
    extends AbstractCodeOutputToken {

    /**
     * Constructs a new token representing the given prose.
     *
     * @param text               the text of the token.
     * @param newLinePrefixChars a prefix to add to the start of any wrapped lines.
     */
    ProseCodeOutputToken( CharSequence text, String newLinePrefixChars ) {

        this.words = ProseCodeOutputToken.WHITESPACE_PATTERN.split( text );

        this.newLinePrefixChars = newLinePrefixChars;

    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        boolean firstWord = true;

        // Output the words with separating spaces.
        for ( String word : this.words ) {
            if ( firstWord ) {
                firstWord = false;
            }
            else {
                output.append( " " );
            }
            output.append( word );
        }

        return startingIndent;

    }

    @Override
    public int writeWrappedText( StringBuilder output, int startingIndent, int spacesPerIndent, int maxLineLength ) {

        boolean firstWord = true;

        int availableLineLength = maxLineLength - this.newLinePrefixChars.length() - startingIndent * spacesPerIndent;
        int usedLineLength = 0;

        for ( String word : this.words ) {
            if ( firstWord ) {
                // Put no separator before the first word.
                firstWord = false;
            }
            else if ( usedLineLength + word.length() + 1 > availableLineLength ) {
                // Insert a line separator, indent & prefix when the line would get too long.
                output.append( SpaceOrWrapCodeOutputToken.LINE_SEPARATOR );
                this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );
                output.append( this.newLinePrefixChars );
                usedLineLength = 0;
            }
            else {
                // Use a simple space character otherwise.
                output.append( " " );
                usedLineLength += 1;
            }

            // Write the word itself.
            output.append( word );
            usedLineLength += word.length();
        }

        return startingIndent;

    }

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile( "\\s+" );

    private final String newLinePrefixChars;

    private final String[] words;

}
