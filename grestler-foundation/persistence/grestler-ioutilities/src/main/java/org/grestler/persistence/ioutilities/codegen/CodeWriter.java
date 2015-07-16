//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.codegen;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for writing code.
 */
@SuppressWarnings( "HardcodedLineSeparator" )
public class CodeWriter
    implements AutoCloseable {

    /**
     * Constructs a new code writer with given configuration.
     *
     * @param writer the underlying output to be written to.
     * @param config the configuration of the writer.
     */
    public CodeWriter( Writer writer, CodeWriterConfig config ) {
        this.writer = writer;
        this.config = config;
        this.indentForCurrLine = 0;
        this.markers = new ArrayList<>();
        this.tokensOnCurrLine = new ArrayList<>();
    }

    /**
     * Appends a string of text to the output code.
     *
     * @param text the text to append (must not contain any new line characters).
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter append( String text ) {
        this.tokensOnCurrLine.add( new TextCodeOutputToken( text ) );
        return this;
    }

    /**
     * Appends a string of text to the output code if a given condition is true.
     *
     * @param condition the guard condition.
     * @param text      the text to append (must not contain any new line characters).
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "BooleanParameter" )
    public CodeWriter appendIf( boolean condition, String text ) {
        if ( condition ) {
            this.append( text );
        }
        return this;
    }

    /**
     * Appends a string of text that can be evenly spaced and wrapped as needed.
     */
    public CodeWriter appendProse( String text, String newLinePrefixChars ) {
        this.tokensOnCurrLine.add( new ProseCodeOutputToken( text, newLinePrefixChars ) );
        return this;
    }

    @Override
    public void close() {
        try {
            this.writer.close();
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Failed to close code writer.", e );
        }
    }

    /**
     * Increments the indent level for the next written line of code.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter indent() {
        this.tokensOnCurrLine.add( new IndentCodeOutputToken() );
        return this;
    }

    /**
     * Marks the current line's output so far as a point to back up to if needed.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter mark() {
        this.markers.add( this.tokensOnCurrLine.size() );
        return this;
    }

    /**
     * Starts a new line in the output. Writes out the currently pending line including intermediate line wraps if
     * needed.
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "ImplicitNumericConversion" )
    public CodeWriter newLine() {

        int indent = this.indentForCurrLine;

        // Try building the current line without wrapping.
        StringBuilder line = new StringBuilder();
        for ( ICodeOutputToken token : this.tokensOnCurrLine ) {
            indent = token.writeText( line, indent, this.config.spacesPerIndent );
        }

        // Ignore trailing spaces.
        int lineLength = line.length();
        while ( lineLength > 0 && line.charAt( lineLength - 1 ) == ' ' ) {
            lineLength -= 1;
        }

        // If the line is too long, rewrite it with intermediate wrapping.
        if ( lineLength > this.config.maxLineLength ) {

            indent = this.indentForCurrLine;
            line = new StringBuilder();
            for ( ICodeOutputToken token : this.tokensOnCurrLine ) {
                indent = token.writeWrappedText( line, indent, this.config.spacesPerIndent, this.config.maxLineLength );
            }

        }

        // Output the final line ending character(s).
        line.append( SpaceOrWrapCodeOutputToken.LINE_SEPARATOR );

        // Write through to the underlying writer.
        try {
            String lineStr = line.toString();

            // Remove trailing spaces.
            lineStr = CodeWriter.SPACE_LF.matcher( lineStr ).replaceAll( "\n" );
            lineStr = CodeWriter.SPACE_CR_LF.matcher( lineStr ).replaceAll( "\r\n" );

            this.writer.write( lineStr );
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Failed to write through code writer.", e );
        }

        // Update the indent level.
        this.indentForCurrLine = indent;

        // Start the next line.
        this.tokensOnCurrLine.clear();
        this.markers.clear();

        return this;

    }

    /**
     * Discards anything written since the last marker was set.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter revertToMark() {

        if ( this.markers.isEmpty() ) {
            throw new IllegalStateException( "No marker to revert to." );
        }

        int lastMarkerIndex = this.markers.size() - 1;
        int lastMarker = this.markers.get( lastMarkerIndex );

        while ( this.tokensOnCurrLine.size() > lastMarker ) {
            this.tokensOnCurrLine.remove( this.tokensOnCurrLine.size() - 1 );
        }

        this.markers.remove( lastMarkerIndex );

        return this;

    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrap() {
        return this.spaceOrWrap( "" );
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead.
     * Includes extra characters (typically a comment indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrap( String newLinePrefixChars ) {
        this.tokensOnCurrLine.add( new SpaceOrWrapCodeOutputToken( newLinePrefixChars ) );
        return this;
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead. If
     * wrapped, the next line will also be indented.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrapIndent() {
        return this.spaceOrWrap().indent();
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead. If
     * wrapped, the next line will also be unindented.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrapUnindent() {
        return this.spaceOrWrap().unindent();
    }

    /**
     * Increments the indent level for the next written line of code.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter unindent() {
        this.tokensOnCurrLine.add( new UnindentCodeOutputToken() );
        return this;
    }

    /** Pattern for removing line ending spaces. */
    private static final Pattern SPACE_CR_LF = Pattern.compile( "( )+\\r\\n" );

    /** Pattern for removing line ending spaces. */
    private static final Pattern SPACE_LF = Pattern.compile( "( )+\\n" );

    /** The configuration of the code writer. */
    private final CodeWriterConfig config;

    /** The indent level queued up for the current line. */
    private int indentForCurrLine;

    /** Markers to make it possible to back up on the current line. */
    private final List<Integer> markers;

    /** Tokens waiting to be written as the current line of output. */
    private final List<ICodeOutputToken> tokensOnCurrLine;

    /** The underlying writer receiving the output of this code writer. */
    private final Writer writer;

}
