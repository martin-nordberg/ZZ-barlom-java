//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.h2database.commands;

import org.grestler.dbutilities.api.IDataSource;
import org.grestler.h2database.H2DatabaseException;
import org.grestler.metamodel.api.cmdquery.IMetamodelCommand;
import org.grestler.metamodel.api.cmdquery.IMetamodelCommandFactory;

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
                throw new H2DatabaseException( "Unknown command type: \"" + commandTypeName + "\"." );
        }

    }

    /** The data source the commands made by this factory will make use of. */
    private final IDataSource dataSource;

}
