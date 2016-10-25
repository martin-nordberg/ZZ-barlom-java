//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/infrastructure/utilties/ajax
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Make an AJAX GET call returning a promise which will give back the response text.
 * @param url the URL to get.
 * @returns {Promise} promise with the text response as its eventual value.
 */
export function httpGet( url : string ) : Promise<string> {

    // Return a new promise.
    return new Promise<string>(
        function ( resolve : ( value? : string ) => void, reject : ( error? : any ) => void ) {

            // Do the usual XHR stuff.
            var req = new XMLHttpRequest();
            req.open( 'GET', url, true );

            // Handle the HTTP response.
            req.onload = function () {
                // Check the status.
                if ( req.status == 200 ) {
                    // Resolve the promise with the response text.
                    resolve( req.response );
                }
                else {
                    // Otherwise reject with the status text which will hopefully be a meaningful error.
                    reject( new Error( req.statusText ) );
                }
            };

            // Handle network errors.
            req.onerror = function () {
                reject( new Error( "Network Error" ) );
            };

            // Make the request.
            req.send();

        }
    );

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Make an AJAX POST call returning a promise which will give back the response text.
 * @param url the URL to get.
 * @param contentType the value for the Content-Type header.
 * @param content the request body to post.
 * @returns {Promise} promise with the text response as its eventual value.
 */
export function httpPost( url : string, contentType : string, content : string ) : Promise<string> {

    // Return a new promise.
    return new Promise<string>(
        function ( resolve : ( value? : string ) => void, reject : ( error? : any ) => void ) {

            // Do the usual XHR stuff.
            var req = new XMLHttpRequest();
            req.open( 'POST', url, true );
            req.setRequestHeader( 'Content-type', contentType );

            // Handle a normal HTTP response.
            req.onreadystatechange = function () {
                if ( req.readyState == 4 ) {
                    // Check the status.
                    if ( req.status == 200 ) {
                        // Resolve the promise with the response text.
                        resolve( req.response );
                    }
                    else {
                        // Otherwise reject with the status text which will hopefully be a meaningful error.
                        reject( new Error( req.statusText ) );
                    }
                }
            };

            // Handle network errors.
            req.onerror = function () {
                reject( new Error( "Network Error" ) );
            };

            // Make the request.
            req.send( content );

        }
    );

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
