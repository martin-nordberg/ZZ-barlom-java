/**
 * Component for the top navigation bar.
 */
import {div,nav,span} from '@cycle/dom'
import xs from 'xstream'

export default function TopNavigation(sources) {

  const vtree$ = xs.of(

    nav('.c-nav.c-nav--inline.c-nav--high', [

      div( '.c-nav__content', [ "Barlon-GDB Console" ] ),

      span( '.c-nav__item.c-nav__item--active', [ "Vertex Types" ] ),

      span( '.c-nav__item', [ "Edge Types" ] ),

      span( '.c-nav__item.c-nav__item--right', [ "Exit" ] )

    ])

  );

  const sinks = {
    DOM: vtree$
  };

  return sinks;
}


