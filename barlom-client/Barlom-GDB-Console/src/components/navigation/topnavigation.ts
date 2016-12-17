
import {Stream} from 'xstream';
import {div, nav, span, VNode} from '@cycle/dom';
import {DOMSource} from '@cycle/dom/xstream-typings';

export interface TopNavigationProps {
    title: string;
}

export type Sources = {
    DOM: DOMSource,
    props$: Stream<TopNavigationProps>,
}

export type Sinks = {
    DOM: Stream<VNode>
}

function TopNavigation( sources: Sources ): Sinks {

    let props$: Stream<TopNavigationProps> = sources.props$;

    let vtree$ = props$
        .map( props =>
                  nav( '.c-nav.c-nav--inline.c-nav--high', [
                      div( '.c-nav__content', [props.title] ),
                      span( '.c-nav__item', ["Vertex Types"] ),
                      span( '.c-nav__item', ["Edge Types"] ),
                      span( '.c-nav__item.c-nav__item--right', "Exit" )
                  ] )
        );

    return {
        DOM: vtree$
    };
}

export default TopNavigation;