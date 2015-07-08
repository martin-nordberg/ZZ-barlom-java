package org.grestler.persistence.ioutilities.codegen

import spock.lang.Specification

/**
 * Specification for class CodeWriter.
 */
class CodeWriterSpec
    extends Specification {

    def nl = System.getProperty( "line.separator" );

    def "A code writer generates a bit of code."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 80 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "// a comment" )
                  .newLine()
                  .appendIf( true, "stuff" )
                  .appendIf( false, "junk" )
                  .spaceOrWrap()
                  .append( "to look at {" )
                  .indent()
                  .newLine()
                  .append( "indented" )
                  .unindent()
                  .newLine()
                  .append( "}" )
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "// a comment" + nl + "stuff to look at {" + nl + "    indented" + nl + "}" + nl;

    }

    def "A code writer wraps when needed."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "1234567890" )
                  .spaceOrWrap()
                  .append( "ABCDEFGHI" )
                  .newLine()
                  .append( "1234567890" )
                  .spaceOrWrap()
                  .append( "ABCDEFGHIJ" )
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "1234567890 ABCDEFGHI" + nl + "1234567890" + nl + "ABCDEFGHIJ" + nl;

    }

    def "A code writer wrap/indents when needed."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "1234567890" )
                  .spaceOrWrapIndent()
                  .append( "ABCDEFGHI" )
                  .spaceOrWrapUnindent()
                  .newLine()
                  .append( "1234567890" )
                  .spaceOrWrapIndent()
                  .append( "ABCDEFGHIJ" )
                  .spaceOrWrapUnindent()
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "1234567890 ABCDEFGHI" + nl + "1234567890" + nl + "    ABCDEFGHIJ" + nl + nl;

    }


}
