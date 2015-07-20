//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

/**
 * Token representing a space character or else a line separator when needed to make the overall line shorter.
 */
class AlwaysWrapCodeOutputToken
    extends SpaceOrWrapCodeOutputToken {

    AlwaysWrapCodeOutputToken( String newLinePrefixChars ) {
        super( newLinePrefixChars );
    }

    @Override
    public int writeText( StringBuilder output, int startingIndent, int spacesPerIndent ) {

        // Append enough spaces to ensure a normal line is wrapped
        output.append( AlwaysWrapCodeOutputToken.TOO_MANY_SPACES );

        return startingIndent;

    }

    /** Lots of space characters used to artificially force wrapping. */
    private static final String TOO_MANY_SPACES =
        "                                                  " +
            "                                                  " +
            "                                                  " +
            "                                                  " +
            "                                                  " +
            "                                                  ";

}
