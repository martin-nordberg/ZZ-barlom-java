import {div, main} from '@cycle/dom'
import xs from 'xstream'
import TopNavigation from './navigation/topnavigation'

export function App( sources ) {

  const topNavSources = { DOM: sources.DOM, history: sources.history };

  const topNav = TopNavigation( topNavSources );

  const topNavVDom$ = topNav.DOM;

  const vtree$ = xs.combine( topNavVDom$ )
      .map(
          ( [topNavVDom] ) =>

          main( '.o-grid.o-grid--no-gutter.o-panel', [

              div( '.o-grid__cell--width-100.o-panel-container', [

                  topNavVDom,

                  div( '.o-grid.o-panel.o-panel--nav-top', [

                      div( '.o-grid__cell.o-grid__cell--width-30', [
                          "Left Panel"
                      ] ),

                      div( '.o-grid__cell.o-grid__cell--width-70', [
                          "Right Panel"
                      ] )
                  ] )

              ] )

          ] )
      );

  const sinks = {
    DOM: vtree$
  };

  return sinks;
}


