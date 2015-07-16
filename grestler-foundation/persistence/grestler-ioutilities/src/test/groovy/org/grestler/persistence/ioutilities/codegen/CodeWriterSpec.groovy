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

    def "A code writer wraps and prefixes new line when needed."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "1234567890" )
                  .spaceOrWrap( "* " )
                  .append( "ABCDEFGHI" )
                  .newLine()
                  .append( "1234567890" )
                  .spaceOrWrap( "* " )
                  .append( "ABCDEFGHIJ" )
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "1234567890 ABCDEFGHI" + nl + "1234567890" + nl + "* ABCDEFGHIJ" + nl;

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

    def "A code writer reverts to a mark."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "1" )
                  .append( "2" )
                  .append( "3" )
                  .mark()
                  .append( "4" )
                  .append( "5" )
                  .append( "6" )
                  .revertToMark()
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "123" + nl;

    }

    def "A code writer reverts to multiple marks."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "1" )
                  .append( "2" )
                  .append( "3" )
                  .mark()
                  .append( "4" )
                  .mark()
                  .append( "5" )
                  .mark()
                  .append( "6" )
                  .revertToMark()
                  .revertToMark()
                  .revertToMark()
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "123" + nl;

    }

    def "A code writer writes prose without wrapping."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.appendProse( "abc   def\tghi\njkl", "NEVER" )
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "abc def ghi jkl" + nl;

    }


    def "A code writer writes prose with wrapping."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.append( "// " )
                  .appendProse( "abc   def\tghi\njkl mno pqr stu vwx yz", "// " )
                  .newLine()
                  .close();

        def code = writer.toString();

        expect:
        code == "// abc def ghi jkl" + nl + "// mno pqr stu vwx" + nl + "// yz" + nl

    }

    def "A code writer writes indented prose without wrapping."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 100 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.indent()
                  .append( "// " )
                  .appendProse( "abc   def\tghi\njkl mno pqr stu vwx yz", "// " )
                  .newLine()
                  .unindent()
                  .close();

        def code = writer.toString();

        expect:
        code == "    // abc def ghi jkl mno pqr stu vwx yz" + nl

    }

    def "A code writer writes indented prose with wrapping."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 4, 20 );
        def codeWriter = new CodeWriter( writer, config );

        codeWriter.indent()
                  .append( "// " )
                  .appendProse( "abc   def\tghi\njkl mno pqr stu vwx yz", "// " )
                  .newLine()
                  .unindent()
                  .close();

        def code = writer.toString();

        expect:
        code == "    // abc def ghi" + nl + "    // jkl mno pqr" + nl + "    // stu vwx yz" + nl

    }


}
