//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.ioutilities.codegen;

/**
 * Configuration parameters for a code writer.
 */
public final class CodeWriterConfig {

    /**
     * Constructs a new code writer configuration object.
     *
     * @param spacesPerIndent the number of spaces per indent level.
     * @param maxLineLength   the maximum length of a line before wrapping.
     */
    public CodeWriterConfig( int spacesPerIndent, int maxLineLength ) {
        this.spacesPerIndent = spacesPerIndent;
        this.maxLineLength = maxLineLength;
    }

    /** The maximum length of a line to allow before wrapping text via intermediate new lines. */
    public final int maxLineLength;

    /** The number of space characters to use for each indent. */
    public final int spacesPerIndent;

}
