
import {VNode} from '@cycle/dom'
import {DOMSource} from '@cycle/dom/xstream-typings'
import {Stream} from 'xstream'

export type ISources = {
    DOM: DOMSource
}

export type ISinks = {
    DOM: Stream<VNode>
}
