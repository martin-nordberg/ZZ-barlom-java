
/// <reference path="./typings/history.d.ts"/>

import {run} from '@cycle/xstream-run'
import {makeDOMDriver} from '@cycle/dom'
import {App as main} from './app'
import {makeHistoryDriver} from '@cycle/history'
import {createHashHistory} from 'history';

const drivers = {
    DOM: makeDOMDriver( '#app' ),
    History: makeHistoryDriver( createHashHistory(), { capture: true } )
};

run( main, drivers );
