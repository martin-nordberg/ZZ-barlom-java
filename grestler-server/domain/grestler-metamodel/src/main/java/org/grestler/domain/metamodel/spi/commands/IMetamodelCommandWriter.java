//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

/**
 * Interface defining the persistence layer for a command.
 */
@FunctionalInterface
public interface IMetamodelCommandWriter<R extends IMetamodelCommandSpi.CmdRecord> {

    /**
     * Executes this command.
     *
     * @param record      the arguments for the command as a parametric record.
     * @param cmdFinisher the command that will make the changes in memory.
     */
    void execute( R record, IMetamodelCommandSpi<R> cmdFinisher );

}
