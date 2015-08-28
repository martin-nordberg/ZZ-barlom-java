//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandSpi;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriter;
import org.grestler.domain.metamodel.spi.commands.IMetamodelCommandWriterFactory;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.api.exceptions.H2DatabaseException;

/**
 * Factory for metamodel commands supported by the H2 Database provider.
 */
public class MetamodelCommandWriterFactory
    implements IMetamodelCommandWriterFactory {

    /**
     * Constructs a new factory for creating metamodel commands.
     *
     * @param dataSource the H2 data source to use.
     */
    public MetamodelCommandWriterFactory( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public IMetamodelCommandWriter<? extends IMetamodelCommandSpi.CmdRecord> makeCommandWriter( String commandTypeName ) {

        switch ( commandTypeName.toLowerCase() ) {
            case "directededgetypecreation":
                return new DirectedEdgeTypeCreationCmdWriter( this.dataSource );
            case "edgetypeabstractnesschange":
                return new EdgeTypeAbstractnessChangeCmdWriter( this.dataSource );
            case "edgetypecyclicitychange":
                return new EdgeTypeCyclicityChangeCmdWriter( this.dataSource );
            case "edgetypemultiedgednesschange":
                return new EdgeTypeMultiEdgednessChangeCmdWriter( this.dataSource );
            case "packagecreation":
                return new PackageCreationCmdWriter( this.dataSource );
            case "packagedelementnamechange":
                return new PackagedElementNameChangeCmdWriter( this.dataSource );
            case "undirectededgetypecreation":
                return new UndirectedEdgeTypeCreationCmdWriter( this.dataSource );
            case "vertextypeabstractnesschange":
                return new VertexTypeAbstractnessChangeCmdWriter( this.dataSource );
            case "vertextypecreation":
                return new VertexTypeCreationCmdWriter( this.dataSource );
            case "vertextypesupertypechange":
                return new VertexTypeSuperTypeChangeCmdWriter( this.dataSource );
            default:
                throw new H2DatabaseException(
                    MetamodelCommandWriterFactory.LOG, "Unknown command type: \"" + commandTypeName + "\"."
                );
        }

    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** The data source the commands made by this factory will make use of. */
    private final IDataSource dataSource;

}
