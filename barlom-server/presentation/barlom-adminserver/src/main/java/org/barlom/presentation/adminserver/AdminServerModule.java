//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.adminserver;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import org.barlom.infrastructure.utilities.functions.IAction;

import javax.inject.Named;

/**
 * Dagger module providing admin server components.
 */
@Module(
    complete = false,
    injects = AdminServerActionsServlet.class,
    library = true
)
public class AdminServerModule {

    /**
     * Provides the admin server action servlet.
     *
     * @param dataDumpAction the action needed for a data dump.
     *
     * @return the constructed data dump action.
     */
    @Provides
    public AdminServerActionsServlet provideAdminServerActionServlet( @Named( "DataDumpAction" ) Lazy<IAction> dataDumpAction ) {
        return new AdminServerActionsServlet( dataDumpAction );
    }

}
