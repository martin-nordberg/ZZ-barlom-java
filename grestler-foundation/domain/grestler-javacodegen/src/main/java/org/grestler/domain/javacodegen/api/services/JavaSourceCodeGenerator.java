//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javacodegen.api.services;

import org.grestler.domain.javamodel.api.elements.IJavaAbstractPackage;
import org.grestler.domain.javamodel.api.elements.IJavaClass;
import org.grestler.domain.javamodel.api.elements.IJavaPackage;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;
import org.grestler.persistence.ioutilities.codegen.CodeWriterConfig;
import org.grestler.persistence.ioutilities.fileio.FileRewriter;
import org.grestler.persistence.ioutilities.fileio.FileWriterComparer;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * Utility class writes a package of source code.
 */
public final class JavaSourceCodeGenerator {

    /**
     * Compares the source code for the contents of a Java package.
     *
     * @param pkg              the package to generate code for.
     * @param sourceRootFolder the root folder for the output.
     * @param config           the configuration of the code generation.
     *
     * @throws IOException if output fails.
     */
    public void compareJavaCode(
        IJavaAbstractPackage pkg,
        File sourceRootFolder,
        CodeWriterConfig config
    ) throws IOException {

        // Compute the folder for the package.
        File pkgFolder = sourceRootFolder;
        for ( String parentPkg : pkg.getFullyQualifiedJavaName().split( "\\." ) ) {
            pkgFolder = new File( pkgFolder, parentPkg );
        }

        // Write each class.
        if ( pkg instanceof IJavaPackage ) {
            for ( IJavaClass cls : ( (IJavaPackage) pkg ).getClasses() ) {
                Writer writer = new FileWriterComparer(
                    new File(
                        pkgFolder,
                        cls.getJavaName()
                    )
                );
                CodeWriter codeWriter = new CodeWriter( writer, config );
                cls.consume( JavaCodeGenerator.INSTANCE, codeWriter );
            }
        }

        // Recursively compare each sub-package.
        for ( IJavaPackage subPkg : pkg.getPackages() ) {
            this.compareJavaCode( subPkg, sourceRootFolder, config );
        }

    }

    /**
     * Writes out the source code for the contents of a Java package.
     *
     * @param pkg              the package to generate code for.
     * @param sourceRootFolder the root folder for the output.
     * @param config           the configuration of the code generation.
     *
     * @throws IOException if output fails.
     */
    public void writeJavaCode(
        IJavaAbstractPackage pkg,
        File sourceRootFolder,
        CodeWriterConfig config
    ) throws IOException {

        // Compute the folder for the package.
        File pkgFolder = sourceRootFolder;
        for ( String parentPkg : pkg.getFullyQualifiedJavaName().split( "\\." ) ) {
            pkgFolder = new File( pkgFolder, parentPkg );
        }

        // Create the folder as needed.
        if ( !pkgFolder.mkdirs() ) {
            throw new IOException( "Cannot create folder: " + pkgFolder.getAbsolutePath() );
        }

        // Write each class.
        if ( pkg instanceof IJavaPackage ) {
            for ( IJavaClass cls : ( (IJavaPackage) pkg ).getClasses() ) {
                Writer writer = new FileRewriter(
                    new File(
                        pkgFolder,
                        cls.getJavaName()
                    )
                );
                CodeWriter codeWriter = new CodeWriter( writer, config );
                cls.consume( JavaCodeGenerator.INSTANCE, codeWriter );
            }
        }

        // Recursively write each sub-package.
        for ( IJavaPackage subPkg : pkg.getPackages() ) {
            this.writeJavaCode( subPkg, sourceRootFolder, config );
        }

    }

}
