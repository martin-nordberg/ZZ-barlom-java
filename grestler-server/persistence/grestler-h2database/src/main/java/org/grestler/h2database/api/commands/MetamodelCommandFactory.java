//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.api.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.dbutilities.api.IDataSource;
import org.grestler.h2database.api.exceptions.H2DatabaseException;
import org.grestler.metamodel.api.commands.IMetamodelCommand;
import org.grestler.metamodel.api.commands.IMetamodelCommandFactory;

/**
 * Factory for metamodel commands supported by the H2 Database provider.
 */
public class MetamodelCommandFactory
    implements IMetamodelCommandFactory {

    /**
     * Constructs a new factory for creating metamodel commands.
     *
     * @param dataSource the H2 data source to use.
     */
    public MetamodelCommandFactory( IDataSource dataSource ) {
        this.dataSource = dataSource;
    }

    @Override
    public IMetamodelCommand makeCommand( String commandTypeName ) {

        switch ( commandTypeName.toLowerCase() ) {
            case "vertextypecreation":
                return new VertexTypeCreationCmd( this.dataSource );
            default:
                throw new H2DatabaseException(
                    MetamodelCommandFactory.LOG, "Unknown command type: \"" + commandTypeName + "\"."
                );
        }

    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** The data source the commands made by this factory will make use of. */
    private final IDataSource dataSource;

}
