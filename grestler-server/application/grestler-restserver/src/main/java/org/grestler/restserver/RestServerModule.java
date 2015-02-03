//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import dagger.Module;
import dagger.Provides;
import org.grestler.metamodel.MetamodelModule;
import org.grestler.metamodel.api.IMetamodelCommandFactory;
import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.restserver.services.metamodel.EdgeTypeQueries;
import org.grestler.restserver.services.metamodel.MetamodelCommands;
import org.grestler.restserver.services.metamodel.PackageQueries;
import org.grestler.restserver.services.metamodel.VertexTypeQueries;

import javax.json.Json;
import javax.json.JsonReaderFactory;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Dagger dependency injection module.
 */
@Module(
    complete = false,
    includes = MetamodelModule.class,
    injects = ApplicationServices.class,
    library = true )
public class RestServerModule {

    @Provides
    public EdgeTypeQueries provideEdgeTypeQueries(
        IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory
    ) {
        return new EdgeTypeQueries( metamodelRepository, jsonGeneratorFactory );
    }

    @Provides
    public JsonGeneratorFactory provideJsonGeneratorFactory() {

        // TODO: make externally configurable
        Map<String, String> generatorConfig = new HashMap<>();
        generatorConfig.put( JsonGenerator.PRETTY_PRINTING, "true" );

        return Json.createGeneratorFactory( generatorConfig );
    }

    @Provides
    public JsonReaderFactory provideJsonReader() {

        // TODO: make externally configurable
        Map<String, String> parserConfig = new HashMap<>();
        // TODO: anything needed here?

        return Json.createReaderFactory( parserConfig );
    }

    @Provides
    public MetamodelCommands provideMetamodelCommands(
        IMetamodelCommandFactory commandFactory, JsonReaderFactory jsonReaderFactory
    ) {
        // TODO: Support read-only REST server configuration with no command processing

        return new MetamodelCommands( commandFactory, jsonReaderFactory );
    }

    @Provides
    public PackageQueries providePackageQueries(
        IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory
    ) {
        return new PackageQueries( metamodelRepository, jsonGeneratorFactory );
    }

    @Provides
    public VertexTypeQueries provideVertexTypeQueries(
        IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory
    ) {
        return new VertexTypeQueries( metamodelRepository, jsonGeneratorFactory );
    }
}
