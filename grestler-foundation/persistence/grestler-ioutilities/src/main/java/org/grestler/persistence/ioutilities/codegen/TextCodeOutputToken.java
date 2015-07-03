//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing a simple string of text.
 */
class TextCodeOutputToken
    extends AbstractCodeOutputToken {

    /**
     * Constructs a new token representing the given text.
     *
     * @param text the text of the token.
     */
    public TextCodeOutputToken( String text ) {
        this.text = text;
    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        if ( !this.text.isEmpty() ) {

            this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );

            output.append( this.text );

        }

        return startingIndent;

    }

    private final String text;

}
