package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javacodegen.api.services.JavaCodeGenerator;
import org.grestler.domain.javamodel.api.elements.IJavaClass;
import org.grestler.domain.javamodel.api.elements.IJavaField;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java class.
 */
public final class JavaClassCodeGenerator
    implements IJavaModelConsumerService<IJavaClass, CodeWriter> {

    private JavaClassCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume( IJavaClass klass, CodeWriter writer ) {

        JavaCodeGenerator generator = JavaCodeGenerator.INSTANCE;

        writer.newLine()
              .append( "package " )
              .append( klass.getParent().getFullyQualifiedJavaName() )
              .append( ";" )
              .newLine()
              .newLine();

        writer.append( "// TODO: imports ... " )
              .newLine()
              .newLine();

        writer.append( "// TODO: javadoc ... " )
              .newLine();

        writer.append( "public " )
              .appendIf( klass.isFinal(), "final " )
              .appendIf( klass.isAbstract(), "abstract " )
              .append( "class " )
              .append( klass.getName() )
              // TODO: type args
              .append( " {" )
              .newLine()
              .indent();

        writer.append( "// TODO: extends ... " )
              .newLine();

        writer.append( "// TODO: implements ... " )
              .newLine();

        writer.append( "// TODO: methods ... " )
              .newLine()
              .newLine();

        for ( IJavaField field : klass.getFields() ) {
            field.consume( generator, writer );
        }

        writer.unindent()
              .append( "}" )
              .newLine()
              .newLine();

    }

    public static final JavaClassCodeGenerator INSTANCE = new JavaClassCodeGenerator();

}
