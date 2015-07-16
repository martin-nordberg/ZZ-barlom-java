package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javacodegen.api.services.JavaCodeGenerator;
import org.grestler.domain.javamodel.api.elements.IJavaClass;
import org.grestler.domain.javamodel.api.elements.IJavaField;
import org.grestler.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java class.
 */
public final class JavaClassCodeGenerator
    extends JavaAnnotatableModelElementCodeGenerator
    implements IJavaModelConsumerService<IJavaClass, CodeWriter> {

    private JavaClassCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume( IJavaClass klass, CodeWriter writer ) {

        JavaCodeGenerator generator = JavaCodeGenerator.INSTANCE;

        // Package declaration
        writer.newLine()
              .append( "package " )
              .append( klass.getParent().getFullyQualifiedJavaName() )
              .append( ";" )
              .newLine()
              .newLine();

        // Imports
        writer.append( "// TODO: imports ... " )
              .newLine()
              .newLine();

        // JavaDoc
        writer.append( "// TODO: javadoc ... " )
              .newLine();

        // Annotations
        this.writeAnnotations( klass, writer );
        writer.newLine();

        writer.append( "public " )
              .appendIf( klass.isFinal(), "final " )
              .appendIf( klass.isAbstract(), "abstract " )
              .append( "class " )
              .append( klass.getJavaName() );
        // TODO: type args

        if ( klass.getBaseClass().isPresent() ) {
            writer.newLine()
                  .indent()
                  .append( "extends " )
                  .append( klass.getBaseClass().get().getJavaName() )
                  .unindent();
        }

        if ( !klass.getImplementedInterfaces().isEmpty() ) {
            writer.newLine()
                  .indent()
                  .append( "implements " );
            for ( IJavaImplementedInterface implInterface : klass.getImplementedInterfaces() ) {
                writer.append( implInterface.getImplementedInterface().getJavaName() )
                      .mark()
                      .append( ", " );
            }
            writer.revertToMark();
        }

        writer.append( " {" )
              .newLine()
              .indent();

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
