//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing a simple string of text.
 */
class SpaceOrWrapCodeOutputToken
    extends AbstractCodeOutputToken {

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );

        output.append( " " );

        return startingIndent;

    }

    @Override
    public int writeWrappedText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        output.append( System.getProperty( "line.separator" ) );

        return startingIndent;

    }

}
