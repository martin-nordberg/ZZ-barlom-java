//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.fileio;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;

/**
 * Class similar to FileWriter, but it does not change a file if the output content matches the file's current content.
 */
public abstract class AbstractFileRewriter
    extends Writer {

    /**
     * Constructs a new file rewriter to the given file.
     * @param file the file to be written if changed.
     */
    protected AbstractFileRewriter( File file ) {
        this.file = file;
        this.writer = new StringWriter();
    }

    /**
     * Closes the file writer: compares the intended output against current file contents and writes the output only if
     * changed.
     */
    @Override
    public void close() throws IOException {

        // Check whether the file exists.
        boolean rewrite = !this.file.exists();

        // Get the new content for the file.
        String newContent = this.writer.toString();

        if ( !rewrite ) {
            try {
                // Read the current content of the file.
                String oldContent = new String( Files.readAllBytes( this.file.toPath() ) );

                // Rewrite if the content has changed.
                if ( !newContent.equals( oldContent ) ) {
                    rewrite = true;
                }
            }
            catch ( IOException e ) {
                // Rewrite if failed to read.
                rewrite = true;
            }
        }

        // Write the real file if its contents have changed.
        if ( rewrite ) {
            this.rewrite( this.file, newContent );
        }
    }

    /**
     * Handles the case where the new contents of the file differ from the old contents and the file
     * needs to be overwritten.
     * @param newContent the new content of the file.
     * @throws IOException if the rewriting fails.
     */
    protected abstract void rewrite( File fileToWrite, String newContent ) throws IOException;

    /** Flushes the output for this file writer. */
    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    /** Low level writer implementation - writes some bytes to the inner string writer. */
    @SuppressWarnings( "NullableProblems" )
    @Override
    public void write( char[] buffer, int off, int len ) throws IOException {
        this.writer.write( buffer, off, len );
    }

    /** The file to be written if content changes */
    private final File file;

    /** The place to write out output until we have decided if the content has changed. */
    private final StringWriter writer;

}
