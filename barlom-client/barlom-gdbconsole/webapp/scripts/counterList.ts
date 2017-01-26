"use strict";

import h from './lib/snabbdom/src/h';
import { VNode } from './lib/snabbdom/src/vnode';
import counter from './counter';

// ACTIONS

const ADD     = 'add';
const UPDATE  = 'update counter';
const REMOVE  = 'remove';
const RESET   = 'reset';

const resetAction = { type: counter.INIT };


// MODEL

/*  model : {
 counters: [{id: Number, counter: counter.model}],
 nextID  : Number
 }
 */


// VIEW

/**
 * Generates the mark up for the list of counters.
 * @param model the state of the counters.
 * @param handler event handling.
 */
function view( model, handler ) : VNode {
  return h(
    'div', [
      h(
        'button', {
          on: { click: handler.bind( null, {type: ADD} ) }
        }, 'Add'
      ),
      h(
        'button', {
          on: { click: handler.bind( null, {type: RESET} ) }
        }, 'Reset'
      ),
      h( 'hr' ),
      h( 'div.counter-list', model.counters.map( item => counterItemView( item, handler ) ) )

    ]
  );
}

/**
 * Generates the mark up for one counter plus a remove button.
 * @param item one entry in the counters array.
 * @param handler the master event handler
 */
function counterItemView( item, handler ) {
  return h(
    'div.counter-item', { key: item.id }, [
      h(
        'button.remove', {
          on: { click: handler.bind( null, { type: REMOVE, id: item.id} ) }
        }, 'Remove'
      ),
      counter.view( item.counter, a => handler( {type: UPDATE, id: item.id, data: a} ) ),
      h( 'hr' )
    ]
  );
}

// UPDATE

function update( model, action ) {

    return  action.type === ADD     ? addCounter(model)
          : action.type === RESET   ? resetCounters(model)
          : action.type === REMOVE  ? removeCounter(model, action.id)
          : action.type === UPDATE  ? updateCounter(model, action.id, action.data)
          : model;

}

function addCounter( model ) {
  const newCounter = { id: model.nextID, counter: counter.update( null, resetAction ) };
  return {
    counters: [...model.counters, newCounter],
    nextID: model.nextID + 1
  };
}


function resetCounters( model ) {

  return {
    ...model,
    counters: model.counters.map(
      item => ({
        ...item,
        counter: counter.update( item.counter, resetAction )
      })
    )
  };
}

function removeCounter( model, id ) {
  return {
    ...model,
    counters: model.counters.filter( item => item.id !== id )
  };
}

function updateCounter( model, id, action ) {
  return {
    ...model,
    counters: model.counters.map(
      item =>
        item.id !== id ?
          item
          : {
            ...item,
            counter: counter.update( item.counter, action )
          }
    )
  };
}

export default { view, update };
