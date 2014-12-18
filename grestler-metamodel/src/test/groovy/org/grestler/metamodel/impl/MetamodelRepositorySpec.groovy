//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl

import org.grestler.metamodel.spi.IMetamodelRepositorySpi
import org.grestler.utilities.revisions.StmTransactionContext
import org.grestler.utilities.uuids.Uuids
import spock.lang.Specification

/**
 * Specification for a meatmodel repository.
 */
class MetamodelRepositorySpec
        extends Specification {

    UUID id1 = Uuids.makeUuid();

    def "A metamodel repository lets added vertex types be retrieved"() {

        given:
        IMetamodelRepositorySpi m
        StmTransactionContext.doInTransaction( 1 ) {
            m = new MetamodelRepository();

            m.loadVertexType( id1, "V1", Optional.empty() );
            m.loadVertexType( Uuids.makeUuid(), "V2", Optional.empty() );
            m.loadVertexType( Uuids.makeUuid(), "V3", Optional.empty() );
            m.loadVertexType( Uuids.makeUuid(), "V4", Optional.empty() );
        }

        expect:
        StmTransactionContext.doInTransaction( 1 ) {
            assert m.findVertexTypeById( id1 ).get().name == "V1";
            assert m.findVertexTypeByName( "V2" ).get().name == "V2";
            assert m.findVertexTypesAll().size() == 4;
        }

    }

}
