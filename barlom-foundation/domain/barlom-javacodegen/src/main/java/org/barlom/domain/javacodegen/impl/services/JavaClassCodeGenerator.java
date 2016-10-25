package org.barlom.domain.javacodegen.impl.services;

import org.barlom.domain.javacodegen.api.services.JavaCodeGenerator;
import org.barlom.domain.javamodel.api.elements.IJavaClass;
import org.barlom.domain.javamodel.api.elements.IJavaConstructor;
import org.barlom.domain.javamodel.api.elements.IJavaField;
import org.barlom.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.barlom.domain.javamodel.api.elements.IJavaMethod;
import org.barlom.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.barlom.domain.javamodel.api.elements.IJavaType;
import org.barlom.domain.javamodel.api.services.IJavaModelConsumerService;
import org.barlom.persistence.ioutilities.codegen.CodeWriter;

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

        // Package declaration
        writer.newLine()
              .append( "package " )
              .append( klass.getParent().getFullyQualifiedJavaName() )
              .append( ";" )
              .newLine()
              .newLine();

        // Imports
        for ( IJavaType imp : klass.getImports() ) {
            writer.append( "import " )
                  .append( imp.getFullyQualifiedJavaName() )
                  .append( ";" )
                  .newLine();
        }
        writer.newLineIf( !klass.getImports().isEmpty() );

        // JavaDoc
        if ( klass.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( klass.getDescription().get(), " * " );

            writer.spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Annotations
        this.writeAnnotations( klass, writer, true );

        // Class name
        writer.append( "public " )
              .appendIf( klass.isFinal(), "final " )
              .appendIf( klass.isAbstract(), "abstract " )
              .append( "class " )
              .append( klass.getJavaName() );
        // TODO: type args

        // Extends
        if ( klass.getBaseClass().isPresent() ) {
            writer.newLine()
                  .indent()
                  .append( "extends " )
                  .append( klass.getBaseClass().get().getJavaName() )
                  .unindent();
        }

        // Implements
        if ( !klass.getImplementedInterfaces().isEmpty() ) {
            writer.newLine()
                  .indent()
                  .append( "implements " );
            for ( IJavaImplementedInterface implInterface : klass.getImplementedInterfaces() ) {
                writer.append( implInterface.getImplementedInterface().getJavaName() )
                      .mark()
                      .append( ", " );
            }
            writer.revertToMark()
                  .unindent();
        }

        // Opening brace
        writer.append( " {" )
              .newLine()
              .newLine()
              .indent();

        // Constructors
        for ( IJavaConstructor constructor : klass.getConstructors() ) {
            constructor.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Methods
        for ( IJavaMethod method : klass.getMethods() ) {
            method.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Fields
        for ( IJavaField field : klass.getFields() ) {
            field.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Static initializers
        for ( IJavaStaticInitialization staticInitialization : klass.getStaticInitializations() ) {
            staticInitialization.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Closing brace
        writer.unindent()
              .append( "}" )
              .newLine()
              .newLine();

    }

    public static final JavaClassCodeGenerator INSTANCE = new JavaClassCodeGenerator();

}
