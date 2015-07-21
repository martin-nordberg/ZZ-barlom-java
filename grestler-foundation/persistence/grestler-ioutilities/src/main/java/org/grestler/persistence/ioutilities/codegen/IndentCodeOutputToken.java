//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing an increment to the indentation level.
 */
final class IndentCodeOutputToken
    extends AbstractCodeOutputToken {

    /**
     * Constructs a new indent token.
     */
    IndentCodeOutputToken() {
    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {
        return startingIndent + 1;
    }

}
