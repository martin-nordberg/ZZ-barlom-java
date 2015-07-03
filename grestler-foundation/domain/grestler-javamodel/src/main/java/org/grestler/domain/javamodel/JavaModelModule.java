//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel;

import dagger.Module;
import dagger.Provides;
import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaPackage;
import org.grestler.domain.javamodel.api.elements.IJavaRootPackage;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerFactory;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.domain.javamodel.api.services.IJavaModelSupplierFactory;
import org.grestler.domain.javamodel.api.services.IJavaModelSupplierService;
import org.grestler.domain.javamodel.impl.elements.JavaRootPackage;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * Dagger dependency injection module.
 */
@SuppressWarnings( "ClassNamePrefixedWithPackageName" )
@Module( library = true )
public class JavaModelModule {

    /**
     * Constructs a new root package.
     *
     * @return the newly created root package.
     */
    @Provides
    @Singleton
    public IJavaRootPackage provideMetamodel() {
        return new JavaRootPackage();
    }


    public void garbage( IJavaModelElement element ) {

        String s = element.supply( new Junk() );

        element.consume( new Trash(), "yuck" );

    }

    static class Junk
        implements IJavaModelSupplierFactory<String> {

        @Override
        public IJavaModelSupplierService<String> build( final Class<? extends IJavaModelElement> elementType ) {
            if ( IJavaPackage.class.isAssignableFrom( elementType ) ) {
                return new Package();
            }
            return new ModelElement();
        }

        static class ModelElement
            implements IJavaModelSupplierService<String> {

            @Override
            public String supply( final IJavaModelElement element ) {
                return "default";
            }
        }

        static class Package
            implements IJavaModelSupplierService<String> {

            @Override
            public String supply( final IJavaModelElement element ) {
                return "package";
            }
        }
    }

    static class Trash
        implements IJavaModelConsumerFactory<String> {

        public Trash() {
            this.consumers = new HashMap<>();

            this.consumers.put( "JavaPackage", new Package() );
            // etc.
        }

        @Override
        public IJavaModelConsumerService<String> build( final Class<? extends IJavaModelElement> elementType ) {

            final String elementTypeName = elementType.getSimpleName();

            if ( !this.consumers.containsKey( elementTypeName ) ) {
                throw new IllegalArgumentException( "Unhandled Java model element type: " + elementTypeName );
            }

            return this.consumers.get( elementTypeName );
        }

        static class ModelElement
            implements IJavaModelConsumerService<String> {

            @Override
            public void consume( final IJavaModelElement element, final String value ) {

            }
        }

        static class Package
            implements IJavaModelConsumerService<String> {

            @Override
            public void consume( final IJavaModelElement element, final String value ) {

            }
        }

        private Map<String,IJavaModelConsumerService<String>> consumers;
    }
}
