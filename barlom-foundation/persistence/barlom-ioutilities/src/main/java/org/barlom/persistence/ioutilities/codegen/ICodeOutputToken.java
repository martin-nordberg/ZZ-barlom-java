//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.ioutilities.codegen;

/**
 * A token of code not yet written by a code writer. Offers output with or without wrapping.
 */
interface ICodeOutputToken {

    /**
     * Writes this token of code to the given string builder. Does not include any new lines in the output.
     *
     * @param output          the string builder to receive the output.
     * @param startingIndent  the starting indent level.
     * @param spacesPerIndent the spaces to use per indent level.
     *
     * @return the ending indent level.
     */
    int writeText( StringBuilder output, int startingIndent, int spacesPerIndent );

    /**
     * Writes this token of code to the given string builder. Includes a new line in the output if supported by the
     * token.
     *
     * @param output          the string builder to receive the output.
     * @param startingIndent  the starting indent level.
     * @param spacesPerIndent the spaces to use per indent level.
     * @param maxLineLength   the maximum number of characters per line.
     *
     * @return the ending indent level.
     */
    int writeWrappedText( StringBuilder output, int startingIndent, int spacesPerIndent, int maxLineLength );

}
