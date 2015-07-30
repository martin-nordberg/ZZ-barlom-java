//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.ioutilities.fileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;

/**
 * Class similar to FileWriter, but instead of writing a file it compares what would be written to what is already in
 * the file for purposes of test correctness assertion.
 */
public class FileWriterComparer
    extends AbstractFileRewriter {

    /**
     * Constructs a new file writer/comparer to the given file.
     * @param file the file to be compared with.
     */
    public FileWriterComparer( File file ) {
        super( file );
    }

    @Override
    protected void rewrite( File fileToWrite, String newContent ) throws IOException {
        assert false : "File comparison failed." + fileToWrite.getAbsolutePath();
    }


}
