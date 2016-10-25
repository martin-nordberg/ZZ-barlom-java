//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel;

import dagger.Module;
import org.barlom.domain.sqlmodel.api.elements.ISqlDataModel;
import org.barlom.domain.sqlmodel.impl.elements.SqlDataModel;

/**
 * Dagger dependency injection module.
 */
@Module( library = true )
public class SqlModelModule {

    /**
     * Provides a new SQL data model instance with given name a short description.
     *
     * @return the newly created top level model element.
     */
    ISqlDataModel provideSqlDataModel() {
        return new SqlDataModel();
    }

}
