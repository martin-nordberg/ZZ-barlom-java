//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.ioutilities.codegen;

/**
 * Token representing a space character or else a line separator when needed to make the overall line shorter.
 */
final class SpaceOrWrapCodeOutputToken
    extends AbstractWrapCodeOutputToken {

    SpaceOrWrapCodeOutputToken( String newLinePrefixChars ) {
        super( newLinePrefixChars );
    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        this.appendIndentSpacesIfNeeded( output, startingIndent, spacesPerIndent );

        output.append( " " );

        return startingIndent;

    }

}
