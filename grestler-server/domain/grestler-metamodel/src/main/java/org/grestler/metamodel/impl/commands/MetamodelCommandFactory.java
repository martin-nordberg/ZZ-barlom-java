//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.commands.IMetamodelCommand;
import org.grestler.metamodel.api.commands.IMetamodelCommandFactory;
import org.grestler.metamodel.api.exceptions.MetamodelException;
import org.grestler.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.metamodel.spi.commands.IMetamodelCommandWriterFactory;
import org.grestler.metamodel.spi.queries.IMetamodelRepositorySpi;

/**
 * Factory for metamodel commands.
 */
public class MetamodelCommandFactory
    implements IMetamodelCommandFactory {

    /**
     * Constructs a new factory for creating metamodel commands.
     *
     * @param metamodelRepository the metamodel repository the commands will act upon.
     */
    public MetamodelCommandFactory(
        IMetamodelRepositorySpi metamodelRepository, IMetamodelCommandWriterFactory cmdWriterFactory
    ) {
        this.metamodelRepository = metamodelRepository;
        this.cmdWriterFactory = cmdWriterFactory;
    }

    @Override
    public IMetamodelCommand makeCommand( String commandTypeName ) {

        IMetamodelCommandWriter cmdWriter = this.cmdWriterFactory.makeCommandWriter(
            commandTypeName
        );

        switch ( commandTypeName.toLowerCase() ) {
            case "packagecreation":
                return new PackageCreationCmd( this.metamodelRepository, cmdWriter );
            case "vertextypecreation":
                return new VertexTypeCreationCmd( this.metamodelRepository, cmdWriter );
            default:
                throw new MetamodelException(
                    MetamodelCommandFactory.LOG, "Unknown command type: \"" + commandTypeName + "\"."
                );
        }

    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** Factory to create the writers needed for commands. */
    private final IMetamodelCommandWriterFactory cmdWriterFactory;

    /** The data source the commands made by this factory will make use of. */
    private final IMetamodelRepositorySpi metamodelRepository;

}
