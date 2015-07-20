import org.grestler.domain.javacodegen.api.services.JavaCodeGenerator
import org.grestler.domain.javamodel.api.elements.EJavaAccessibility
import org.grestler.domain.javamodel.api.elements.IJavaRootPackage
import org.grestler.domain.javamodel.impl.elements.JavaRootPackage
import org.grestler.persistence.ioutilities.codegen.CodeWriter
import org.grestler.persistence.ioutilities.codegen.CodeWriterConfig
import spock.lang.Specification

/**
 * Specification for class JavaCodeGenerator.
 */
class JavaCodeGeneratorSpec
    extends Specification {

    def nl = System.getProperty( "line.separator" );

    def "A code generator generates the code for a simple class."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 2, 80 );
        def codeWriter = new CodeWriter( writer, config );

        IJavaRootPackage root = new JavaRootPackage();

        def pkg = root.addPackage( "pkg1" ).addPackage( "pkg2" );
        def cls = pkg.addClass( "MyClass", Optional.empty(), false, true, Optional.empty() );

        cls.addField( "field1", root.builtinInt );

        cls.addConstructor( "Default constructor.", EJavaAccessibility.PROTECTED );

        def con = cls.addConstructor( "Constructs a new instance.", EJavaAccessibility.PUBLIC );
        con.addParameter( "p1", "first parameter", root.builtinDouble );
        con.addParameter( "p2", "second parameter", root.builtinFloat );
//        con.addAssignmentStatement( "this.field1", "p1" );

        def meth1 = cls.addMethod( "method1", "A method to do stuff.", root.builtinBoolean );

        def meth2 = cls.addMethod( "method2", "A method to do more stuff.", root.builtinInt );
        meth2.addParameter( "arg1", "first argument", root.builtinLong );

        cls.consume( JavaCodeGenerator.INSTANCE, codeWriter );

        def code = writer.toString();

        expect:
        code == nl +
            "package pkg1.pkg2;" + nl +
            nl +
            "// TODO: imports ..." + nl +
            nl +
            "public final class MyClass {" + nl +
            nl +
            "  /** Default constructor. */" + nl +
            "  protected  MyClass() {" + nl +
            "  }" + nl +
            nl +
            "  /**" + nl +
            "   * Constructs a new instance." + nl +
            "   * @param p1 first parameter" + nl +
            "   * @param p2 second parameter" + nl +
            "   */" + nl +
            "  public  MyClass( double p1, float p2 ) {" + nl +
            "  }" + nl +
            nl +
            "  /** A method to do stuff. */" + nl +
            "  public boolean method1() {" + nl +
            "  }" + nl +
            nl +
            "  /**" + nl +
            "   * A method to do more stuff." + nl +
            "   * @param arg1 first argument" + nl +
            "   */" + nl +
            "  public int method2( long arg1 ) {" + nl +
            "  }" + nl +
            nl +
            "  private final int field1;" + nl +
            nl +
            "}" + nl +
            nl;

    }


}
