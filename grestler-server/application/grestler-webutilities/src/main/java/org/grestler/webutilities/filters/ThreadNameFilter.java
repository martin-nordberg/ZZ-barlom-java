//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.webutilities.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Servlet filter sets the current thread name to the request URL. This benefits logging and debugging which then double
 * the request URL in the thread name.
 */
public class ThreadNameFilter
    implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException {

        // Determine the request URL.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if ( httpRequest.getQueryString() != null ) {
            path += "?" + httpRequest.getQueryString();
        }

        // Set the thread name.
        Thread.currentThread().setName( path );

        // Pass along the request.
        chain.doFilter( request, response );
    }

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {
    }

}
