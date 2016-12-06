
import {div, main, nav} from '@cycle/dom'
import xs from 'xstream'

import {ISources, ISinks} from './common/cycledefinitions'


export function App( sources : ISources ) : ISinks {

    const vtree$ = xs.of(

        main( '.o-grid.o-grid--no-gutter.o-panel', [

            div( '.o-grid__cell--width-100.o-panel-container', [

                nav( '.c-nav.c-nav--inline.c-nav--high', [
                    div( '.c-nav__content', ["Barlom-GDB Console"] ),
                    div( '.c-nav__item', ["Vertex Types"] ),
                    div( '.c-nav__item', ["Edge Types"] ),
                    div( '.c-nav__item.c-nav__item--right', "Exit" )
                ] ),

                div( '.o-grid o-panel.o-panel--nav-top', [
                    div( '.o-grid__cell.o-grid__cell--width-30', { id: 'left-panel' }, ["Left Panel"] ),
                    div( '.o-grid__cell.o-grid__cell--width-70', { id: 'right-panel' }, ["Right Panel"] )
                ] )

            ] )

        ] )

    );

    const sinks = {
        DOM: vtree$
    };

    return sinks

}
