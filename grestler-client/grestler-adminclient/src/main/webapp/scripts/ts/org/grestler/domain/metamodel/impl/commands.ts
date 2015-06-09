//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/impl/commands
 */

import api_commands = require( '../api/commands' );
import api_elements = require( '../api/elements' );
import spi_commands = require( '../spi/commands' );
import spi_queries = require( '../spi/queries' );
import values = require( '../../../infrastructure/utilities/values' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Partial implementation of IMetamodelCommand.
 */
class AbstractMetamodelCommand implements api_commands.IMetamodelCommand, spi_commands.IMetamodelCommandSpi {

    /**
     * Constructs a new command.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi,
        cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        this._cmdWriter = cmdWriter;
        this._metamodelRepository = metamodelRepository;
    }

    public execute( jsonCmdArgs : any ) : Promise<values.ENothing> {
        return this._cmdWriter.execute( jsonCmdArgs, this );
    }

    public finish( jsonCmdArgs : any ) : void {
        this.writeChangesToMetamodel( jsonCmdArgs );
    }

    /**
     * @return The metamodel repository to be acted upon by this command.
     */
    public get metamodelRepository() : spi_queries.IMetamodelRepositorySpi {
        return this._metamodelRepository;
    }

    /**
     * Makes the in-memory changes needed to complete this command.
     *
     * @param jsonCmdArgs the JSON for the changes.
     */
    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {
        throw new Error( "Abstract method must be implemented in a derived class." );
    }

    /** The persistence provider for this command. */
    private _cmdWriter : spi_commands.IMetamodelCommandWriter;

    /** The metamodel repository to be acted upon by this command. */
    private _metamodelRepository : spi_queries.IMetamodelRepositorySpi;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to create a directed edge type.
 */
class DirectedEdgeTypeCreationCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the package attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var parentPackageId : string = jsonCmdArgs.parentPackageId;
        var name : string = jsonCmdArgs.name;
        var superTypeId : string = jsonCmdArgs.superTypeId;
        var abstractness = api_elements.abstractnessFromString( jsonCmdArgs.abstractness );
        var cyclicity = api_elements.cyclicityFromString( jsonCmdArgs.cyclicity );
        var multiEdgedness = api_elements.multiEdgednessFromString( jsonCmdArgs.multiEdgedness );
        var selfLooping = api_elements.selfLoopingFromString( jsonCmdArgs.selfLooping );
        var tailVertexTypeId : string = jsonCmdArgs.tailVertexTypeId;
        var headVertexTypeId : string = jsonCmdArgs.headVertexTypeId;
        var tailRoleName : string = jsonCmdArgs.tailRoleName;
        var headRoleName : string = jsonCmdArgs.headRoleName;
        var minTailOutDegree : number = jsonCmdArgs.minTailOutDegree;
        var maxTailOutDegree : number = jsonCmdArgs.maxTailOutDegree;
        var minHeadInDegree : number = jsonCmdArgs.minHeadInDegree;
        var maxHeadInDegree : number = jsonCmdArgs.maxHeadInDegree;

        // Look up the related parent package.
        var parentPackage : api_elements.IPackage = this.metamodelRepository.findPackageById( parentPackageId );
        var superType : api_elements.IDirectedEdgeType = this.metamodelRepository.findDirectedEdgeTypeById( superTypeId );
        var tailVertexType : api_elements.IVertexType = this.metamodelRepository.findVertexTypeById( tailVertexTypeId );
        var headVertexType : api_elements.IVertexType = this.metamodelRepository.findVertexTypeById( headVertexTypeId );

        // Create the new package.
        this.metamodelRepository.loadDirectedEdgeType(
            id,
            parentPackage,
            name,
            superType,
            abstractness,
            cyclicity,
            multiEdgedness,
            selfLooping,
            tailVertexType,
            headVertexType,
            tailRoleName,
            headRoleName,
            minTailOutDegree,
            maxTailOutDegree,
            minHeadInDegree,
            maxHeadInDegree
        )
        ;

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to create a package.
 */
class PackageCreationCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the package attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var parentPackageId : string = jsonCmdArgs.parentPackageId;
        var name : string = jsonCmdArgs.name;

        // Look up the related parent package.
        var parentPackage : api_elements.IPackage = this.metamodelRepository.findPackageById( parentPackageId );

        // Create the new package.
        this.metamodelRepository.loadPackage( id, parentPackage, name );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to rename a packaged element.
 */
class PackagedElementNameChangeCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the package attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var name : string = jsonCmdArgs.name;

        // Change the name.
        this.metamodelRepository.findPackagedElementById( id ).name = name;

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to create an undirected edge type.
 */
class UndirectedEdgeTypeCreationCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the edge type attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var parentPackageId : string = jsonCmdArgs.parentPackageId;
        var name : string = jsonCmdArgs.name;
        var superTypeId : string = jsonCmdArgs.superTypeId;
        var abstractness = api_elements.abstractnessFromString( jsonCmdArgs.abstractness );
        var cyclicity = api_elements.cyclicityFromString( jsonCmdArgs.cyclicity );
        var multiEdgedness = api_elements.multiEdgednessFromString( jsonCmdArgs.multiEdgedness );
        var selfLooping = api_elements.selfLoopingFromString( jsonCmdArgs.selfLooping );
        var vertexTypeId : string = jsonCmdArgs.vertexTypeId;
        var minDegree : number = jsonCmdArgs.minDegree;
        var maxDegree : number = jsonCmdArgs.maxDegree;

        // Look up the related elements.
        var parentPackage : api_elements.IPackage = this.metamodelRepository.findPackageById( parentPackageId );
        var superType : api_elements.IUndirectedEdgeType = this.metamodelRepository.findUndirectedEdgeTypeById( superTypeId );
        var vertexType : api_elements.IVertexType = this.metamodelRepository.findVertexTypeById( vertexTypeId );

        // Create the new edge type.
        this.metamodelRepository.loadUndirectedEdgeType(
            id,
            parentPackage,
            name,
            superType,
            abstractness,
            cyclicity,
            multiEdgedness,
            selfLooping,
            vertexType,
            minDegree,
            maxDegree
        );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to create a vertex type.
 */
class VertexTypeCreationCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the vertex attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var parentPackageId : string = jsonCmdArgs.parentPackageId;
        var name : string = jsonCmdArgs.name;
        var superTypeId : string = jsonCmdArgs.superTypeId;
        var abstractness = api_elements.abstractnessFromString( jsonCmdArgs.abstractness );

        // Look up the related elements.
        var parentPackage : api_elements.IPackage = this.metamodelRepository.findPackageById( parentPackageId );
        var superType : api_elements.IVertexType = this.metamodelRepository.findVertexTypeById( superTypeId );

        // Create the new vertex type.
        this.metamodelRepository.loadVertexType( id, parentPackage, name, superType, abstractness );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to change the abstractness of a vertex type.
 */
class VertexTypeAbstractnessChangeCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the package attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var abstractness : string = jsonCmdArgs.abstractness;

        // Make the change.
        this.metamodelRepository.findVertexTypeById( id ).abstractness = api_elements.EAbstractness[abstractness];

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to change the super type of a vertex type.
 */
class VertexTypeSuperTypeChangeCmd extends AbstractMetamodelCommand {

    /**
     * Constructs a new command.
     *
     * @param metamodelRepository the repository the command will act upon.
     * @param cmdWriter           the command's persistence provider.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi, cmdWriter : spi_commands.IMetamodelCommandWriter
    ) {
        super( metamodelRepository, cmdWriter );
    }

    public writeChangesToMetamodel( jsonCmdArgs : any ) : void {

        // Extract the package attributes from the command JSON.
        var id : string = jsonCmdArgs.id;
        var superTypeId : string = jsonCmdArgs.superTypeId;

        // Make the change.
        this.metamodelRepository.findVertexTypeById( id ).superType =
            this.metamodelRepository.findVertexTypeById( superTypeId );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Factory for metamodel commands.
 */
export class MetamodelCommandFactory implements api_commands.IMetamodelCommandFactory {

    /**
     * Constructs a new factory for creating metamodel commands.
     *
     * @param metamodelRepository the metamodel repository the commands will act upon.
     * @param cmdWriterFactory the factory for creating the persistence service for the command.
     */
    constructor(
        metamodelRepository : spi_queries.IMetamodelRepositorySpi,
        cmdWriterFactory : spi_commands.IMetamodelCommandWriterFactory
    ) {
        this._metamodelRepository = metamodelRepository;
        this._cmdWriterFactory = cmdWriterFactory;
    }

    public makeCommand( commandTypeName : string ) : api_commands.IMetamodelCommand {

        var cmdWriter : spi_commands.IMetamodelCommandWriter = this._cmdWriterFactory.makeCommandWriter(
            commandTypeName
        );

        switch ( commandTypeName.toLowerCase() ) {
            case "directededgetypecreation":
                return new DirectedEdgeTypeCreationCmd( this._metamodelRepository, cmdWriter );
            case "packagecreation":
                return new PackageCreationCmd( this._metamodelRepository, cmdWriter );
            case "packagedelementnamechange":
                return new PackagedElementNameChangeCmd( this._metamodelRepository, cmdWriter );
            case "undirectededgetypecreation":
                return new UndirectedEdgeTypeCreationCmd( this._metamodelRepository, cmdWriter );
            case "vertextypeabstractnesschange":
                return new VertexTypeAbstractnessChangeCmd( this._metamodelRepository, cmdWriter );
            case "vertextypecreation":
                return new VertexTypeCreationCmd( this._metamodelRepository, cmdWriter );
            case "vertextypesupertypechange":
                return new VertexTypeSuperTypeChangeCmd( this._metamodelRepository, cmdWriter );
            default:
                throw new Error( "Unknown command type: \"" + commandTypeName + "\"." );
        }

    }

    /** Factory to create the writers needed for commands. */
    private _cmdWriterFactory : spi_commands.IMetamodelCommandWriterFactory;

    /** The data source the commands made by this factory will make use of. */
    private _metamodelRepository : spi_queries.IMetamodelRepositorySpi;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

