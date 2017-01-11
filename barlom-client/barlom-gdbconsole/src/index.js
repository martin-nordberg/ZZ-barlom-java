import {run} from '@cycle/xstream-run'
import {makeDOMDriver} from '@cycle/dom'
import {App} from './app'
import {makeHistoryDriver} from "@cycle/history/lib/makeHistoryDriver";
import {createHistory} from 'history';

const drivers = {
  DOM: makeDOMDriver('#app'),
  history: makeHistoryDriver(createHistory(), {capture: true}),
};

run( App, drivers );
