//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * A column used as a discriminator (i.e. type indicator).
 */
public interface ISqlDiscriminatorColumn
    extends ISqlTableColumn {

    @Override
    public ISqlTable getParent();

}
