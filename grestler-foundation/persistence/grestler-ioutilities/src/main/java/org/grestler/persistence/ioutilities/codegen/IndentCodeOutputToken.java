//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing a simple string of text.
 */
class IndentCodeOutputToken
    extends AbstractCodeOutputToken {

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {
        return startingIndent + 1;
    }

}
