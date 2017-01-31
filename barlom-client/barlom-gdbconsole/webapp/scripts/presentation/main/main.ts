"use strict";

import {initState, update, view} from './main-window';
import {main} from "../../infrastructure/tselmenite/main";


// Find the DOM node to drop our app in.
let domNode = document.getElementById( 'app' );

if ( domNode == null ) {
    // Abandon hope if the real DOM node is not found.
    console.log( "Cannot find application DOM node." );
}
else {
    // Fire off the first loop of the lifecycle and let it cycle via handler callbacks from then on.
    main( initState(), domNode, view, update );
}
