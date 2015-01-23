//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import javax.json.stream.JsonGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the top level root package.
 */
public class RootPackage
    implements IPackage, IPackageSpi {

    /**
     * Constructs a new root package.
     *
     * @param id the unique ID of the root package.
     */
    public RootPackage( UUID id ) {

        this.id = id;

        this.childPackages = new ArrayList<>();
        this.edgeTypes = new ArrayList<>();
        this.vertexTypes = new ArrayList<>();

    }

    @Override
    public void addChildPackage( IPackage pkg ) {
        this.childPackages.add( pkg );
    }

    @Override
    public void addEdgeType( IEdgeType edgeType ) {
        this.edgeTypes.add( edgeType );
    }

    @Override
    public void addVertexType( IVertexType vertexType ) {
        this.vertexTypes.add( vertexType );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() ).writeNull( "parentPackageId" ).write( "name", "$" ).write( "path", "" );
    }

    @Override
    public List<IPackage> getChildPackages() {
        return this.childPackages;
    }

    @Override
    public List<IEdgeType> getEdgeTypes() {
        return this.edgeTypes;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return "$";
    }

    @Override
    public IPackage getParentPackage() {
        return this;
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public List<IVertexType> getVertexTypes() {
        return this.vertexTypes;
    }

    @Override
    public boolean isChildOf( IPackage parentPackage ) {
        return false;
    }


    /** The sub-packages of this package. */
    private final List<IPackage> childPackages;

    /** The edge types within this package. */
    private final List<IEdgeType> edgeTypes;

    /** The unique ID of this root package. */
    private final UUID id;

    /** The vertex types within this package. */
    private final List<IVertexType> vertexTypes;

}
