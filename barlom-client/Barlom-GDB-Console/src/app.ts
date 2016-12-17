import {div, main, nav, VNode} from '@cycle/dom'
import xs from 'xstream'
import {DOMSource} from '@cycle/dom/xstream-typings'
import {Stream} from 'xstream'
import TopNavigation, {TopNavigationProps} from "./components/navigation/topnavigation";

export type Sources = {
    DOM : DOMSource
}

export type Sinks = {
    DOM : Stream<VNode>
}

export function App( sources : Sources ) : Sinks {

    const topNavProps$ = xs.of<TopNavigationProps>( { title: "Barlom-GDB Console" } ).remember();

    const topNav = TopNavigation( { DOM: sources.DOM, props$: topNavProps$ } );

    const vtree$ = topNav.DOM.map(
        topNavVTree =>
            main(
                '.o-grid.o-grid--no-gutter.o-panel', [

                    div(
                        '.o-grid__cell--width-100.o-panel-container', [

                            topNavVTree,

                            div(
                                '.o-grid o-panel.o-panel--nav-top', [
                                    div( '.o-grid__cell.o-grid__cell--width-30', { id: 'left-panel' }, ["Left Panel"] ),
                                    div(
                                        '.o-grid__cell.o-grid__cell--width-70',
                                        { id: 'right-panel' },
                                        ["Right Panel"]
                                    )
                                ]
                            )

                        ]
                    )

                ]
            )
    );

    const sinks = {
        DOM: vtree$
    };

    return sinks

}
