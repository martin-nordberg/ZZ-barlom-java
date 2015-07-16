//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing a decrement to the indentation level.
 */
class UnindentCodeOutputToken
    extends AbstractCodeOutputToken {

    /**
     * Constructs a new unindent token.
     */
    UnindentCodeOutputToken() {
    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {
        return startingIndent - 1;
    }

}
