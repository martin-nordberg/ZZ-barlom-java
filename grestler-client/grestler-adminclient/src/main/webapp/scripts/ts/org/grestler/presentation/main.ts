//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/main
 */

/*
 // TODO: not used so far, but useful to know:
 /// <reference path="../../../../../../lib/jquery-2.1.3/jquery.d.ts"/>
 /// <amd-dependency path="jquery"/>
 import $ = require( 'jquery' );
 */

import application_model_repository = require( '../application/model/repository' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export function initializeApplication() {

    application_model_repository.loadMetamodelRepository();

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

