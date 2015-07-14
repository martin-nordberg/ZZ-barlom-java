//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaBuiltinType;
import org.grestler.domain.javamodel.api.elements.IJavaRootPackage;

import java.util.Optional;

/**
 * The root package for Java code.
 */
public final class JavaRootPackage
    extends JavaAbstractPackage
    implements IJavaRootPackage {

    /**
     * Constructs a new Java package.
     */
    public JavaRootPackage() {
        super( null, "$", Optional.of( "(Top level Java package)" ) );

        this.builtinBoolean = new JavaBuiltinType( "boolean" );
        this.builtinDouble = new JavaBuiltinType( "double" );
        this.builtinFloat = new JavaBuiltinType( "float" );
        this.builtinInt = new JavaBuiltinType( "int" );
        this.builtinLong = new JavaBuiltinType( "long" );
        this.builtinVoid = new JavaBuiltinType( "void" );
    }

    @Override
    public IJavaBuiltinType getBuiltinBoolean() {
        return this.builtinBoolean;
    }

    @Override
    public IJavaBuiltinType getBuiltinDouble() {
        return this.builtinDouble;
    }

    @Override
    public IJavaBuiltinType getBuiltinFloat() {
        return this.builtinFloat;
    }

    @Override
    public IJavaBuiltinType getBuiltinInt() {
        return this.builtinInt;
    }

    @Override
    public IJavaBuiltinType getBuiltinLong() {
        return this.builtinLong;
    }

    @Override
    public IJavaBuiltinType getBuiltinVoid() {
        return this.builtinVoid;
    }

    @Override
    public String getFullyQualifiedJavaName() {
        return "";
    }

    @Override
    public String getJavaName() {
        return "";
    }

    @Override
    public JavaRootPackage getRootPackage() {
        return this;
    }

    private final JavaBuiltinType builtinBoolean;

    private final JavaBuiltinType builtinDouble;

    private final JavaBuiltinType builtinFloat;

    private final JavaBuiltinType builtinInt;

    private final JavaBuiltinType builtinLong;

    private final JavaBuiltinType builtinVoid;

}
