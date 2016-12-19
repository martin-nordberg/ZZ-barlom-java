import {Stream} from 'xstream';
import {div, nav, span, VNode} from '@cycle/dom';
import {DOMSource} from '@cycle/dom/xstream-typings';

export interface IProps {
    title : string;
}

export interface ISources {
    DOM : DOMSource,
    props$ : Stream<IProps>,
}

export interface Sinks {
    DOM : Stream<VNode>
}

export default function TopNavigation( sources : ISources ) : Sinks {

    let props$ : Stream<IProps> = sources.props$;

    let vtree$ = props$.map( props =>
        nav(
            '.c-nav.c-nav--inline.c-nav--high', [
                div( '.c-nav__content', [props.title] ),
                span( '.c-nav__item', ["Vertex Types"] ),
                span( '.c-nav__item', ["Edge Types"] ),
                span( '.c-nav__item.c-nav__item--right', "Exit" )
            ]
        )
    );

    return {
        DOM: vtree$
    };

}
