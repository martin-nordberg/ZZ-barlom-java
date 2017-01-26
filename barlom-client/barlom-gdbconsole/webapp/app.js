(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
"use strict";
const h_1 = require("./lib/snabbdom/src/h");
const INC = 'inc';
const DEC = 'dec';
const INIT = 'init';
// model : Number
function view(count, handler) {
    return h_1.default('div', [
        h_1.default('button', {
            on: { click: handler.bind(null, { type: INC }) }
        }, '+'),
        h_1.default('button', {
            on: { click: handler.bind(null, { type: DEC }) }
        }, '-'),
        h_1.default('div', `Count : ${count}`),
    ]);
}
function update(count, action) {
    return action.type === INC ? count + 1
        : action.type === DEC ? count - 1
            : action.type === INIT ? 0
                : count;
}
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = { view, update, INIT };

},{"./lib/snabbdom/src/h":3}],2:[function(require,module,exports){
"use strict";
var __assign = (this && this.__assign) || Object.assign || function(t) {
    for (var s, i = 1, n = arguments.length; i < n; i++) {
        s = arguments[i];
        for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
            t[p] = s[p];
    }
    return t;
};
const h_1 = require("./lib/snabbdom/src/h");
const counter_1 = require("./counter");
// ACTIONS
const ADD = 'add';
const UPDATE = 'update counter';
const REMOVE = 'remove';
const RESET = 'reset';
const resetAction = { type: counter_1.default.INIT };
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
function view(model, handler) {
    return h_1.default('div', [
        h_1.default('button', {
            on: { click: handler.bind(null, { type: ADD }) }
        }, 'Add'),
        h_1.default('button', {
            on: { click: handler.bind(null, { type: RESET }) }
        }, 'Reset'),
        h_1.default('hr'),
        h_1.default('div.counter-list', model.counters.map(item => counterItemView(item, handler)))
    ]);
}
/**
 * Generates the mark up for one counter plus a remove button.
 * @param item one entry in the counters array.
 * @param handler the master event handler
 */
function counterItemView(item, handler) {
    return h_1.default('div.counter-item', { key: item.id }, [
        h_1.default('button.remove', {
            on: { click: handler.bind(null, { type: REMOVE, id: item.id }) }
        }, 'Remove'),
        counter_1.default.view(item.counter, a => handler({ type: UPDATE, id: item.id, data: a })),
        h_1.default('hr')
    ]);
}
// UPDATE
function update(model, action) {
    return action.type === ADD ? addCounter(model)
        : action.type === RESET ? resetCounters(model)
            : action.type === REMOVE ? removeCounter(model, action.id)
                : action.type === UPDATE ? updateCounter(model, action.id, action.data)
                    : model;
}
function addCounter(model) {
    const newCounter = { id: model.nextID, counter: counter_1.default.update(null, resetAction) };
    return {
        counters: [...model.counters, newCounter],
        nextID: model.nextID + 1
    };
}
function resetCounters(model) {
    return __assign({}, model, { counters: model.counters.map(item => (__assign({}, item, { counter: counter_1.default.update(item.counter, resetAction) }))) });
}
function removeCounter(model, id) {
    return __assign({}, model, { counters: model.counters.filter(item => item.id !== id) });
}
function updateCounter(model, id, action) {
    return __assign({}, model, { counters: model.counters.map(item => item.id !== id ?
            item
            : __assign({}, item, { counter: counter_1.default.update(item.counter, action) })) });
}
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = { view, update };

},{"./counter":1,"./lib/snabbdom/src/h":3}],3:[function(require,module,exports){
"use strict";
const vnode_1 = require("./vnode");
const is = require("./is");
function addNS(data, children, sel) {
    data.ns = 'http://www.w3.org/2000/svg';
    if (sel !== 'foreignObject' && children !== undefined) {
        for (let i = 0; i < children.length; ++i) {
            let childData = children[i].data;
            if (childData !== undefined) {
                addNS(childData, children[i].children, children[i].sel);
            }
        }
    }
}
function h(sel, b, c) {
    var data = {}, children, text, i;
    if (c !== undefined) {
        data = b;
        if (is.array(c)) {
            children = c;
        }
        else if (is.primitive(c)) {
            text = c;
        }
        else if (c && c.sel) {
            children = [c];
        }
    }
    else if (b !== undefined) {
        if (is.array(b)) {
            children = b;
        }
        else if (is.primitive(b)) {
            text = b;
        }
        else if (b && b.sel) {
            children = [b];
        }
        else {
            data = b;
        }
    }
    if (is.array(children)) {
        for (i = 0; i < children.length; ++i) {
            if (is.primitive(children[i]))
                children[i] = vnode_1.vnode(undefined, undefined, undefined, children[i]);
        }
    }
    if (sel[0] === 's' && sel[1] === 'v' && sel[2] === 'g' &&
        (sel.length === 3 || sel[3] === '.' || sel[3] === '#')) {
        addNS(data, children, sel);
    }
    return vnode_1.vnode(sel, data, children, text, undefined);
}
exports.h = h;
;
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = h;

},{"./is":5,"./vnode":12}],4:[function(require,module,exports){
"use strict";
function createElement(tagName) {
    return document.createElement(tagName);
}
function createElementNS(namespaceURI, qualifiedName) {
    return document.createElementNS(namespaceURI, qualifiedName);
}
function createTextNode(text) {
    return document.createTextNode(text);
}
function insertBefore(parentNode, newNode, referenceNode) {
    parentNode.insertBefore(newNode, referenceNode);
}
function removeChild(node, child) {
    node.removeChild(child);
}
function appendChild(node, child) {
    node.appendChild(child);
}
function parentNode(node) {
    return node.parentNode;
}
function nextSibling(node) {
    return node.nextSibling;
}
function tagName(elm) {
    return elm.tagName;
}
function setTextContent(node, text) {
    node.textContent = text;
}
exports.htmlDomApi = {
    createElement,
    createElementNS,
    createTextNode,
    insertBefore,
    removeChild,
    appendChild,
    parentNode,
    nextSibling,
    tagName,
    setTextContent,
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = exports.htmlDomApi;

},{}],5:[function(require,module,exports){
"use strict";
exports.array = Array.isArray;
function primitive(s) {
    return typeof s === 'string' || typeof s === 'number';
}
exports.primitive = primitive;

},{}],6:[function(require,module,exports){
"use strict";
function updateClass(oldVnode, vnode) {
    var cur, name, elm = vnode.elm, oldClass = oldVnode.data.class, klass = vnode.data.class;
    if (!oldClass && !klass)
        return;
    if (oldClass === klass)
        return;
    oldClass = oldClass || {};
    klass = klass || {};
    for (name in oldClass) {
        if (!klass[name]) {
            elm.classList.remove(name);
        }
    }
    for (name in klass) {
        cur = klass[name];
        if (cur !== oldClass[name]) {
            elm.classList[cur ? 'add' : 'remove'](name);
        }
    }
}
exports.classModule = { create: updateClass, update: updateClass };
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = exports.classModule;

},{}],7:[function(require,module,exports){
"use strict";
function invokeHandler(handler, vnode, event) {
    if (typeof handler === "function") {
        // call function handler
        handler.call(vnode, event, vnode);
    }
    else if (typeof handler === "object") {
        // call handler with arguments
        if (typeof handler[0] === "function") {
            // special case for single argument for performance
            if (handler.length === 2) {
                handler[0].call(vnode, handler[1], event, vnode);
            }
            else {
                var args = handler.slice(1);
                args.push(event);
                args.push(vnode);
                handler[0].apply(vnode, args);
            }
        }
        else {
            // call multiple handlers
            for (var i = 0; i < handler.length; i++) {
                invokeHandler(handler[i]);
            }
        }
    }
}
function handleEvent(event, vnode) {
    var name = event.type, on = vnode.data.on;
    // call event handler(s) if exists
    if (on && on[name]) {
        invokeHandler(on[name], vnode, event);
    }
}
function createListener() {
    return function handler(event) {
        handleEvent(event, handler.vnode);
    };
}
function updateEventListeners(oldVnode, vnode) {
    var oldOn = oldVnode.data.on, oldListener = oldVnode.listener, oldElm = oldVnode.elm, on = vnode && vnode.data.on, elm = (vnode && vnode.elm), name;
    // optimization for reused immutable handlers
    if (oldOn === on) {
        return;
    }
    // remove existing listeners which no longer used
    if (oldOn && oldListener) {
        // if element changed or deleted we remove all existing listeners unconditionally
        if (!on) {
            for (name in oldOn) {
                // remove listener if element was changed or existing listeners removed
                oldElm.removeEventListener(name, oldListener, false);
            }
        }
        else {
            for (name in oldOn) {
                // remove listener if existing listener removed
                if (!on[name]) {
                    oldElm.removeEventListener(name, oldListener, false);
                }
            }
        }
    }
    // add new listeners which has not already attached
    if (on) {
        // reuse existing listener or create new
        var listener = vnode.listener = oldVnode.listener || createListener();
        // update vnode for listener
        listener.vnode = vnode;
        // if element changed or added we add all needed listeners unconditionally
        if (!oldOn) {
            for (name in on) {
                // add listener if element was changed or new listeners added
                elm.addEventListener(name, listener, false);
            }
        }
        else {
            for (name in on) {
                // add listener if new listener added
                if (!oldOn[name]) {
                    elm.addEventListener(name, listener, false);
                }
            }
        }
    }
}
exports.eventListenersModule = {
    create: updateEventListeners,
    update: updateEventListeners,
    destroy: updateEventListeners
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = exports.eventListenersModule;

},{}],8:[function(require,module,exports){
"use strict";
function updateProps(oldVnode, vnode) {
    var key, cur, old, elm = vnode.elm, oldProps = oldVnode.data.props, props = vnode.data.props;
    if (!oldProps && !props)
        return;
    if (oldProps === props)
        return;
    oldProps = oldProps || {};
    props = props || {};
    for (key in oldProps) {
        if (!props[key]) {
            delete elm[key];
        }
    }
    for (key in props) {
        cur = props[key];
        old = oldProps[key];
        if (old !== cur && (key !== 'value' || elm[key] !== cur)) {
            elm[key] = cur;
        }
    }
}
exports.propsModule = { create: updateProps, update: updateProps };
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = exports.propsModule;

},{}],9:[function(require,module,exports){
"use strict";
var raf = (typeof window !== 'undefined' && window.requestAnimationFrame) || setTimeout;
var nextFrame = function (fn) { raf(function () { raf(fn); }); };
function setNextFrame(obj, prop, val) {
    nextFrame(function () { obj[prop] = val; });
}
function updateStyle(oldVnode, vnode) {
    var cur, name, elm = vnode.elm, oldStyle = oldVnode.data.style, style = vnode.data.style;
    if (!oldStyle && !style)
        return;
    if (oldStyle === style)
        return;
    oldStyle = oldStyle || {};
    style = style || {};
    var oldHasDel = 'delayed' in oldStyle;
    for (name in oldStyle) {
        if (!style[name]) {
            if (name.startsWith('--')) {
                elm.style.removeProperty(name);
            }
            else {
                elm.style[name] = '';
            }
        }
    }
    for (name in style) {
        cur = style[name];
        if (name === 'delayed') {
            for (name in style.delayed) {
                cur = style.delayed[name];
                if (!oldHasDel || cur !== oldStyle.delayed[name]) {
                    setNextFrame(elm.style, name, cur);
                }
            }
        }
        else if (name !== 'remove' && cur !== oldStyle[name]) {
            if (name.startsWith('--')) {
                elm.style.setProperty(name, cur);
            }
            else {
                elm.style[name] = cur;
            }
        }
    }
}
function applyDestroyStyle(vnode) {
    var style, name, elm = vnode.elm, s = vnode.data.style;
    if (!s || !(style = s.destroy))
        return;
    for (name in style) {
        elm.style[name] = style[name];
    }
}
function applyRemoveStyle(vnode, rm) {
    var s = vnode.data.style;
    if (!s || !s.remove) {
        rm();
        return;
    }
    var name, elm = vnode.elm, i = 0, compStyle, style = s.remove, amount = 0, applied = [];
    for (name in style) {
        applied.push(name);
        elm.style[name] = style[name];
    }
    compStyle = getComputedStyle(elm);
    var props = compStyle['transition-property'].split(', ');
    for (; i < props.length; ++i) {
        if (applied.indexOf(props[i]) !== -1)
            amount++;
    }
    elm.addEventListener('transitionend', function (ev) {
        if (ev.target === elm)
            --amount;
        if (amount === 0)
            rm();
    });
}
exports.styleModule = {
    create: updateStyle,
    update: updateStyle,
    destroy: applyDestroyStyle,
    remove: applyRemoveStyle
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = exports.styleModule;

},{}],10:[function(require,module,exports){
"use strict";
const vnode_1 = require("./vnode");
const is = require("./is");
const htmldomapi_1 = require("./htmldomapi");
function isUndef(s) { return s === undefined; }
function isDef(s) { return s !== undefined; }
const emptyNode = vnode_1.default('', {}, [], undefined, undefined);
function sameVnode(vnode1, vnode2) {
    return vnode1.key === vnode2.key && vnode1.sel === vnode2.sel;
}
function isVnode(vnode) {
    return vnode.sel !== undefined;
}
function createKeyToOldIdx(children, beginIdx, endIdx) {
    let i, map = {}, key;
    for (i = beginIdx; i <= endIdx; ++i) {
        key = children[i].key;
        if (key !== undefined)
            map[key] = i;
    }
    return map;
}
const hooks = ['create', 'update', 'remove', 'destroy', 'pre', 'post'];
var h_1 = require("./h");
exports.h = h_1.h;
var thunk_1 = require("./thunk");
exports.thunk = thunk_1.thunk;
function init(modules, domApi) {
    let i, j, cbs = {};
    const api = domApi !== undefined ? domApi : htmldomapi_1.default;
    for (i = 0; i < hooks.length; ++i) {
        cbs[hooks[i]] = [];
        for (j = 0; j < modules.length; ++j) {
            const hook = modules[j][hooks[i]];
            if (hook !== undefined) {
                cbs[hooks[i]].push(hook);
            }
        }
    }
    function emptyNodeAt(elm) {
        const id = elm.id ? '#' + elm.id : '';
        const c = elm.className ? '.' + elm.className.split(' ').join('.') : '';
        return vnode_1.default(api.tagName(elm).toLowerCase() + id + c, {}, [], undefined, elm);
    }
    function createRmCb(childElm, listeners) {
        return function rmCb() {
            if (--listeners === 0) {
                const parent = api.parentNode(childElm);
                api.removeChild(parent, childElm);
            }
        };
    }
    function createElm(vnode, insertedVnodeQueue) {
        let i, data = vnode.data;
        if (data !== undefined) {
            if (isDef(i = data.hook) && isDef(i = i.init)) {
                i(vnode);
                data = vnode.data;
            }
        }
        let children = vnode.children, sel = vnode.sel;
        if (sel !== undefined) {
            // Parse selector
            const hashIdx = sel.indexOf('#');
            const dotIdx = sel.indexOf('.', hashIdx);
            const hash = hashIdx > 0 ? hashIdx : sel.length;
            const dot = dotIdx > 0 ? dotIdx : sel.length;
            const tag = hashIdx !== -1 || dotIdx !== -1 ? sel.slice(0, Math.min(hash, dot)) : sel;
            const elm = vnode.elm = isDef(data) && isDef(i = data.ns) ? api.createElementNS(i, tag)
                : api.createElement(tag);
            if (hash < dot)
                elm.id = sel.slice(hash + 1, dot);
            if (dotIdx > 0)
                elm.className = sel.slice(dot + 1).replace(/\./g, ' ');
            if (is.array(children)) {
                for (i = 0; i < children.length; ++i) {
                    api.appendChild(elm, createElm(children[i], insertedVnodeQueue));
                }
            }
            else if (is.primitive(vnode.text)) {
                api.appendChild(elm, api.createTextNode(vnode.text));
            }
            for (i = 0; i < cbs.create.length; ++i)
                cbs.create[i](emptyNode, vnode);
            i = vnode.data.hook; // Reuse variable
            if (isDef(i)) {
                if (i.create)
                    i.create(emptyNode, vnode);
                if (i.insert)
                    insertedVnodeQueue.push(vnode);
            }
        }
        else {
            vnode.elm = api.createTextNode(vnode.text);
        }
        return vnode.elm;
    }
    function addVnodes(parentElm, before, vnodes, startIdx, endIdx, insertedVnodeQueue) {
        for (; startIdx <= endIdx; ++startIdx) {
            api.insertBefore(parentElm, createElm(vnodes[startIdx], insertedVnodeQueue), before);
        }
    }
    function invokeDestroyHook(vnode) {
        let i, j, data = vnode.data;
        if (data !== undefined) {
            if (isDef(i = data.hook) && isDef(i = i.destroy))
                i(vnode);
            for (i = 0; i < cbs.destroy.length; ++i)
                cbs.destroy[i](vnode);
            if (vnode.children !== undefined) {
                for (j = 0; j < vnode.children.length; ++j) {
                    i = vnode.children[j];
                    if (typeof i !== "string") {
                        invokeDestroyHook(i);
                    }
                }
            }
        }
    }
    function removeVnodes(parentElm, vnodes, startIdx, endIdx) {
        for (; startIdx <= endIdx; ++startIdx) {
            let i, listeners, rm, ch = vnodes[startIdx];
            if (isDef(ch)) {
                if (isDef(ch.sel)) {
                    invokeDestroyHook(ch);
                    listeners = cbs.remove.length + 1;
                    rm = createRmCb(ch.elm, listeners);
                    for (i = 0; i < cbs.remove.length; ++i)
                        cbs.remove[i](ch, rm);
                    if (isDef(i = ch.data) && isDef(i = i.hook) && isDef(i = i.remove)) {
                        i(ch, rm);
                    }
                    else {
                        rm();
                    }
                }
                else {
                    api.removeChild(parentElm, ch.elm);
                }
            }
        }
    }
    function updateChildren(parentElm, oldCh, newCh, insertedVnodeQueue) {
        let oldStartIdx = 0, newStartIdx = 0;
        let oldEndIdx = oldCh.length - 1;
        let oldStartVnode = oldCh[0];
        let oldEndVnode = oldCh[oldEndIdx];
        let newEndIdx = newCh.length - 1;
        let newStartVnode = newCh[0];
        let newEndVnode = newCh[newEndIdx];
        let oldKeyToIdx;
        let idxInOld;
        let elmToMove;
        let before;
        while (oldStartIdx <= oldEndIdx && newStartIdx <= newEndIdx) {
            if (isUndef(oldStartVnode)) {
                oldStartVnode = oldCh[++oldStartIdx]; // Vnode has been moved left
            }
            else if (isUndef(oldEndVnode)) {
                oldEndVnode = oldCh[--oldEndIdx];
            }
            else if (sameVnode(oldStartVnode, newStartVnode)) {
                patchVnode(oldStartVnode, newStartVnode, insertedVnodeQueue);
                oldStartVnode = oldCh[++oldStartIdx];
                newStartVnode = newCh[++newStartIdx];
            }
            else if (sameVnode(oldEndVnode, newEndVnode)) {
                patchVnode(oldEndVnode, newEndVnode, insertedVnodeQueue);
                oldEndVnode = oldCh[--oldEndIdx];
                newEndVnode = newCh[--newEndIdx];
            }
            else if (sameVnode(oldStartVnode, newEndVnode)) {
                patchVnode(oldStartVnode, newEndVnode, insertedVnodeQueue);
                api.insertBefore(parentElm, oldStartVnode.elm, api.nextSibling(oldEndVnode.elm));
                oldStartVnode = oldCh[++oldStartIdx];
                newEndVnode = newCh[--newEndIdx];
            }
            else if (sameVnode(oldEndVnode, newStartVnode)) {
                patchVnode(oldEndVnode, newStartVnode, insertedVnodeQueue);
                api.insertBefore(parentElm, oldEndVnode.elm, oldStartVnode.elm);
                oldEndVnode = oldCh[--oldEndIdx];
                newStartVnode = newCh[++newStartIdx];
            }
            else {
                if (oldKeyToIdx === undefined) {
                    oldKeyToIdx = createKeyToOldIdx(oldCh, oldStartIdx, oldEndIdx);
                }
                idxInOld = oldKeyToIdx[newStartVnode.key];
                if (isUndef(idxInOld)) {
                    api.insertBefore(parentElm, createElm(newStartVnode, insertedVnodeQueue), oldStartVnode.elm);
                    newStartVnode = newCh[++newStartIdx];
                }
                else {
                    elmToMove = oldCh[idxInOld];
                    if (elmToMove.sel !== newStartVnode.sel) {
                        api.insertBefore(parentElm, createElm(newStartVnode, insertedVnodeQueue), oldStartVnode.elm);
                    }
                    else {
                        patchVnode(elmToMove, newStartVnode, insertedVnodeQueue);
                        oldCh[idxInOld] = undefined;
                        api.insertBefore(parentElm, elmToMove.elm, oldStartVnode.elm);
                    }
                    newStartVnode = newCh[++newStartIdx];
                }
            }
        }
        if (oldStartIdx > oldEndIdx) {
            before = isUndef(newCh[newEndIdx + 1]) ? null : newCh[newEndIdx + 1].elm;
            addVnodes(parentElm, before, newCh, newStartIdx, newEndIdx, insertedVnodeQueue);
        }
        else if (newStartIdx > newEndIdx) {
            removeVnodes(parentElm, oldCh, oldStartIdx, oldEndIdx);
        }
    }
    function patchVnode(oldVnode, vnode, insertedVnodeQueue) {
        let i, hook;
        if (isDef(i = vnode.data) && isDef(hook = i.hook) && isDef(i = hook.prepatch)) {
            i(oldVnode, vnode);
        }
        const elm = vnode.elm = oldVnode.elm;
        let oldCh = oldVnode.children;
        let ch = vnode.children;
        if (oldVnode === vnode)
            return;
        if (vnode.data !== undefined) {
            for (i = 0; i < cbs.update.length; ++i)
                cbs.update[i](oldVnode, vnode);
            i = vnode.data.hook;
            if (isDef(i) && isDef(i = i.update))
                i(oldVnode, vnode);
        }
        if (isUndef(vnode.text)) {
            if (isDef(oldCh) && isDef(ch)) {
                if (oldCh !== ch)
                    updateChildren(elm, oldCh, ch, insertedVnodeQueue);
            }
            else if (isDef(ch)) {
                if (isDef(oldVnode.text))
                    api.setTextContent(elm, '');
                addVnodes(elm, null, ch, 0, ch.length - 1, insertedVnodeQueue);
            }
            else if (isDef(oldCh)) {
                removeVnodes(elm, oldCh, 0, oldCh.length - 1);
            }
            else if (isDef(oldVnode.text)) {
                api.setTextContent(elm, '');
            }
        }
        else if (oldVnode.text !== vnode.text) {
            api.setTextContent(elm, vnode.text);
        }
        if (isDef(hook) && isDef(i = hook.postpatch)) {
            i(oldVnode, vnode);
        }
    }
    return function patch(oldVnode, vnode) {
        let i, elm, parent;
        const insertedVnodeQueue = [];
        for (i = 0; i < cbs.pre.length; ++i)
            cbs.pre[i]();
        if (!isVnode(oldVnode)) {
            oldVnode = emptyNodeAt(oldVnode);
        }
        if (sameVnode(oldVnode, vnode)) {
            patchVnode(oldVnode, vnode, insertedVnodeQueue);
        }
        else {
            elm = oldVnode.elm;
            parent = api.parentNode(elm);
            createElm(vnode, insertedVnodeQueue);
            if (parent !== null) {
                api.insertBefore(parent, vnode.elm, api.nextSibling(elm));
                removeVnodes(parent, [oldVnode], 0, 0);
            }
        }
        for (i = 0; i < insertedVnodeQueue.length; ++i) {
            insertedVnodeQueue[i].data.hook.insert(insertedVnodeQueue[i]);
        }
        for (i = 0; i < cbs.post.length; ++i)
            cbs.post[i]();
        return vnode;
    };
}
exports.init = init;

},{"./h":3,"./htmldomapi":4,"./is":5,"./thunk":11,"./vnode":12}],11:[function(require,module,exports){
"use strict";
const h_1 = require("./h");
function copyToThunk(vnode, thunk) {
    thunk.elm = vnode.elm;
    vnode.data.fn = thunk.data.fn;
    vnode.data.args = thunk.data.args;
    thunk.data = vnode.data;
    thunk.children = vnode.children;
    thunk.text = vnode.text;
    thunk.elm = vnode.elm;
}
function init(thunk) {
    const cur = thunk.data;
    const vnode = cur.fn.apply(undefined, cur.args);
    copyToThunk(vnode, thunk);
}
function prepatch(oldVnode, thunk) {
    let i, old = oldVnode.data, cur = thunk.data;
    const oldArgs = old.args, args = cur.args;
    if (old.fn !== cur.fn || oldArgs.length !== args.length) {
        copyToThunk(cur.fn.apply(undefined, args), thunk);
    }
    for (i = 0; i < args.length; ++i) {
        if (oldArgs[i] !== args[i]) {
            copyToThunk(cur.fn.apply(undefined, args), thunk);
            return;
        }
    }
    copyToThunk(oldVnode, thunk);
}
exports.thunk = function thunk(sel, key, fn, args) {
    if (args === undefined) {
        args = fn;
        fn = key;
        key = undefined;
    }
    return h_1.h(sel, {
        key: key,
        hook: { init: init, prepatch: prepatch },
        fn: fn,
        args: args
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = exports.thunk;

},{"./h":3}],12:[function(require,module,exports){
"use strict";
function vnode(sel, data, children, text, elm) {
    let key = data === undefined ? undefined : data.key;
    return { sel: sel, data: data, children: children,
        text: text, elm: elm, key: key };
}
exports.vnode = vnode;
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = vnode;

},{}],13:[function(require,module,exports){
"use strict";
const snabbdom_1 = require("./lib/snabbdom/src/snabbdom");
const class_1 = require("./lib/snabbdom/src/modules/class");
const props_1 = require("./lib/snabbdom/src/modules/props");
const style_1 = require("./lib/snabbdom/src/modules/style");
const eventlisteners_1 = require("./lib/snabbdom/src/modules/eventlisteners");
const counterList_1 = require("./counterList");
// Get a Snabbdom patch function with the normal HTML modules.
const patch = snabbdom_1.init([
    class_1.classModule,
    props_1.propsModule,
    style_1.styleModule,
    eventlisteners_1.eventListenersModule
]);
/**
 * Runs one cycle of the Snabbdom event loop.
 * @param state the latest model state.
 * @param oldVnode the prior virtual DOM or the real DOM first time through.
 * @param view the function to compute the new virtual DOM from the model.
 * @param update the function to compute the new model state from the old state and a pending action.
 */
function main(state, oldVnode, { view, update }) {
    let eventHandler = action => {
        const newState = update(state, action);
        main(newState, newVnode, { view, update });
    };
    const newVnode = view(state, eventHandler);
    patch(oldVnode, newVnode);
}
/**
 * Initializes the application state.
 * @returns {{nextID: number, counters: Array}}
 */
function initState() {
    return { nextID: 5, counters: [] };
}
// ---------------------------------------------------------------------------
// Find the DOM node to drop our app in.
let domNode = document.getElementById('app');
if (domNode == null) {
    // Abandon hope if the real DOM node is not found.
    console.log("Cannot find application DOM node.");
}
else {
    // Fire off the first loop of the lifecycle.
    main(initState(), domNode, counterList_1.default);
}

},{"./counterList":2,"./lib/snabbdom/src/modules/class":6,"./lib/snabbdom/src/modules/eventlisteners":7,"./lib/snabbdom/src/modules/props":8,"./lib/snabbdom/src/modules/style":9,"./lib/snabbdom/src/snabbdom":10}]},{},[13])
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIm5vZGVfbW9kdWxlcy9icm93c2VyLXBhY2svX3ByZWx1ZGUuanMiLCJ3ZWJhcHAvc2NyaXB0cy9jb3VudGVyLnRzIiwid2ViYXBwL3NjcmlwdHMvY291bnRlckxpc3QudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL2gudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL2h0bWxkb21hcGkudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL2lzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL2NsYXNzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL2V2ZW50bGlzdGVuZXJzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL3Byb3BzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL3N0eWxlLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9zbmFiYmRvbS50cyIsIndlYmFwcC9zY3JpcHRzL2xpYi9zbmFiYmRvbS9zcmMvdGh1bmsudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL3Zub2RlLnRzIiwid2ViYXBwL3NjcmlwdHMvbWFpbi50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQ0FBLFlBQVksQ0FBQztBQUdiLDRDQUFxQztBQUVyQyxNQUFNLEdBQUcsR0FBRyxLQUFLLENBQUM7QUFDbEIsTUFBTSxHQUFHLEdBQUcsS0FBSyxDQUFDO0FBQ2xCLE1BQU0sSUFBSSxHQUFHLE1BQU0sQ0FBQztBQUdwQixpQkFBaUI7QUFDakIsY0FBZSxLQUFhLEVBQUUsT0FBTztJQUNqQyxNQUFNLENBQUMsV0FBQyxDQUNKLEtBQUssRUFBRTtRQUNILFdBQUMsQ0FDRyxRQUFRLEVBQUU7WUFDTixFQUFFLEVBQUUsRUFBRSxLQUFLLEVBQUUs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ckYsQ0FBQztRQUNILENBQUM7SUFDSCxDQUFDO0FBQ0gsQ0FBQztBQVFELFdBQWtCLEdBQVEsRUFBRSxDQUFPLEVBQUUsQ0FBTztJQUMxQyxJQUFJLElBQUksR0FBYyxFQUFFLEVBQUUsUUFBYSxFQUFFLElBQVMsRUFBRSxDQUFTLENBQUM7SUFDOUQsRUFBRSxDQUFDLENBQUMsQ0FBQyxLQUFLLFNBQVMsQ0FBQyxDQUFDLENBQUM7UUFDcEIsSUFBSSxHQUFHLENBQUMsQ0FBQztRQUNULEVBQUUsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxLQUFLLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQUMsUUFBUSxHQUFHLENBQUMsQ0FBQztRQUFDLENBQUM7UUFDbEMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxTQUFTLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQUMsSUFBSSxHQUFHLENBQUMsQ0FBQztRQUFDLENBQUM7UUFDdkMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsSUFBSSxDQUFDLENBQUMsR0FBRyxDQUFDLENBQUMsQ0FBQztZQUFDLFFBQVEsR0FBRyxDQUFDLENBQUMsQ0FBQyxDQUFDO1FBQUMsQ0FBQztJQUMxQyxDQUFDO0lBQUMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsS0FBSyxTQUFTLENBQUMsQ0FBQyxDQUFDO1FBQzNCLEVBQUUsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxLQUFLLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQUMsUUFBUSxHQUFHLENBQUMsQ0FBQztRQUFDLENBQUM7UUFDbEMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxTQUFTLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQUMsSUFBSSxHQUFHLENBQUMsQ0FBQztRQUFDLENBQUM7UUFDdkMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsSUFBSSxDQUFDLENBQUMsR0FBRyxDQUFDLENBQUMsQ0FBQztZQUFDLFFB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RUFBRSxNQUFNLEVBQUUsQ0FBRSxDQUFDO0lBQ2pELENBQUMsQ0FBQztJQUVGLE1BQU0sUUFBUSxHQUFHLElBQUksQ0FBRSxLQUFLLEVBQUUsWUFBWSxDQUFFLENBQUM7SUFFN0MsS0FBSyxDQUFFLFFBQVEsRUFBRSxRQUFRLENBQUUsQ0FBQztBQUVoQyxDQUFDO0FBRUQ7OztHQUdHO0FBQ0g7SUFDSSxNQUFNLENBQUMsRUFBRSxNQUFNLEVBQUUsQ0FBQyxFQUFFLFFBQVEsRUFBRSxFQUFFLEVBQUUsQ0FBQztBQUN2QyxDQUFDO0FBRUQsOEVBQThFO0FBRTlFLHdDQUF3QztBQUN4QyxJQUFJLE9BQU8sR0FBRyxRQUFRLENBQUMsY0FBYyxDQUFFLEtBQUssQ0FBRSxDQUFDO0FBRS9DLEVBQUUsQ0FBQyxDQUFFLE9BQU8sSUFBSSxJQUFLLENBQUMsQ0FBQyxDQUFDO0lBQ3BCLGtEQUFrRDtJQUNsRCxPQUFPLENBQUMsR0FBRyxDQUFFLG1DQUFtQyxDQUFFLENBQUM7QUFDdkQsQ0FBQztBQUNELElBQUksQ0FBQyxDQUFDO0lBQ0YsNENBQTRDO0lBQzVDLElBQUksQ0FBRSxTQUFTLEVBQUUsRUFBRSxPQUFPLEVBQUUscUJBQVcsQ0FBRSxDQUFDO0FBQzlDLENBQUMiLCJmaWxlIjoiZ2VuZXJhdGVkLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXNDb250ZW50IjpbIihmdW5jdGlvbiBlKHQsbixyKXtmdW5jdGlvbiBzKG8sdSl7aWYoIW5bb10pe2lmKCF0W29dKXt2YXIgYT10eXBlb2YgcmVxdWlyZT09XCJmdW5jdGlvblwiJiZyZXF1aXJlO2lmKCF1JiZhKXJldHVybiBhKG8sITApO2lmKGkpcmV0dXJuIGkobywhMCk7dmFyIGY9bmV3IEVycm9yKFwiQ2Fubm90IGZpbmQgbW9kdWxlICdcIitvK1wiJ1wiKTt0aHJvdyBmLmNvZGU9XCJNT0RVTEVfTk9UX0ZPVU5EXCIsZn12YXIgbD1uW29dPXtleHBvcnRzOnt9fTt0W29dWzBdLmNhbGwobC5leHBvcnRzLGZ1bmN0aW9uKGUpe3ZhciBuPXRbb11bMV1bZV07cmV0dXJuIHMobj9uOmUpfSxsLGwuZXhwb3J0cyxlLHQsbixyKX1yZXR1cm4gbltvXS5leHBvcnRzfXZhciBpPXR5cGVvZiByZXF1aXJlPT1cImZ1bmN0aW9uXCImJnJlcXVpcmU7Zm9yKHZhciBvPTA7bzxyLmxlbmd0aDtvKyspcyhyW29dKTtyZXR1cm4gc30pIiwiXCJ1c2Ugc3RyaWN0XCI7XG5cbmltcG9ydCB7IFZOb2RlIH0gZnJvbSAnLi9saWIvc25hYmJkb20vc3JjL3Zub2RlJztcbmltcG9ydCBoIGZyb20gJy4vbGliL3NuYWJiZG9tL3NyYy9oJztcblxuY29uc3QgSU5DID0gJ2luYyc7XG5jb25zdCBERUMgPSAnZGVjJztcbmNvbnN0IElOSVQgPSAnaW5pdCc7XG5cblxuLy8gbW9kZWwgOiBOdW1iZXJcbmZ1bmN0aW9uIHZpZXcoIGNvdW50OiBudW1iZXIsIGhhbmRsZXIgKSA6IFZOb2RlIHtcbiAgICByZXR1cm4gaChcbiAgICAgICAgJ2RpdicsIFtcbiAgICAgICAgICAgIGgoXG4gICAgICAgICAgICAgICAgJ2J1dHRvbicsIHtcbiAgICAgICAgICAgICAgICAgICAgb246IHsgY2xpY2s6IGhhbmRsZXIuYmluZCggbnVsbCwgeyB0eXBlOiBJTkMgfSApIH1cbiAgICAgICAgICAgICAgICB9LCAnKydcbiAgICAgICAgICAgICksXG4gICAgICAgICAgICBoKFxuICAgICAgICAgICAgICAgICdidXR0b24nLCB7XG4gICAgICAgICAgICAgICAgICAgIG9uOiB7IGNsaWNrOiBoYW5kbGVyLmJpbmQoIG51bGwsIHsgdHlwZTogREVDIH0gKSB9XG4gICAgICAgICAgICAgICAgfSwgJy0nXG4gICAgICAgICAgICApLFxuICAgICAgICAgICAgaCggJ2RpdicsIGBDb3VudCA6ICR7Y291bnR9YCApLFxuICAgICAgICBdXG4gICAgKTtcbn1cblxuXG5mdW5jdGlvbiB1cGRhdGUoIGNvdW50LCBhY3Rpb24gKSB7XG4gICAgcmV0dXJuIGFjdGlvbi50eXBlID09PSBJTkMgPyBjb3VudCArIDFcbiAgICAgICAgIDogYWN0aW9uLnR5cGUgPT09IERFQyA/IGNvdW50IC0gMVxuICAgICAgICAgOiBhY3Rpb24udHlwZSA9PT0gSU5JVCA/IDBcbiAgICAgICAgIDogY291bnQ7XG59XG5cbmV4cG9ydCBkZWZhdWx0IHsgdmlldywgdXBkYXRlLCBJTklUIH1cbiIsIlwidXNlIHN0cmljdFwiO1xuXG5pbXBvcnQgaCBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvaCc7XG5pbXBvcnQgeyBWTm9kZSB9IGZyb20gJy4vbGliL3NuYWJiZG9tL3NyYy92bm9kZSc7XG5pbXBvcnQgY291bnRlciBmcm9tICcuL2NvdW50ZXInO1xuXG4vLyBBQ1RJT05TXG5cbmNvbnN0IEFERCAgICAgPSAnYWRkJztcbmNvbnN0IFVQREFURSAgPSAndXBkYXRlIGNvdW50ZXInO1xuY29uc3QgUkVNT1ZFICA9ICdyZW1vdmUnO1xuY29uc3QgUkVTRVQgICA9ICdyZXNldCc7XG5cbmNvbnN0IHJlc2V0QWN0aW9uID0geyB0eXBlOiBjb3VudGVyLklOSVQgfTtcblxuXG4vLyBNT0RFTFxuXG4vKiAgbW9kZWwgOiB7XG4gY291bnRlcnM6IFt7aWQ6IE51bWJlciwgY291bnRlcjogY291bnRlci5tb2RlbH1dLFxuIG5leHRJRCAgOiBOdW1iZXJcbiB9XG4gKi9cblxuXG4vLyBWSUVXXG5cbi8qKlxuICogR2VuZXJhdGVzIHRoZSBtYXJrIHVwIGZvciB0aGUgbGlzdCBvZiBjb3VudGVycy5cbiAqIEBwYXJhbSBtb2RlbCB0aGUgc3RhdGUgb2YgdGhlIGNvdW50ZXJzLlxuICogQHBhcmFtIGhhbmRsZXIgZXZlbnQgaGFuZGxpbmcuXG4gKi9cbmZ1bmN0aW9uIHZpZXcoIG1vZGVsLCBoYW5kbGVyICkgOiBWTm9kZSB7XG4gIHJldHVybiBoKFxuICAgICdkaXYnLCBbXG4gICAgICBoKFxuICAgICAgICAnYnV0dG9uJywge1xuICAgICAgICAgIG9uOiB7IGNsaWNrOiBoYW5kbGVyLmJpbmQoIG51bGwsIHt0eXBlOiBBRER9ICkgfVxuICAgICAgICB9LCAnQWRkJ1xuICAgICAgKSxcbiAgICAgIGgoXG4gICAgICAgICdidXR0b24nLCB7XG4gICAgICAgICAgb246IHsgY2xpY2s6IGhhbmRsZXIuYmluZCggbnVsbCwge3R5cGU6IFJFU0VUfSApIH1cbiAgICAgICAgfSwgJ1Jlc2V0J1xuICAgICAgKSxcbiAgICAgIGgoICdocicgKSxcbiAgICAgIGgoICdkaXYuY291bnRlci1saXN0JywgbW9kZWwuY291bnRlcnMubWFwKCBpdGVtID0+IGNvdW50ZXJJdGVtVmlldyggaXRlbSwgaGFuZGxlciApICkgKVxuXG4gICAgXVxuICApO1xufVxuXG4vKipcbiAqIEdlbmVyYXRlcyB0aGUgbWFyayB1cCBmb3Igb25lIGNvdW50ZXIgcGx1cyBhIHJlbW92ZSBidXR0b24uXG4gKiBAcGFyYW0gaXRlbSBvbmUgZW50cnkgaW4gdGhlIGNvdW50ZXJzIGFycmF5LlxuICogQHBhcmFtIGhhbmRsZXIgdGhlIG1hc3RlciBldmVudCBoYW5kbGVyXG4gKi9cbmZ1bmN0aW9uIGNvdW50ZXJJdGVtVmlldyggaXRlbSwgaGFuZGxlciApIHtcbiAgcmV0dXJuIGgoXG4gICAgJ2Rpdi5jb3VudGVyLWl0ZW0nLCB7IGtleTogaXRlbS5pZCB9LCBbXG4gICAgICBoKFxuICAgICAgICAnYnV0dG9uLnJlbW92ZScsIHtcbiAgICAgICAgICBvbjogeyBjbGljazogaGFuZGxlci5iaW5kKCBudWxsLCB7IHR5cGU6IFJFTU9WRSwgaWQ6IGl0ZW0uaWR9ICkgfVxuICAgICAgICB9LCAnUmVtb3ZlJ1xuICAgICAgKSxcbiAgICAgIGNvdW50ZXIudmlldyggaXRlbS5jb3VudGVyLCBhID0+IGhhbmRsZXIoIHt0eXBlOiBVUERBVEUsIGlkOiBpdGVtLmlkLCBkYXRhOiBhfSApICksXG4gICAgICBoKCAnaHInIClcbiAgICBdXG4gICk7XG59XG5cbi8vIFVQREFURVxuXG5mdW5jdGlvbiB1cGRhdGUoIG1vZGVsLCBhY3Rpb24gKSB7XG5cbiAgICByZXR1cm4gIGFjdGlvbi50eXBlID09PSBBREQgICAgID8gYWRkQ291bnRlcihtb2RlbClcbiAgICAgICAgICA6IGFjdGlvbi50eXBlID09PSBSRVNFVCAgID8gcmVzZXRDb3VudGVycyhtb2RlbClcbiAgICAgICAgICA6IGFjdGlvbi50eXBlID09PSBSRU1PVkUgID8gcmVtb3ZlQ291bnRlcihtb2RlbCwgYWN0aW9uLmlkKVxuICAgICAgICAgIDogYWN0aW9uLnR5cGUgPT09IFVQREFURSAgPyB1cGRhdGVDb3VudGVyKG1vZGVsLCBhY3Rpb24uaWQsIGFjdGlvbi5kYXRhKVxuICAgICAgICAgIDogbW9kZWw7XG5cbn1cblxuZnVuY3Rpb24gYWRkQ291bnRlciggbW9kZWwgKSB7XG4gIGNvbnN0IG5ld0NvdW50ZXIgPSB7IGlkOiBtb2RlbC5uZXh0SUQsIGNvdW50ZXI6IGNvdW50ZXIudXBkYXRlKCBudWxsLCByZXNldEFjdGlvbiApIH07XG4gIHJldHVybiB7XG4gICAgY291bnRlcnM6IFsuLi5tb2RlbC5jb3VudGVycywgbmV3Q291bnRlcl0sXG4gICAgbmV4dElEOiBtb2RlbC5uZXh0SUQgKyAxXG4gIH07XG59XG5cblxuZnVuY3Rpb24gcmVzZXRDb3VudGVycyggbW9kZWwgKSB7XG5cbiAgcmV0dXJuIHtcbiAgICAuLi5tb2RlbCxcbiAgICBjb3VudGVyczogbW9kZWwuY291bnRlcnMubWFwKFxuICAgICAgaXRlbSA9PiAoe1xuICAgICAgICAuLi5pdGVtLFxuICAgICAgICBjb3VudGVyOiBjb3VudGVyLnVwZGF0ZSggaXRlbS5jb3VudGVyLCByZXNldEFjdGlvbiApXG4gICAgICB9KVxuICAgIClcbiAgfTtcbn1cblxuZnVuY3Rpb24gcmVtb3ZlQ291bnRlciggbW9kZWwsIGlkICkge1xuICByZXR1cm4ge1xuICAgIC4uLm1vZGVsLFxuICAgIGNvdW50ZXJzOiBtb2RlbC5jb3VudGVycy5maWx0ZXIoIGl0ZW0gPT4gaXRlbS5pZCAhPT0gaWQgKVxuICB9O1xufVxuXG5mdW5jdGlvbiB1cGRhdGVDb3VudGVyKCBtb2RlbCwgaWQsIGFjdGlvbiApIHtcbiAgcmV0dXJuIHtcbiAgICAuLi5tb2RlbCxcbiAgICBjb3VudGVyczogbW9kZWwuY291bnRlcnMubWFwKFxuICAgICAgaXRlbSA9PlxuICAgICAgICBpdGVtLmlkICE9PSBpZCA/XG4gICAgICAgICAgaXRlbVxuICAgICAgICAgIDoge1xuICAgICAgICAgICAgLi4uaXRlbSxcbiAgICAgICAgICAgIGNvdW50ZXI6IGNvdW50ZXIudXBkYXRlKCBpdGVtLmNvdW50ZXIsIGFjdGlvbiApXG4gICAgICAgICAgfVxuICAgIClcbiAgfTtcbn1cblxuZXhwb3J0IGRlZmF1bHQgeyB2aWV3LCB1cGRhdGUgfTtcbiIsImltcG9ydCB7dm5vZGUsIFZOb2RlLCBWTm9kZURhdGF9IGZyb20gJy4vdm5vZGUnO1xuaW1wb3J0ICogYXMgaXMgZnJvbSAnLi9pcyc7XG5cbmZ1bmN0aW9uIGFkZE5TKGRhdGE6IGFueSwgY2hpbGRyZW46IEFycmF5PFZOb2RlPiB8IHVuZGVmaW5lZCwgc2VsOiBzdHJpbmcgfCB1bmRlZmluZWQpOiB2b2lkIHtcbiAgZGF0YS5ucyA9ICdodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2Zyc7XG4gIGlmIChzZWwgIT09ICdmb3JlaWduT2JqZWN0JyAmJiBjaGlsZHJlbiAhPT0gdW5kZWZpbmVkKSB7XG4gICAgZm9yIChsZXQgaSA9IDA7IGkgPCBjaGlsZHJlbi5sZW5ndGg7ICsraSkge1xuICAgICAgbGV0IGNoaWxkRGF0YSA9IGNoaWxkcmVuW2ldLmRhdGE7XG4gICAgICBpZiAoY2hpbGREYXRhICE9PSB1bmRlZmluZWQpIHtcbiAgICAgICAgYWRkTlMoY2hpbGREYXRhLCAoY2hpbGRyZW5baV0gYXMgVk5vZGUpLmNoaWxkcmVuIGFzIEFycmF5PFZOb2RlPiwgY2hpbGRyZW5baV0uc2VsKTtcbiAgICAgIH1cbiAgICB9XG4gIH1cbn1cblxuZXhwb3J0IGZ1bmN0aW9uIGgoc2VsOiBzdHJpbmcpOiBWTm9kZTtcbmV4cG9ydCBmdW5jdGlvbiBoKHNlbDogc3RyaW5nLCBkYXRhOiBWTm9kZURhdGEpOiBWTm9kZTtcbmV4cG9ydCBmdW5jdGlvbiBoKHNlbDogc3RyaW5nLCB0ZXh0OiBzdHJpbmcpOiBWTm9kZTtcbmV4cG9ydCBmdW5jdGlvbiBoKHNlbDogc3RyaW5nLCBjaGlsZHJlbjogQXJyYXk8Vk5vZGU+KTogVk5vZGU7XG5leHBvcnQgZnVuY3Rpb24gaChzZWw6IHN0cmluZywgZGF0YTogVk5vZGVEYXRhLCB0ZXh0OiBzdHJpbmcpOiBWTm9kZTtcbmV4cG9ydCBmdW5jdGlvbiBoKHNlbDogc3RyaW5nLCBkYXRhOiBWTm9kZURhdGEsIGNoaWxkcmVuOiBBcnJheTxWTm9kZT4pOiBWTm9kZTtcbmV4cG9ydCBmdW5jdGlvbiBoKHNlbDogYW55LCBiPzogYW55LCBjPzogYW55KTogVk5vZGUge1xuICB2YXIgZGF0YTogVk5vZGVEYXRhID0ge30sIGNoaWxkcmVuOiBhbnksIHRleHQ6IGFueSwgaTogbnVtYmVyO1xuICBpZiAoYyAhPT0gdW5kZWZpbmVkKSB7XG4gICAgZGF0YSA9IGI7XG4gICAgaWYgKGlzLmFycmF5KGMpKSB7IGNoaWxkcmVuID0gYzsgfVxuICAgIGVsc2UgaWYgKGlzLnByaW1pdGl2ZShjKSkgeyB0ZXh0ID0gYzsgfVxuICAgIGVsc2UgaWYgKGMgJiYgYy5zZWwpIHsgY2hpbGRyZW4gPSBbY107IH1cbiAgfSBlbHNlIGlmIChiICE9PSB1bmRlZmluZWQpIHtcbiAgICBpZiAoaXMuYXJyYXkoYikpIHsgY2hpbGRyZW4gPSBiOyB9XG4gICAgZWxzZSBpZiAoaXMucHJpbWl0aXZlKGIpKSB7IHRleHQgPSBiOyB9XG4gICAgZWxzZSBpZiAoYiAmJiBiLnNlbCkgeyBjaGlsZHJlbiA9IFtiXTsgfVxuICAgIGVsc2UgeyBkYXRhID0gYjsgfVxuICB9XG4gIGlmIChpcy5hcnJheShjaGlsZHJlbikpIHtcbiAgICBmb3IgKGkgPSAwOyBpIDwgY2hpbGRyZW4ubGVuZ3RoOyArK2kpIHtcbiAgICAgIGlmIChpcy5wcmltaXRpdmUoY2hpbGRyZW5baV0pKSBjaGlsZHJlbltpXSA9ICh2bm9kZSBhcyBhbnkpKHVuZGVmaW5lZCwgdW5kZWZpbmVkLCB1bmRlZmluZWQsIGNoaWxkcmVuW2ldKTtcbiAgICB9XG4gIH1cbiAgaWYgKFxuICAgIHNlbFswXSA9PT0gJ3MnICYmIHNlbFsxXSA9PT0gJ3YnICYmIHNlbFsyXSA9PT0gJ2cnICYmXG4gICAgKHNlbC5sZW5ndGggPT09IDMgfHwgc2VsWzNdID09PSAnLicgfHwgc2VsWzNdID09PSAnIycpXG4gICkge1xuICAgIGFkZE5TKGRhdGEsIGNoaWxkcmVuLCBzZWwpO1xuICB9XG4gIHJldHVybiB2bm9kZShzZWwsIGRhdGEsIGNoaWxkcmVuLCB0ZXh0LCB1bmRlZmluZWQpO1xufTtcbmV4cG9ydCBkZWZhdWx0IGg7XG4iLCJleHBvcnQgaW50ZXJmYWNlIERPTUFQSSB7XG4gIGNyZWF0ZUVsZW1lbnQ6ICh0YWdOYW1lOiBhbnkpID0+IEhUTUxFbGVtZW50O1xuICBjcmVhdGVFbGVtZW50TlM6IChuYW1lc3BhY2VVUkk6IHN0cmluZywgcXVhbGlmaWVkTmFtZTogc3RyaW5nKSA9PiBFbGVtZW50O1xuICBjcmVhdGVUZXh0Tm9kZTogKHRleHQ6IHN0cmluZykgPT4gVGV4dDtcbiAgaW5zZXJ0QmVmb3JlOiAocGFyZW50Tm9kZTogTm9kZSwgbmV3Tm9kZTogTm9kZSwgcmVmZXJlbmNlTm9kZTogTm9kZSB8IG51bGwpID0+IHZvaWQ7XG4gIHJlbW92ZUNoaWxkOiAobm9kZTogTm9kZSwgY2hpbGQ6IE5vZGUpID0+IHZvaWQ7XG4gIGFwcGVuZENoaWxkOiAobm9kZTogTm9kZSwgY2hpbGQ6IE5vZGUpID0+IHZvaWQ7XG4gIHBhcmVudE5vZGU6IChub2RlOiBOb2RlKSA9PiBOb2RlO1xuICBuZXh0U2libGluZzogKG5vZGU6IE5vZGUpID0+IE5vZGU7XG4gIHRhZ05hbWU6IChlbG06IEVsZW1lbnQpID0+IHN0cmluZztcbiAgc2V0VGV4dENvbnRlbnQ6IChub2RlOiBOb2RlLCB0ZXh0OiBzdHJpbmcgfCBudWxsKSA9PiB2b2lkO1xufVxuXG5mdW5jdGlvbiBjcmVhdGVFbGVtZW50KHRhZ05hbWU6IGFueSk6IEhUTUxFbGVtZW50IHtcbiAgcmV0dXJuIGRvY3VtZW50LmNyZWF0ZUVsZW1lbnQodGFnTmFtZSk7XG59XG5cbmZ1bmN0aW9uIGNyZWF0ZUVsZW1lbnROUyhuYW1lc3BhY2VVUkk6IHN0cmluZywgcXVhbGlmaWVkTmFtZTogc3RyaW5nKTogRWxlbWVudCB7XG4gIHJldHVybiBkb2N1bWVudC5jcmVhdGVFbGVtZW50TlMobmFtZXNwYWNlVVJJLCBxdWFsaWZpZWROYW1lKTtcbn1cblxuZnVuY3Rpb24gY3JlYXRlVGV4dE5vZGUodGV4dDogc3RyaW5nKTogVGV4dCB7XG4gIHJldHVybiBkb2N1bWVudC5jcmVhdGVUZXh0Tm9kZSh0ZXh0KTtcbn1cblxuZnVuY3Rpb24gaW5zZXJ0QmVmb3JlKHBhcmVudE5vZGU6IE5vZGUsIG5ld05vZGU6IE5vZGUsIHJlZmVyZW5jZU5vZGU6IE5vZGUgfCBudWxsKTogdm9pZCB7XG4gIHBhcmVudE5vZGUuaW5zZXJ0QmVmb3JlKG5ld05vZGUsIHJlZmVyZW5jZU5vZGUpO1xufVxuXG5mdW5jdGlvbiByZW1vdmVDaGlsZChub2RlOiBOb2RlLCBjaGlsZDogTm9kZSk6IHZvaWQge1xuICBub2RlLnJlbW92ZUNoaWxkKGNoaWxkKTtcbn1cblxuZnVuY3Rpb24gYXBwZW5kQ2hpbGQobm9kZTogTm9kZSwgY2hpbGQ6IE5vZGUpOiB2b2lkIHtcbiAgbm9kZS5hcHBlbmRDaGlsZChjaGlsZCk7XG59XG5cbmZ1bmN0aW9uIHBhcmVudE5vZGUobm9kZTogTm9kZSk6IE5vZGUgfCBudWxsIHtcbiAgcmV0dXJuIG5vZGUucGFyZW50Tm9kZTtcbn1cblxuZnVuY3Rpb24gbmV4dFNpYmxpbmcobm9kZTogTm9kZSk6IE5vZGUgfCBudWxsIHtcbiAgcmV0dXJuIG5vZGUubmV4dFNpYmxpbmc7XG59XG5cbmZ1bmN0aW9uIHRhZ05hbWUoZWxtOiBFbGVtZW50KTogc3RyaW5nIHtcbiAgcmV0dXJuIGVsbS50YWdOYW1lO1xufVxuXG5mdW5jdGlvbiBzZXRUZXh0Q29udGVudChub2RlOiBOb2RlLCB0ZXh0OiBzdHJpbmcgfCBudWxsKTogdm9pZCB7XG4gIG5vZGUudGV4dENvbnRlbnQgPSB0ZXh0O1xufVxuXG5leHBvcnQgY29uc3QgaHRtbERvbUFwaSA9IHtcbiAgY3JlYXRlRWxlbWVudCxcbiAgY3JlYXRlRWxlbWVudE5TLFxuICBjcmVhdGVUZXh0Tm9kZSxcbiAgaW5zZXJ0QmVmb3JlLFxuICByZW1vdmVDaGlsZCxcbiAgYXBwZW5kQ2hpbGQsXG4gIHBhcmVudE5vZGUsXG4gIG5leHRTaWJsaW5nLFxuICB0YWdOYW1lLFxuICBzZXRUZXh0Q29udGVudCxcbn0gYXMgRE9NQVBJO1xuXG5leHBvcnQgZGVmYXVsdCBodG1sRG9tQXBpO1xuIiwiZXhwb3J0IGNvbnN0IGFycmF5ID0gQXJyYXkuaXNBcnJheTtcbmV4cG9ydCBmdW5jdGlvbiBwcmltaXRpdmUoczogYW55KTogcyBpcyAoc3RyaW5nIHwgbnVtYmVyKSB7XG4gIHJldHVybiB0eXBlb2YgcyA9PT0gJ3N0cmluZycgfHwgdHlwZW9mIHMgPT09ICdudW1iZXInO1xufVxuIiwiaW1wb3J0IHtWTm9kZSwgVk5vZGVEYXRhfSBmcm9tICcuLi92bm9kZSc7XG5pbXBvcnQge01vZHVsZX0gZnJvbSAnLi9tb2R1bGUnO1xuXG5mdW5jdGlvbiB1cGRhdGVDbGFzcyhvbGRWbm9kZTogVk5vZGUsIHZub2RlOiBWTm9kZSk6IHZvaWQge1xuICB2YXIgY3VyOiBhbnksIG5hbWU6IHN0cmluZywgZWxtOiBFbGVtZW50ID0gdm5vZGUuZWxtIGFzIEVsZW1lbnQsXG4gICAgICBvbGRDbGFzcyA9IChvbGRWbm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuY2xhc3MsXG4gICAgICBrbGFzcyA9ICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuY2xhc3M7XG5cbiAgaWYgKCFvbGRDbGFzcyAmJiAha2xhc3MpIHJldHVybjtcbiAgaWYgKG9sZENsYXNzID09PSBrbGFzcykgcmV0dXJuO1xuICBvbGRDbGFzcyA9IG9sZENsYXNzIHx8IHt9O1xuICBrbGFzcyA9IGtsYXNzIHx8IHt9O1xuXG4gIGZvciAobmFtZSBpbiBvbGRDbGFzcykge1xuICAgIGlmICgha2xhc3NbbmFtZV0pIHtcbiAgICAgIGVsbS5jbGFzc0xpc3QucmVtb3ZlKG5hbWUpO1xuICAgIH1cbiAgfVxuICBmb3IgKG5hbWUgaW4ga2xhc3MpIHtcbiAgICBjdXIgPSBrbGFzc1tuYW1lXTtcbiAgICBpZiAoY3VyICE9PSBvbGRDbGFzc1tuYW1lXSkge1xuICAgICAgKGVsbS5jbGFzc0xpc3QgYXMgYW55KVtjdXIgPyAnYWRkJyA6ICdyZW1vdmUnXShuYW1lKTtcbiAgICB9XG4gIH1cbn1cblxuZXhwb3J0IGNvbnN0IGNsYXNzTW9kdWxlID0ge2NyZWF0ZTogdXBkYXRlQ2xhc3MsIHVwZGF0ZTogdXBkYXRlQ2xhc3N9IGFzIE1vZHVsZTtcbmV4cG9ydCBkZWZhdWx0IGNsYXNzTW9kdWxlO1xuIiwiaW1wb3J0IHtWTm9kZSwgVk5vZGVEYXRhfSBmcm9tICcuLi92bm9kZSc7XG5pbXBvcnQge01vZHVsZX0gZnJvbSAnLi9tb2R1bGUnO1xuXG5mdW5jdGlvbiBpbnZva2VIYW5kbGVyKGhhbmRsZXI6IGFueSwgdm5vZGU/OiBWTm9kZSwgZXZlbnQ/OiBFdmVudCk6IHZvaWQge1xuICBpZiAodHlwZW9mIGhhbmRsZXIgPT09IFwiZnVuY3Rpb25cIikge1xuICAgIC8vIGNhbGwgZnVuY3Rpb24gaGFuZGxlclxuICAgIGhhbmRsZXIuY2FsbCh2bm9kZSwgZXZlbnQsIHZub2RlKTtcbiAgfSBlbHNlIGlmICh0eXBlb2YgaGFuZGxlciA9PT0gXCJvYmplY3RcIikge1xuICAgIC8vIGNhbGwgaGFuZGxlciB3aXRoIGFyZ3VtZW50c1xuICAgIGlmICh0eXBlb2YgaGFuZGxlclswXSA9PT0gXCJmdW5jdGlvblwiKSB7XG4gICAgICAvLyBzcGVjaWFsIGNhc2UgZm9yIHNpbmdsZSBhcmd1bWVudCBmb3IgcGVyZm9ybWFuY2VcbiAgICAgIGlmIChoYW5kbGVyLmxlbmd0aCA9PT0gMikge1xuICAgICAgICBoYW5kbGVyWzBdLmNhbGwodm5vZGUsIGhhbmRsZXJbMV0sIGV2ZW50LCB2bm9kZSk7XG4gICAgICB9IGVsc2Uge1xuICAgICAgICB2YXIgYXJncyA9IGhhbmRsZXIuc2xpY2UoMSk7XG4gICAgICAgIGFyZ3MucHVzaChldmVudCk7XG4gICAgICAgIGFyZ3MucHVzaCh2bm9kZSk7XG4gICAgICAgIGhhbmRsZXJbMF0uYXBwbHkodm5vZGUsIGFyZ3MpO1xuICAgICAgfVxuICAgIH0gZWxzZSB7XG4gICAgICAvLyBjYWxsIG11bHRpcGxlIGhhbmRsZXJzXG4gICAgICBmb3IgKHZhciBpID0gMDsgaSA8IGhhbmRsZXIubGVuZ3RoOyBpKyspIHtcbiAgICAgICAgaW52b2tlSGFuZGxlcihoYW5kbGVyW2ldKTtcbiAgICAgIH1cbiAgICB9XG4gIH1cbn1cblxuZnVuY3Rpb24gaGFuZGxlRXZlbnQoZXZlbnQ6IEV2ZW50LCB2bm9kZTogVk5vZGUpIHtcbiAgdmFyIG5hbWUgPSBldmVudC50eXBlLFxuICAgICAgb24gPSAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLm9uO1xuXG4gIC8vIGNhbGwgZXZlbnQgaGFuZGxlcihzKSBpZiBleGlzdHNcbiAgaWYgKG9uICYmIG9uW25hbWVdKSB7XG4gICAgaW52b2tlSGFuZGxlcihvbltuYW1lXSwgdm5vZGUsIGV2ZW50KTtcbiAgfVxufVxuXG5mdW5jdGlvbiBjcmVhdGVMaXN0ZW5lcigpIHtcbiAgcmV0dXJuIGZ1bmN0aW9uIGhhbmRsZXIoZXZlbnQ6IEV2ZW50KSB7XG4gICAgaGFuZGxlRXZlbnQoZXZlbnQsIChoYW5kbGVyIGFzIGFueSkudm5vZGUpO1xuICB9XG59XG5cbmZ1bmN0aW9uIHVwZGF0ZUV2ZW50TGlzdGVuZXJzKG9sZFZub2RlOiBWTm9kZSwgdm5vZGU/OiBWTm9kZSk6IHZvaWQge1xuICB2YXIgb2xkT24gPSAob2xkVm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLm9uLFxuICAgICAgb2xkTGlzdGVuZXIgPSAob2xkVm5vZGUgYXMgYW55KS5saXN0ZW5lcixcbiAgICAgIG9sZEVsbTogRWxlbWVudCA9IG9sZFZub2RlLmVsbSBhcyBFbGVtZW50LFxuICAgICAgb24gPSB2bm9kZSAmJiAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLm9uLFxuICAgICAgZWxtOiBFbGVtZW50ID0gKHZub2RlICYmIHZub2RlLmVsbSkgYXMgRWxlbWVudCxcbiAgICAgIG5hbWU6IHN0cmluZztcblxuICAvLyBvcHRpbWl6YXRpb24gZm9yIHJldXNlZCBpbW11dGFibGUgaGFuZGxlcnNcbiAgaWYgKG9sZE9uID09PSBvbikge1xuICAgIHJldHVybjtcbiAgfVxuXG4gIC8vIHJlbW92ZSBleGlzdGluZyBsaXN0ZW5lcnMgd2hpY2ggbm8gbG9uZ2VyIHVzZWRcbiAgaWYgKG9sZE9uICYmIG9sZExpc3RlbmVyKSB7XG4gICAgLy8gaWYgZWxlbWVudCBjaGFuZ2VkIG9yIGRlbGV0ZWQgd2UgcmVtb3ZlIGFsbCBleGlzdGluZyBsaXN0ZW5lcnMgdW5jb25kaXRpb25hbGx5XG4gICAgaWYgKCFvbikge1xuICAgICAgZm9yIChuYW1lIGluIG9sZE9uKSB7XG4gICAgICAgIC8vIHJlbW92ZSBsaXN0ZW5lciBpZiBlbGVtZW50IHdhcyBjaGFuZ2VkIG9yIGV4aXN0aW5nIGxpc3RlbmVycyByZW1vdmVkXG4gICAgICAgIG9sZEVsbS5yZW1vdmVFdmVudExpc3RlbmVyKG5hbWUsIG9sZExpc3RlbmVyLCBmYWxzZSk7XG4gICAgICB9XG4gICAgfSBlbHNlIHtcbiAgICAgIGZvciAobmFtZSBpbiBvbGRPbikge1xuICAgICAgICAvLyByZW1vdmUgbGlzdGVuZXIgaWYgZXhpc3RpbmcgbGlzdGVuZXIgcmVtb3ZlZFxuICAgICAgICBpZiAoIW9uW25hbWVdKSB7XG4gICAgICAgICAgb2xkRWxtLnJlbW92ZUV2ZW50TGlzdGVuZXIobmFtZSwgb2xkTGlzdGVuZXIsIGZhbHNlKTtcbiAgICAgICAgfVxuICAgICAgfVxuICAgIH1cbiAgfVxuXG4gIC8vIGFkZCBuZXcgbGlzdGVuZXJzIHdoaWNoIGhhcyBub3QgYWxyZWFkeSBhdHRhY2hlZFxuICBpZiAob24pIHtcbiAgICAvLyByZXVzZSBleGlzdGluZyBsaXN0ZW5lciBvciBjcmVhdGUgbmV3XG4gICAgdmFyIGxpc3RlbmVyID0gKHZub2RlIGFzIGFueSkubGlzdGVuZXIgPSAob2xkVm5vZGUgYXMgYW55KS5saXN0ZW5lciB8fCBjcmVhdGVMaXN0ZW5lcigpO1xuICAgIC8vIHVwZGF0ZSB2bm9kZSBmb3IgbGlzdGVuZXJcbiAgICBsaXN0ZW5lci52bm9kZSA9IHZub2RlO1xuXG4gICAgLy8gaWYgZWxlbWVudCBjaGFuZ2VkIG9yIGFkZGVkIHdlIGFkZCBhbGwgbmVlZGVkIGxpc3RlbmVycyB1bmNvbmRpdGlvbmFsbHlcbiAgICBpZiAoIW9sZE9uKSB7XG4gICAgICBmb3IgKG5hbWUgaW4gb24pIHtcbiAgICAgICAgLy8gYWRkIGxpc3RlbmVyIGlmIGVsZW1lbnQgd2FzIGNoYW5nZWQgb3IgbmV3IGxpc3RlbmVycyBhZGRlZFxuICAgICAgICBlbG0uYWRkRXZlbnRMaXN0ZW5lcihuYW1lLCBsaXN0ZW5lciwgZmFsc2UpO1xuICAgICAgfVxuICAgIH0gZWxzZSB7XG4gICAgICBmb3IgKG5hbWUgaW4gb24pIHtcbiAgICAgICAgLy8gYWRkIGxpc3RlbmVyIGlmIG5ldyBsaXN0ZW5lciBhZGRlZFxuICAgICAgICBpZiAoIW9sZE9uW25hbWVdKSB7XG4gICAgICAgICAgZWxtLmFkZEV2ZW50TGlzdGVuZXIobmFtZSwgbGlzdGVuZXIsIGZhbHNlKTtcbiAgICAgICAgfVxuICAgICAgfVxuICAgIH1cbiAgfVxufVxuXG5leHBvcnQgY29uc3QgZXZlbnRMaXN0ZW5lcnNNb2R1bGUgPSB7XG4gIGNyZWF0ZTogdXBkYXRlRXZlbnRMaXN0ZW5lcnMsXG4gIHVwZGF0ZTogdXBkYXRlRXZlbnRMaXN0ZW5lcnMsXG4gIGRlc3Ryb3k6IHVwZGF0ZUV2ZW50TGlzdGVuZXJzXG59IGFzIE1vZHVsZTtcbmV4cG9ydCBkZWZhdWx0IGV2ZW50TGlzdGVuZXJzTW9kdWxlOyIsImltcG9ydCB7Vk5vZGUsIFZOb2RlRGF0YX0gZnJvbSAnLi4vdm5vZGUnO1xuaW1wb3J0IHtNb2R1bGV9IGZyb20gJy4vbW9kdWxlJztcblxuZnVuY3Rpb24gdXBkYXRlUHJvcHMob2xkVm5vZGU6IFZOb2RlLCB2bm9kZTogVk5vZGUpOiB2b2lkIHtcbiAgdmFyIGtleTogc3RyaW5nLCBjdXI6IGFueSwgb2xkOiBhbnksIGVsbSA9IHZub2RlLmVsbSxcbiAgICAgIG9sZFByb3BzID0gKG9sZFZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5wcm9wcyxcbiAgICAgIHByb3BzID0gKHZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5wcm9wcztcblxuICBpZiAoIW9sZFByb3BzICYmICFwcm9wcykgcmV0dXJuO1xuICBpZiAob2xkUHJvcHMgPT09IHByb3BzKSByZXR1cm47XG4gIG9sZFByb3BzID0gb2xkUHJvcHMgfHwge307XG4gIHByb3BzID0gcHJvcHMgfHwge307XG5cbiAgZm9yIChrZXkgaW4gb2xkUHJvcHMpIHtcbiAgICBpZiAoIXByb3BzW2tleV0pIHtcbiAgICAgIGRlbGV0ZSAoZWxtIGFzIGFueSlba2V5XTtcbiAgICB9XG4gIH1cbiAgZm9yIChrZXkgaW4gcHJvcHMpIHtcbiAgICBjdXIgPSBwcm9wc1trZXldO1xuICAgIG9sZCA9IG9sZFByb3BzW2tleV07XG4gICAgaWYgKG9sZCAhPT0gY3VyICYmIChrZXkgIT09ICd2YWx1ZScgfHwgKGVsbSBhcyBhbnkpW2tleV0gIT09IGN1cikpIHtcbiAgICAgIChlbG0gYXMgYW55KVtrZXldID0gY3VyO1xuICAgIH1cbiAgfVxufVxuXG5leHBvcnQgY29uc3QgcHJvcHNNb2R1bGUgPSB7Y3JlYXRlOiB1cGRhdGVQcm9wcywgdXBkYXRlOiB1cGRhdGVQcm9wc30gYXMgTW9kdWxlO1xuZXhwb3J0IGRlZmF1bHQgcHJvcHNNb2R1bGU7IiwiaW1wb3J0IHtWTm9kZSwgVk5vZGVEYXRhfSBmcm9tICcuLi92bm9kZSc7XG5pbXBvcnQge01vZHVsZX0gZnJvbSAnLi9tb2R1bGUnO1xuXG52YXIgcmFmID0gKHR5cGVvZiB3aW5kb3cgIT09ICd1bmRlZmluZWQnICYmIHdpbmRvdy5yZXF1ZXN0QW5pbWF0aW9uRnJhbWUpIHx8IHNldFRpbWVvdXQ7XG52YXIgbmV4dEZyYW1lID0gZnVuY3Rpb24oZm46IGFueSkgeyByYWYoZnVuY3Rpb24oKSB7IHJhZihmbik7IH0pOyB9O1xuXG5mdW5jdGlvbiBzZXROZXh0RnJhbWUob2JqOiBhbnksIHByb3A6IHN0cmluZywgdmFsOiBhbnkpOiB2b2lkIHtcbiAgbmV4dEZyYW1lKGZ1bmN0aW9uKCkgeyBvYmpbcHJvcF0gPSB2YWw7IH0pO1xufVxuXG5mdW5jdGlvbiB1cGRhdGVTdHlsZShvbGRWbm9kZTogVk5vZGUsIHZub2RlOiBWTm9kZSk6IHZvaWQge1xuICB2YXIgY3VyOiBhbnksIG5hbWU6IHN0cmluZywgZWxtID0gdm5vZGUuZWxtLFxuICAgICAgb2xkU3R5bGUgPSAob2xkVm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLnN0eWxlLFxuICAgICAgc3R5bGUgPSAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLnN0eWxlO1xuXG4gIGlmICghb2xkU3R5bGUgJiYgIXN0eWxlKSByZXR1cm47XG4gIGlmIChvbGRTdHlsZSA9PT0gc3R5bGUpIHJldHVybjtcbiAgb2xkU3R5bGUgPSBvbGRTdHlsZSB8fCB7fTtcbiAgc3R5bGUgPSBzdHlsZSB8fCB7fTtcbiAgdmFyIG9sZEhhc0RlbCA9ICdkZWxheWVkJyBpbiBvbGRTdHlsZTtcblxuICBmb3IgKG5hbWUgaW4gb2xkU3R5bGUpIHtcbiAgICBpZiAoIXN0eWxlW25hbWVdKSB7XG4gICAgICBpZiAobmFtZS5zdGFydHNXaXRoKCctLScpKSB7XG4gICAgICAgIChlbG0gYXMgYW55KS5zdHlsZS5yZW1vdmVQcm9wZXJ0eShuYW1lKTtcbiAgICAgIH0gZWxzZSB7XG4gICAgICAgIChlbG0gYXMgYW55KS5zdHlsZVtuYW1lXSA9ICcnO1xuICAgICAgfVxuICAgIH1cbiAgfVxuICBmb3IgKG5hbWUgaW4gc3R5bGUpIHtcbiAgICBjdXIgPSBzdHlsZVtuYW1lXTtcbiAgICBpZiAobmFtZSA9PT0gJ2RlbGF5ZWQnKSB7XG4gICAgICBmb3IgKG5hbWUgaW4gc3R5bGUuZGVsYXllZCkge1xuICAgICAgICBjdXIgPSBzdHlsZS5kZWxheWVkW25hbWVdO1xuICAgICAgICBpZiAoIW9sZEhhc0RlbCB8fCBjdXIgIT09IG9sZFN0eWxlLmRlbGF5ZWRbbmFtZV0pIHtcbiAgICAgICAgICBzZXROZXh0RnJhbWUoKGVsbSBhcyBhbnkpLnN0eWxlLCBuYW1lLCBjdXIpO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfSBlbHNlIGlmIChuYW1lICE9PSAncmVtb3ZlJyAmJiBjdXIgIT09IG9sZFN0eWxlW25hbWVdKSB7XG4gICAgICBpZiAobmFtZS5zdGFydHNXaXRoKCctLScpKSB7XG4gICAgICAgIChlbG0gYXMgYW55KS5zdHlsZS5zZXRQcm9wZXJ0eShuYW1lLCBjdXIpO1xuICAgICAgfSBlbHNlIHtcbiAgICAgICAgKGVsbSBhcyBhbnkpLnN0eWxlW25hbWVdID0gY3VyO1xuICAgICAgfVxuICAgIH1cbiAgfVxufVxuXG5mdW5jdGlvbiBhcHBseURlc3Ryb3lTdHlsZSh2bm9kZTogVk5vZGUpOiB2b2lkIHtcbiAgdmFyIHN0eWxlOiBhbnksIG5hbWU6IHN0cmluZywgZWxtID0gdm5vZGUuZWxtLCBzID0gKHZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5zdHlsZTtcbiAgaWYgKCFzIHx8ICEoc3R5bGUgPSBzLmRlc3Ryb3kpKSByZXR1cm47XG4gIGZvciAobmFtZSBpbiBzdHlsZSkge1xuICAgIChlbG0gYXMgYW55KS5zdHlsZVtuYW1lXSA9IHN0eWxlW25hbWVdO1xuICB9XG59XG5cbmZ1bmN0aW9uIGFwcGx5UmVtb3ZlU3R5bGUodm5vZGU6IFZOb2RlLCBybTogKCkgPT4gdm9pZCk6IHZvaWQge1xuICB2YXIgcyA9ICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuc3R5bGU7XG4gIGlmICghcyB8fCAhcy5yZW1vdmUpIHtcbiAgICBybSgpO1xuICAgIHJldHVybjtcbiAgfVxuICB2YXIgbmFtZTogc3RyaW5nLCBlbG0gPSB2bm9kZS5lbG0sIGkgPSAwLCBjb21wU3R5bGU6IENTU1N0eWxlRGVjbGFyYXRpb24sXG4gICAgICBzdHlsZSA9IHMucmVtb3ZlLCBhbW91bnQgPSAwLCBhcHBsaWVkOiBBcnJheTxzdHJpbmc+ID0gW107XG4gIGZvciAobmFtZSBpbiBzdHlsZSkge1xuICAgIGFwcGxpZWQucHVzaChuYW1lKTtcbiAgICAoZWxtIGFzIGFueSkuc3R5bGVbbmFtZV0gPSBzdHlsZVtuYW1lXTtcbiAgfVxuICBjb21wU3R5bGUgPSBnZXRDb21wdXRlZFN0eWxlKGVsbSBhcyBFbGVtZW50KTtcbiAgdmFyIHByb3BzID0gKGNvbXBTdHlsZSBhcyBhbnkpWyd0cmFuc2l0aW9uLXByb3BlcnR5J10uc3BsaXQoJywgJyk7XG4gIGZvciAoOyBpIDwgcHJvcHMubGVuZ3RoOyArK2kpIHtcbiAgICBpZihhcHBsaWVkLmluZGV4T2YocHJvcHNbaV0pICE9PSAtMSkgYW1vdW50Kys7XG4gIH1cbiAgKGVsbSBhcyBFbGVtZW50KS5hZGRFdmVudExpc3RlbmVyKCd0cmFuc2l0aW9uZW5kJywgZnVuY3Rpb24gKGV2OiBUcmFuc2l0aW9uRXZlbnQpIHtcbiAgICBpZiAoZXYudGFyZ2V0ID09PSBlbG0pIC0tYW1vdW50O1xuICAgIGlmIChhbW91bnQgPT09IDApIHJtKCk7XG4gIH0pO1xufVxuXG5leHBvcnQgY29uc3Qgc3R5bGVNb2R1bGUgPSB7XG4gIGNyZWF0ZTogdXBkYXRlU3R5bGUsXG4gIHVwZGF0ZTogdXBkYXRlU3R5bGUsXG4gIGRlc3Ryb3k6IGFwcGx5RGVzdHJveVN0eWxlLFxuICByZW1vdmU6IGFwcGx5UmVtb3ZlU3R5bGVcbn0gYXMgTW9kdWxlO1xuZXhwb3J0IGRlZmF1bHQgc3R5bGVNb2R1bGU7XG4iLCIvKiBnbG9iYWwgbW9kdWxlLCBkb2N1bWVudCwgTm9kZSAqL1xuaW1wb3J0IHtNb2R1bGV9IGZyb20gJy4vbW9kdWxlcy9tb2R1bGUnO1xuaW1wb3J0IHtIb29rc30gZnJvbSAnLi9ob29rcyc7XG5pbXBvcnQgdm5vZGUsIHtWTm9kZSwgVk5vZGVEYXRhLCBLZXl9IGZyb20gJy4vdm5vZGUnO1xuaW1wb3J0ICogYXMgaXMgZnJvbSAnLi9pcyc7XG5pbXBvcnQgaHRtbERvbUFwaSwge0RPTUFQSX0gZnJvbSAnLi9odG1sZG9tYXBpJztcblxuZnVuY3Rpb24gaXNVbmRlZihzOiBhbnkpOiBib29sZWFuIHsgcmV0dXJuIHMgPT09IHVuZGVmaW5lZDsgfVxuZnVuY3Rpb24gaXNEZWYoczogYW55KTogYm9vbGVhbiB7IHJldHVybiBzICE9PSB1bmRlZmluZWQ7IH1cblxudHlwZSBWTm9kZVF1ZXVlID0gQXJyYXk8Vk5vZGU+O1xuXG5jb25zdCBlbXB0eU5vZGUgPSB2bm9kZSgnJywge30sIFtdLCB1bmRlZmluZWQsIHVuZGVmaW5lZCk7XG5cbmZ1bmN0aW9uIHNhbWVWbm9kZSh2bm9kZTE6IFZOb2RlLCB2bm9kZTI6IFZOb2RlKTogYm9vbGVhbiB7XG4gIHJldHVybiB2bm9kZTEua2V5ID09PSB2bm9kZTIua2V5ICYmIHZub2RlMS5zZWwgPT09IHZub2RlMi5zZWw7XG59XG5cbmZ1bmN0aW9uIGlzVm5vZGUodm5vZGU6IGFueSk6IHZub2RlIGlzIFZOb2RlIHtcbiAgcmV0dXJuIHZub2RlLnNlbCAhPT0gdW5kZWZpbmVkO1xufVxuXG50eXBlIEtleVRvSW5kZXhNYXAgPSB7W2tleTogc3RyaW5nXTogbnVtYmVyfTtcblxudHlwZSBBcnJheXNPZjxUPiA9IHtcbiAgW0sgaW4ga2V5b2YgVF06IChUW0tdKVtdO1xufVxuXG50eXBlIE1vZHVsZUhvb2tzID0gQXJyYXlzT2Y8TW9kdWxlPjtcblxuZnVuY3Rpb24gY3JlYXRlS2V5VG9PbGRJZHgoY2hpbGRyZW46IEFycmF5PFZOb2RlPiwgYmVnaW5JZHg6IG51bWJlciwgZW5kSWR4OiBudW1iZXIpOiBLZXlUb0luZGV4TWFwIHtcbiAgbGV0IGk6IG51bWJlciwgbWFwOiBLZXlUb0luZGV4TWFwID0ge30sIGtleTogS2V5O1xuICBmb3IgKGkgPSBiZWdpbklkeDsgaSA8PSBlbmRJZHg7ICsraSkge1xuICAgIGtleSA9IGNoaWxkcmVuW2ldLmtleTtcbiAgICBpZiAoa2V5ICE9PSB1bmRlZmluZWQpIG1hcFtrZXldID0gaTtcbiAgfVxuICByZXR1cm4gbWFwO1xufVxuXG5jb25zdCBob29rczogKGtleW9mIE1vZHVsZSlbXSA9IFsnY3JlYXRlJywgJ3VwZGF0ZScsICdyZW1vdmUnLCAnZGVzdHJveScsICdwcmUnLCAncG9zdCddO1xuXG5leHBvcnQge2h9IGZyb20gJy4vaCc7XG5leHBvcnQge3RodW5rfSBmcm9tICcuL3RodW5rJztcblxuZXhwb3J0IGZ1bmN0aW9uIGluaXQobW9kdWxlczogQXJyYXk8UGFydGlhbDxNb2R1bGU+PiwgZG9tQXBpPzogRE9NQVBJKSB7XG4gIGxldCBpOiBudW1iZXIsIGo6IG51bWJlciwgY2JzID0gKHt9IGFzIE1vZHVsZUhvb2tzKTtcblxuICBjb25zdCBhcGk6IERPTUFQSSA9IGRvbUFwaSAhPT0gdW5kZWZpbmVkID8gZG9tQXBpIDogaHRtbERvbUFwaTtcblxuICBmb3IgKGkgPSAwOyBpIDwgaG9va3MubGVuZ3RoOyArK2kpIHtcbiAgICBjYnNbaG9va3NbaV1dID0gW107XG4gICAgZm9yIChqID0gMDsgaiA8IG1vZHVsZXMubGVuZ3RoOyArK2opIHtcbiAgICAgIGNvbnN0IGhvb2sgPSBtb2R1bGVzW2pdW2hvb2tzW2ldXTtcbiAgICAgIGlmIChob29rICE9PSB1bmRlZmluZWQpIHtcbiAgICAgICAgKGNic1tob29rc1tpXV0gYXMgQXJyYXk8YW55PikucHVzaChob29rKTtcbiAgICAgIH1cbiAgICB9XG4gIH1cblxuICBmdW5jdGlvbiBlbXB0eU5vZGVBdChlbG06IEVsZW1lbnQpIHtcbiAgICBjb25zdCBpZCA9IGVsbS5pZCA/ICcjJyArIGVsbS5pZCA6ICcnO1xuICAgIGNvbnN0IGMgPSBlbG0uY2xhc3NOYW1lID8gJy4nICsgZWxtLmNsYXNzTmFtZS5zcGxpdCgnICcpLmpvaW4oJy4nKSA6ICcnO1xuICAgIHJldHVybiB2bm9kZShhcGkudGFnTmFtZShlbG0pLnRvTG93ZXJDYXNlKCkgKyBpZCArIGMsIHt9LCBbXSwgdW5kZWZpbmVkLCBlbG0pO1xuICB9XG5cbiAgZnVuY3Rpb24gY3JlYXRlUm1DYihjaGlsZEVsbTogTm9kZSwgbGlzdGVuZXJzOiBudW1iZXIpIHtcbiAgICByZXR1cm4gZnVuY3Rpb24gcm1DYigpIHtcbiAgICAgIGlmICgtLWxpc3RlbmVycyA9PT0gMCkge1xuICAgICAgICBjb25zdCBwYXJlbnQgPSBhcGkucGFyZW50Tm9kZShjaGlsZEVsbSk7XG4gICAgICAgIGFwaS5yZW1vdmVDaGlsZChwYXJlbnQsIGNoaWxkRWxtKTtcbiAgICAgIH1cbiAgICB9O1xuICB9XG5cbiAgZnVuY3Rpb24gY3JlYXRlRWxtKHZub2RlOiBWTm9kZSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlOiBWTm9kZVF1ZXVlKTogTm9kZSB7XG4gICAgbGV0IGk6IGFueSwgZGF0YSA9IHZub2RlLmRhdGE7XG4gICAgaWYgKGRhdGEgIT09IHVuZGVmaW5lZCkge1xuICAgICAgaWYgKGlzRGVmKGkgPSBkYXRhLmhvb2spICYmIGlzRGVmKGkgPSBpLmluaXQpKSB7XG4gICAgICAgIGkodm5vZGUpO1xuICAgICAgICBkYXRhID0gdm5vZGUuZGF0YTtcbiAgICAgIH1cbiAgICB9XG4gICAgbGV0IGNoaWxkcmVuID0gdm5vZGUuY2hpbGRyZW4sIHNlbCA9IHZub2RlLnNlbDtcbiAgICBpZiAoc2VsICE9PSB1bmRlZmluZWQpIHtcbiAgICAgIC8vIFBhcnNlIHNlbGVjdG9yXG4gICAgICBjb25zdCBoYXNoSWR4ID0gc2VsLmluZGV4T2YoJyMnKTtcbiAgICAgIGNvbnN0IGRvdElkeCA9IHNlbC5pbmRleE9mKCcuJywgaGFzaElkeCk7XG4gICAgICBjb25zdCBoYXNoID0gaGFzaElkeCA+IDAgPyBoYXNoSWR4IDogc2VsLmxlbmd0aDtcbiAgICAgIGNvbnN0IGRvdCA9IGRvdElkeCA+IDAgPyBkb3RJZHggOiBzZWwubGVuZ3RoO1xuICAgICAgY29uc3QgdGFnID0gaGFzaElkeCAhPT0gLTEgfHwgZG90SWR4ICE9PSAtMSA/IHNlbC5zbGljZSgwLCBNYXRoLm1pbihoYXNoLCBkb3QpKSA6IHNlbDtcbiAgICAgIGNvbnN0IGVsbSA9IHZub2RlLmVsbSA9IGlzRGVmKGRhdGEpICYmIGlzRGVmKGkgPSAoZGF0YSBhcyBWTm9kZURhdGEpLm5zKSA/IGFwaS5jcmVhdGVFbGVtZW50TlMoaSwgdGFnKVxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDogYXBpLmNyZWF0ZUVsZW1lbnQodGFnKTtcbiAgICAgIGlmIChoYXNoIDwgZG90KSBlbG0uaWQgPSBzZWwuc2xpY2UoaGFzaCArIDEsIGRvdCk7XG4gICAgICBpZiAoZG90SWR4ID4gMCkgZWxtLmNsYXNzTmFtZSA9IHNlbC5zbGljZShkb3QgKyAxKS5yZXBsYWNlKC9cXC4vZywgJyAnKTtcbiAgICAgIGlmIChpcy5hcnJheShjaGlsZHJlbikpIHtcbiAgICAgICAgZm9yIChpID0gMDsgaSA8IGNoaWxkcmVuLmxlbmd0aDsgKytpKSB7XG4gICAgICAgICAgYXBpLmFwcGVuZENoaWxkKGVsbSwgY3JlYXRlRWxtKGNoaWxkcmVuW2ldIGFzIFZOb2RlLCBpbnNlcnRlZFZub2RlUXVldWUpKTtcbiAgICAgICAgfVxuICAgICAgfSBlbHNlIGlmIChpcy5wcmltaXRpdmUodm5vZGUudGV4dCkpIHtcbiAgICAgICAgYXBpLmFwcGVuZENoaWxkKGVsbSwgYXBpLmNyZWF0ZVRleHROb2RlKHZub2RlLnRleHQpKTtcbiAgICAgIH1cbiAgICAgIGZvciAoaSA9IDA7IGkgPCBjYnMuY3JlYXRlLmxlbmd0aDsgKytpKSBjYnMuY3JlYXRlW2ldKGVtcHR5Tm9kZSwgdm5vZGUpO1xuICAgICAgaSA9ICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuaG9vazsgLy8gUmV1c2UgdmFyaWFibGVcbiAgICAgIGlmIChpc0RlZihpKSkge1xuICAgICAgICBpZiAoaS5jcmVhdGUpIGkuY3JlYXRlKGVtcHR5Tm9kZSwgdm5vZGUpO1xuICAgICAgICBpZiAoaS5pbnNlcnQpIGluc2VydGVkVm5vZGVRdWV1ZS5wdXNoKHZub2RlKTtcbiAgICAgIH1cbiAgICB9IGVsc2Uge1xuICAgICAgdm5vZGUuZWxtID0gYXBpLmNyZWF0ZVRleHROb2RlKHZub2RlLnRleHQgYXMgc3RyaW5nKTtcbiAgICB9XG4gICAgcmV0dXJuIHZub2RlLmVsbTtcbiAgfVxuXG4gIGZ1bmN0aW9uIGFkZFZub2RlcyhwYXJlbnRFbG06IE5vZGUsXG4gICAgICAgICAgICAgICAgICAgICBiZWZvcmU6IE5vZGUgfCBudWxsLFxuICAgICAgICAgICAgICAgICAgICAgdm5vZGVzOiBBcnJheTxWTm9kZT4sXG4gICAgICAgICAgICAgICAgICAgICBzdGFydElkeDogbnVtYmVyLFxuICAgICAgICAgICAgICAgICAgICAgZW5kSWR4OiBudW1iZXIsXG4gICAgICAgICAgICAgICAgICAgICBpbnNlcnRlZFZub2RlUXVldWU6IFZOb2RlUXVldWUpIHtcbiAgICBmb3IgKDsgc3RhcnRJZHggPD0gZW5kSWR4OyArK3N0YXJ0SWR4KSB7XG4gICAgICBhcGkuaW5zZXJ0QmVmb3JlKHBhcmVudEVsbSwgY3JlYXRlRWxtKHZub2Rlc1tzdGFydElkeF0sIGluc2VydGVkVm5vZGVRdWV1ZSksIGJlZm9yZSk7XG4gICAgfVxuICB9XG5cbiAgZnVuY3Rpb24gaW52b2tlRGVzdHJveUhvb2sodm5vZGU6IFZOb2RlKSB7XG4gICAgbGV0IGk6IGFueSwgajogbnVtYmVyLCBkYXRhID0gdm5vZGUuZGF0YTtcbiAgICBpZiAoZGF0YSAhPT0gdW5kZWZpbmVkKSB7XG4gICAgICBpZiAoaXNEZWYoaSA9IGRhdGEuaG9vaykgJiYgaXNEZWYoaSA9IGkuZGVzdHJveSkpIGkodm5vZGUpO1xuICAgICAgZm9yIChpID0gMDsgaSA8IGNicy5kZXN0cm95Lmxlbmd0aDsgKytpKSBjYnMuZGVzdHJveVtpXSh2bm9kZSk7XG4gICAgICBpZiAodm5vZGUuY2hpbGRyZW4gIT09IHVuZGVmaW5lZCkge1xuICAgICAgICBmb3IgKGogPSAwOyBqIDwgdm5vZGUuY2hpbGRyZW4ubGVuZ3RoOyArK2opIHtcbiAgICAgICAgICBpID0gdm5vZGUuY2hpbGRyZW5bal07XG4gICAgICAgICAgaWYgKHR5cGVvZiBpICE9PSBcInN0cmluZ1wiKSB7XG4gICAgICAgICAgICBpbnZva2VEZXN0cm95SG9vayhpKTtcbiAgICAgICAgICB9XG4gICAgICAgIH1cbiAgICAgIH1cbiAgICB9XG4gIH1cblxuICBmdW5jdGlvbiByZW1vdmVWbm9kZXMocGFyZW50RWxtOiBOb2RlLFxuICAgICAgICAgICAgICAgICAgICAgICAgdm5vZGVzOiBBcnJheTxWTm9kZT4sXG4gICAgICAgICAgICAgICAgICAgICAgICBzdGFydElkeDogbnVtYmVyLFxuICAgICAgICAgICAgICAgICAgICAgICAgZW5kSWR4OiBudW1iZXIpOiB2b2lkIHtcbiAgICBmb3IgKDsgc3RhcnRJZHggPD0gZW5kSWR4OyArK3N0YXJ0SWR4KSB7XG4gICAgICBsZXQgaTogYW55LCBsaXN0ZW5lcnM6IG51bWJlciwgcm06ICgpID0+IHZvaWQsIGNoID0gdm5vZGVzW3N0YXJ0SWR4XTtcbiAgICAgIGlmIChpc0RlZihjaCkpIHtcbiAgICAgICAgaWYgKGlzRGVmKGNoLnNlbCkpIHtcbiAgICAgICAgICBpbnZva2VEZXN0cm95SG9vayhjaCk7XG4gICAgICAgICAgbGlzdGVuZXJzID0gY2JzLnJlbW92ZS5sZW5ndGggKyAxO1xuICAgICAgICAgIHJtID0gY3JlYXRlUm1DYihjaC5lbG0gYXMgTm9kZSwgbGlzdGVuZXJzKTtcbiAgICAgICAgICBmb3IgKGkgPSAwOyBpIDwgY2JzLnJlbW92ZS5sZW5ndGg7ICsraSkgY2JzLnJlbW92ZVtpXShjaCwgcm0pO1xuICAgICAgICAgIGlmIChpc0RlZihpID0gY2guZGF0YSkgJiYgaXNEZWYoaSA9IGkuaG9vaykgJiYgaXNEZWYoaSA9IGkucmVtb3ZlKSkge1xuICAgICAgICAgICAgaShjaCwgcm0pO1xuICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICBybSgpO1xuICAgICAgICAgIH1cbiAgICAgICAgfSBlbHNlIHsgLy8gVGV4dCBub2RlXG4gICAgICAgICAgYXBpLnJlbW92ZUNoaWxkKHBhcmVudEVsbSwgY2guZWxtIGFzIE5vZGUpO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfVxuICB9XG5cbiAgZnVuY3Rpb24gdXBkYXRlQ2hpbGRyZW4ocGFyZW50RWxtOiBOb2RlLFxuICAgICAgICAgICAgICAgICAgICAgICAgICBvbGRDaDogQXJyYXk8Vk5vZGU+LFxuICAgICAgICAgICAgICAgICAgICAgICAgICBuZXdDaDogQXJyYXk8Vk5vZGU+LFxuICAgICAgICAgICAgICAgICAgICAgICAgICBpbnNlcnRlZFZub2RlUXVldWU6IFZOb2RlUXVldWUpIHtcbiAgICBsZXQgb2xkU3RhcnRJZHggPSAwLCBuZXdTdGFydElkeCA9IDA7XG4gICAgbGV0IG9sZEVuZElkeCA9IG9sZENoLmxlbmd0aCAtIDE7XG4gICAgbGV0IG9sZFN0YXJ0Vm5vZGUgPSBvbGRDaFswXTtcbiAgICBsZXQgb2xkRW5kVm5vZGUgPSBvbGRDaFtvbGRFbmRJZHhdO1xuICAgIGxldCBuZXdFbmRJZHggPSBuZXdDaC5sZW5ndGggLSAxO1xuICAgIGxldCBuZXdTdGFydFZub2RlID0gbmV3Q2hbMF07XG4gICAgbGV0IG5ld0VuZFZub2RlID0gbmV3Q2hbbmV3RW5kSWR4XTtcbiAgICBsZXQgb2xkS2V5VG9JZHg6IGFueTtcbiAgICBsZXQgaWR4SW5PbGQ6IG51bWJlcjtcbiAgICBsZXQgZWxtVG9Nb3ZlOiBWTm9kZTtcbiAgICBsZXQgYmVmb3JlOiBhbnk7XG5cbiAgICB3aGlsZSAob2xkU3RhcnRJZHggPD0gb2xkRW5kSWR4ICYmIG5ld1N0YXJ0SWR4IDw9IG5ld0VuZElkeCkge1xuICAgICAgaWYgKGlzVW5kZWYob2xkU3RhcnRWbm9kZSkpIHtcbiAgICAgICAgb2xkU3RhcnRWbm9kZSA9IG9sZENoWysrb2xkU3RhcnRJZHhdOyAvLyBWbm9kZSBoYXMgYmVlbiBtb3ZlZCBsZWZ0XG4gICAgICB9IGVsc2UgaWYgKGlzVW5kZWYob2xkRW5kVm5vZGUpKSB7XG4gICAgICAgIG9sZEVuZFZub2RlID0gb2xkQ2hbLS1vbGRFbmRJZHhdO1xuICAgICAgfSBlbHNlIGlmIChzYW1lVm5vZGUob2xkU3RhcnRWbm9kZSwgbmV3U3RhcnRWbm9kZSkpIHtcbiAgICAgICAgcGF0Y2hWbm9kZShvbGRTdGFydFZub2RlLCBuZXdTdGFydFZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpO1xuICAgICAgICBvbGRTdGFydFZub2RlID0gb2xkQ2hbKytvbGRTdGFydElkeF07XG4gICAgICAgIG5ld1N0YXJ0Vm5vZGUgPSBuZXdDaFsrK25ld1N0YXJ0SWR4XTtcbiAgICAgIH0gZWxzZSBpZiAoc2FtZVZub2RlKG9sZEVuZFZub2RlLCBuZXdFbmRWbm9kZSkpIHtcbiAgICAgICAgcGF0Y2hWbm9kZShvbGRFbmRWbm9kZSwgbmV3RW5kVm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICAgIG9sZEVuZFZub2RlID0gb2xkQ2hbLS1vbGRFbmRJZHhdO1xuICAgICAgICBuZXdFbmRWbm9kZSA9IG5ld0NoWy0tbmV3RW5kSWR4XTtcbiAgICAgIH0gZWxzZSBpZiAoc2FtZVZub2RlKG9sZFN0YXJ0Vm5vZGUsIG5ld0VuZFZub2RlKSkgeyAvLyBWbm9kZSBtb3ZlZCByaWdodFxuICAgICAgICBwYXRjaFZub2RlKG9sZFN0YXJ0Vm5vZGUsIG5ld0VuZFZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpO1xuICAgICAgICBhcGkuaW5zZXJ0QmVmb3JlKHBhcmVudEVsbSwgb2xkU3RhcnRWbm9kZS5lbG0gYXMgTm9kZSwgYXBpLm5leHRTaWJsaW5nKG9sZEVuZFZub2RlLmVsbSBhcyBOb2RlKSk7XG4gICAgICAgIG9sZFN0YXJ0Vm5vZGUgPSBvbGRDaFsrK29sZFN0YXJ0SWR4XTtcbiAgICAgICAgbmV3RW5kVm5vZGUgPSBuZXdDaFstLW5ld0VuZElkeF07XG4gICAgICB9IGVsc2UgaWYgKHNhbWVWbm9kZShvbGRFbmRWbm9kZSwgbmV3U3RhcnRWbm9kZSkpIHsgLy8gVm5vZGUgbW92ZWQgbGVmdFxuICAgICAgICBwYXRjaFZub2RlKG9sZEVuZFZub2RlLCBuZXdTdGFydFZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpO1xuICAgICAgICBhcGkuaW5zZXJ0QmVmb3JlKHBhcmVudEVsbSwgb2xkRW5kVm5vZGUuZWxtIGFzIE5vZGUsIG9sZFN0YXJ0Vm5vZGUuZWxtIGFzIE5vZGUpO1xuICAgICAgICBvbGRFbmRWbm9kZSA9IG9sZENoWy0tb2xkRW5kSWR4XTtcbiAgICAgICAgbmV3U3RhcnRWbm9kZSA9IG5ld0NoWysrbmV3U3RhcnRJZHhdO1xuICAgICAgfSBlbHNlIHtcbiAgICAgICAgaWYgKG9sZEtleVRvSWR4ID09PSB1bmRlZmluZWQpIHtcbiAgICAgICAgICBvbGRLZXlUb0lkeCA9IGNyZWF0ZUtleVRvT2xkSWR4KG9sZENoLCBvbGRTdGFydElkeCwgb2xkRW5kSWR4KTtcbiAgICAgICAgfVxuICAgICAgICBpZHhJbk9sZCA9IG9sZEtleVRvSWR4W25ld1N0YXJ0Vm5vZGUua2V5IGFzIHN0cmluZ107XG4gICAgICAgIGlmIChpc1VuZGVmKGlkeEluT2xkKSkgeyAvLyBOZXcgZWxlbWVudFxuICAgICAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50RWxtLCBjcmVhdGVFbG0obmV3U3RhcnRWbm9kZSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKSwgb2xkU3RhcnRWbm9kZS5lbG0gYXMgTm9kZSk7XG4gICAgICAgICAgbmV3U3RhcnRWbm9kZSA9IG5ld0NoWysrbmV3U3RhcnRJZHhdO1xuICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgIGVsbVRvTW92ZSA9IG9sZENoW2lkeEluT2xkXTtcbiAgICAgICAgICBpZiAoZWxtVG9Nb3ZlLnNlbCAhPT0gbmV3U3RhcnRWbm9kZS5zZWwpIHtcbiAgICAgICAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50RWxtLCBjcmVhdGVFbG0obmV3U3RhcnRWbm9kZSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKSwgb2xkU3RhcnRWbm9kZS5lbG0gYXMgTm9kZSk7XG4gICAgICAgICAgfSBlbHNlIHtcbiAgICAgICAgICAgIHBhdGNoVm5vZGUoZWxtVG9Nb3ZlLCBuZXdTdGFydFZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpO1xuICAgICAgICAgICAgb2xkQ2hbaWR4SW5PbGRdID0gdW5kZWZpbmVkIGFzIGFueTtcbiAgICAgICAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50RWxtLCAoZWxtVG9Nb3ZlLmVsbSBhcyBOb2RlKSwgb2xkU3RhcnRWbm9kZS5lbG0gYXMgTm9kZSk7XG4gICAgICAgICAgfVxuICAgICAgICAgIG5ld1N0YXJ0Vm5vZGUgPSBuZXdDaFsrK25ld1N0YXJ0SWR4XTtcbiAgICAgICAgfVxuICAgICAgfVxuICAgIH1cbiAgICBpZiAob2xkU3RhcnRJZHggPiBvbGRFbmRJZHgpIHtcbiAgICAgIGJlZm9yZSA9IGlzVW5kZWYobmV3Q2hbbmV3RW5kSWR4KzFdKSA/IG51bGwgOiBuZXdDaFtuZXdFbmRJZHgrMV0uZWxtO1xuICAgICAgYWRkVm5vZGVzKHBhcmVudEVsbSwgYmVmb3JlLCBuZXdDaCwgbmV3U3RhcnRJZHgsIG5ld0VuZElkeCwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKTtcbiAgICB9IGVsc2UgaWYgKG5ld1N0YXJ0SWR4ID4gbmV3RW5kSWR4KSB7XG4gICAgICByZW1vdmVWbm9kZXMocGFyZW50RWxtLCBvbGRDaCwgb2xkU3RhcnRJZHgsIG9sZEVuZElkeCk7XG4gICAgfVxuICB9XG5cbiAgZnVuY3Rpb24gcGF0Y2hWbm9kZShvbGRWbm9kZTogVk5vZGUsIHZub2RlOiBWTm9kZSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlOiBWTm9kZVF1ZXVlKSB7XG4gICAgbGV0IGk6IGFueSwgaG9vazogYW55O1xuICAgIGlmIChpc0RlZihpID0gdm5vZGUuZGF0YSkgJiYgaXNEZWYoaG9vayA9IGkuaG9vaykgJiYgaXNEZWYoaSA9IGhvb2sucHJlcGF0Y2gpKSB7XG4gICAgICBpKG9sZFZub2RlLCB2bm9kZSk7XG4gICAgfVxuICAgIGNvbnN0IGVsbSA9IHZub2RlLmVsbSA9IChvbGRWbm9kZS5lbG0gYXMgTm9kZSk7XG4gICAgbGV0IG9sZENoID0gb2xkVm5vZGUuY2hpbGRyZW47XG4gICAgbGV0IGNoID0gdm5vZGUuY2hpbGRyZW47XG4gICAgaWYgKG9sZFZub2RlID09PSB2bm9kZSkgcmV0dXJuO1xuICAgIGlmICh2bm9kZS5kYXRhICE9PSB1bmRlZmluZWQpIHtcbiAgICAgIGZvciAoaSA9IDA7IGkgPCBjYnMudXBkYXRlLmxlbmd0aDsgKytpKSBjYnMudXBkYXRlW2ldKG9sZFZub2RlLCB2bm9kZSk7XG4gICAgICBpID0gdm5vZGUuZGF0YS5ob29rO1xuICAgICAgaWYgKGlzRGVmKGkpICYmIGlzRGVmKGkgPSBpLnVwZGF0ZSkpIGkob2xkVm5vZGUsIHZub2RlKTtcbiAgICB9XG4gICAgaWYgKGlzVW5kZWYodm5vZGUudGV4dCkpIHtcbiAgICAgIGlmIChpc0RlZihvbGRDaCkgJiYgaXNEZWYoY2gpKSB7XG4gICAgICAgIGlmIChvbGRDaCAhPT0gY2gpIHVwZGF0ZUNoaWxkcmVuKGVsbSwgb2xkQ2ggYXMgQXJyYXk8Vk5vZGU+LCBjaCBhcyBBcnJheTxWTm9kZT4sIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICB9IGVsc2UgaWYgKGlzRGVmKGNoKSkge1xuICAgICAgICBpZiAoaXNEZWYob2xkVm5vZGUudGV4dCkpIGFwaS5zZXRUZXh0Q29udGVudChlbG0sICcnKTtcbiAgICAgICAgYWRkVm5vZGVzKGVsbSwgbnVsbCwgY2ggYXMgQXJyYXk8Vk5vZGU+LCAwLCAoY2ggYXMgQXJyYXk8Vk5vZGU+KS5sZW5ndGggLSAxLCBpbnNlcnRlZFZub2RlUXVldWUpO1xuICAgICAgfSBlbHNlIGlmIChpc0RlZihvbGRDaCkpIHtcbiAgICAgICAgcmVtb3ZlVm5vZGVzKGVsbSwgb2xkQ2ggYXMgQXJyYXk8Vk5vZGU+LCAwLCAob2xkQ2ggYXMgQXJyYXk8Vk5vZGU+KS5sZW5ndGggLSAxKTtcbiAgICAgIH0gZWxzZSBpZiAoaXNEZWYob2xkVm5vZGUudGV4dCkpIHtcbiAgICAgICAgYXBpLnNldFRleHRDb250ZW50KGVsbSwgJycpO1xuICAgICAgfVxuICAgIH0gZWxzZSBpZiAob2xkVm5vZGUudGV4dCAhPT0gdm5vZGUudGV4dCkge1xuICAgICAgYXBpLnNldFRleHRDb250ZW50KGVsbSwgdm5vZGUudGV4dCBhcyBzdHJpbmcpO1xuICAgIH1cbiAgICBpZiAoaXNEZWYoaG9vaykgJiYgaXNEZWYoaSA9IGhvb2sucG9zdHBhdGNoKSkge1xuICAgICAgaShvbGRWbm9kZSwgdm5vZGUpO1xuICAgIH1cbiAgfVxuXG4gIHJldHVybiBmdW5jdGlvbiBwYXRjaChvbGRWbm9kZTogVk5vZGUgfCBFbGVtZW50LCB2bm9kZTogVk5vZGUpOiBWTm9kZSB7XG4gICAgbGV0IGk6IG51bWJlciwgZWxtOiBOb2RlLCBwYXJlbnQ6IE5vZGU7XG4gICAgY29uc3QgaW5zZXJ0ZWRWbm9kZVF1ZXVlOiBWTm9kZVF1ZXVlID0gW107XG4gICAgZm9yIChpID0gMDsgaSA8IGNicy5wcmUubGVuZ3RoOyArK2kpIGNicy5wcmVbaV0oKTtcblxuICAgIGlmICghaXNWbm9kZShvbGRWbm9kZSkpIHtcbiAgICAgIG9sZFZub2RlID0gZW1wdHlOb2RlQXQob2xkVm5vZGUpO1xuICAgIH1cblxuICAgIGlmIChzYW1lVm5vZGUob2xkVm5vZGUsIHZub2RlKSkge1xuICAgICAgcGF0Y2hWbm9kZShvbGRWbm9kZSwgdm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgfSBlbHNlIHtcbiAgICAgIGVsbSA9IG9sZFZub2RlLmVsbSBhcyBOb2RlO1xuICAgICAgcGFyZW50ID0gYXBpLnBhcmVudE5vZGUoZWxtKTtcblxuICAgICAgY3JlYXRlRWxtKHZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpO1xuXG4gICAgICBpZiAocGFyZW50ICE9PSBudWxsKSB7XG4gICAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50LCB2bm9kZS5lbG0gYXMgTm9kZSwgYXBpLm5leHRTaWJsaW5nKGVsbSkpO1xuICAgICAgICByZW1vdmVWbm9kZXMocGFyZW50LCBbb2xkVm5vZGVdLCAwLCAwKTtcbiAgICAgIH1cbiAgICB9XG5cbiAgICBmb3IgKGkgPSAwOyBpIDwgaW5zZXJ0ZWRWbm9kZVF1ZXVlLmxlbmd0aDsgKytpKSB7XG4gICAgICAoKChpbnNlcnRlZFZub2RlUXVldWVbaV0uZGF0YSBhcyBWTm9kZURhdGEpLmhvb2sgYXMgSG9va3MpLmluc2VydCBhcyBhbnkpKGluc2VydGVkVm5vZGVRdWV1ZVtpXSk7XG4gICAgfVxuICAgIGZvciAoaSA9IDA7IGkgPCBjYnMucG9zdC5sZW5ndGg7ICsraSkgY2JzLnBvc3RbaV0oKTtcbiAgICByZXR1cm4gdm5vZGU7XG4gIH07XG59XG4iLCJpbXBvcnQge1ZOb2RlLCBWTm9kZURhdGF9IGZyb20gJy4vdm5vZGUnO1xuaW1wb3J0IHtofSBmcm9tICcuL2gnO1xuXG5leHBvcnQgaW50ZXJmYWNlIFRodW5rRGF0YSBleHRlbmRzIFZOb2RlRGF0YSB7XG4gIGZuOiAoKSA9PiBWTm9kZTtcbiAgYXJnczogQXJyYXk8YW55Pjtcbn1cblxuZXhwb3J0IGludGVyZmFjZSBUaHVuayBleHRlbmRzIFZOb2RlIHtcbiAgZGF0YTogVGh1bmtEYXRhO1xufVxuXG5leHBvcnQgaW50ZXJmYWNlIFRodW5rRm4ge1xuICAoc2VsOiBzdHJpbmcsIGZuOiBGdW5jdGlvbiwgYXJnczogQXJyYXk8YW55Pik6IFRodW5rO1xuICAoc2VsOiBzdHJpbmcsIGtleTogYW55LCBmbjogRnVuY3Rpb24sIGFyZ3M6IEFycmF5PGFueT4pOiBUaHVuaztcbn1cblxuZnVuY3Rpb24gY29weVRvVGh1bmsodm5vZGU6IFZOb2RlLCB0aHVuazogVk5vZGUpOiB2b2lkIHtcbiAgdGh1bmsuZWxtID0gdm5vZGUuZWxtO1xuICAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLmZuID0gKHRodW5rLmRhdGEgYXMgVk5vZGVEYXRhKS5mbjtcbiAgKHZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5hcmdzID0gKHRodW5rLmRhdGEgYXMgVk5vZGVEYXRhKS5hcmdzO1xuICB0aHVuay5kYXRhID0gdm5vZGUuZGF0YTtcbiAgdGh1bmsuY2hpbGRyZW4gPSB2bm9kZS5jaGlsZHJlbjtcbiAgdGh1bmsudGV4dCA9IHZub2RlLnRleHQ7XG4gIHRodW5rLmVsbSA9IHZub2RlLmVsbTtcbn1cblxuZnVuY3Rpb24gaW5pdCh0aHVuazogVk5vZGUpOiB2b2lkIHtcbiAgY29uc3QgY3VyID0gdGh1bmsuZGF0YSBhcyBWTm9kZURhdGE7XG4gIGNvbnN0IHZub2RlID0gKGN1ci5mbiBhcyBhbnkpLmFwcGx5KHVuZGVmaW5lZCwgY3VyLmFyZ3MpO1xuICBjb3B5VG9UaHVuayh2bm9kZSwgdGh1bmspO1xufVxuXG5mdW5jdGlvbiBwcmVwYXRjaChvbGRWbm9kZTogVk5vZGUsIHRodW5rOiBWTm9kZSk6IHZvaWQge1xuICBsZXQgaTogbnVtYmVyLCBvbGQgPSBvbGRWbm9kZS5kYXRhIGFzIFZOb2RlRGF0YSwgY3VyID0gdGh1bmsuZGF0YSBhcyBWTm9kZURhdGE7XG4gIGNvbnN0IG9sZEFyZ3MgPSBvbGQuYXJncywgYXJncyA9IGN1ci5hcmdzO1xuICBpZiAob2xkLmZuICE9PSBjdXIuZm4gfHwgKG9sZEFyZ3MgYXMgYW55KS5sZW5ndGggIT09IChhcmdzIGFzIGFueSkubGVuZ3RoKSB7XG4gICAgY29weVRvVGh1bmsoKGN1ci5mbiBhcyBhbnkpLmFwcGx5KHVuZGVmaW5lZCwgYXJncyksIHRodW5rKTtcbiAgfVxuICBmb3IgKGkgPSAwOyBpIDwgKGFyZ3MgYXMgYW55KS5sZW5ndGg7ICsraSkge1xuICAgIGlmICgob2xkQXJncyBhcyBhbnkpW2ldICE9PSAoYXJncyBhcyBhbnkpW2ldKSB7XG4gICAgICBjb3B5VG9UaHVuaygoY3VyLmZuIGFzIGFueSkuYXBwbHkodW5kZWZpbmVkLCBhcmdzKSwgdGh1bmspO1xuICAgICAgcmV0dXJuO1xuICAgIH1cbiAgfVxuICBjb3B5VG9UaHVuayhvbGRWbm9kZSwgdGh1bmspO1xufVxuXG5leHBvcnQgY29uc3QgdGh1bmsgPSBmdW5jdGlvbiB0aHVuayhzZWw6IHN0cmluZywga2V5PzogYW55LCBmbj86IGFueSwgYXJncz86IGFueSk6IFZOb2RlIHtcbiAgaWYgKGFyZ3MgPT09IHVuZGVmaW5lZCkge1xuICAgIGFyZ3MgPSBmbjtcbiAgICBmbiA9IGtleTtcbiAgICBrZXkgPSB1bmRlZmluZWQ7XG4gIH1cbiAgcmV0dXJuIGgoc2VsLCB7XG4gICAga2V5OiBrZXksXG4gICAgaG9vazoge2luaXQ6IGluaXQsIHByZXBhdGNoOiBwcmVwYXRjaH0sXG4gICAgZm46IGZuLFxuICAgIGFyZ3M6IGFyZ3NcbiAgfSk7XG59IGFzIFRodW5rRm47XG5cbmV4cG9ydCBkZWZhdWx0IHRodW5rOyIsImltcG9ydCB7SG9va3N9IGZyb20gJy4vaG9va3MnO1xuXG5leHBvcnQgdHlwZSBLZXkgPSBzdHJpbmcgfCBudW1iZXI7XG5cbmV4cG9ydCBpbnRlcmZhY2UgVk5vZGUge1xuICBzZWw6IHN0cmluZyB8IHVuZGVmaW5lZDtcbiAgZGF0YTogVk5vZGVEYXRhIHwgdW5kZWZpbmVkO1xuICBjaGlsZHJlbjogQXJyYXk8Vk5vZGUgfCBzdHJpbmc+IHwgdW5kZWZpbmVkO1xuICBlbG06IE5vZGUgfCB1bmRlZmluZWQ7XG4gIHRleHQ6IHN0cmluZyB8IHVuZGVmaW5lZDtcbiAga2V5OiBLZXk7XG59XG5cbmV4cG9ydCBpbnRlcmZhY2UgVk5vZGVEYXRhIHtcbiAgLy8gbW9kdWxlcyAtIHVzZSBhbnkgYmVjYXVzZSBPYmplY3QgdHlwZSBpcyB1c2VsZXNzXG4gIHByb3BzPzogYW55O1xuICBhdHRycz86IGFueTtcbiAgY2xhc3M/OiBhbnk7XG4gIHN0eWxlPzogYW55O1xuICBkYXRhc2V0PzogYW55O1xuICBvbj86IGFueTtcbiAgaGVybz86IGFueTtcbiAgYXR0YWNoRGF0YT86IGFueTtcbiAgaG9vaz86IEhvb2tzO1xuICBrZXk/OiBLZXk7XG4gIG5zPzogc3RyaW5nOyAvLyBmb3IgU1ZHc1xuICBmbj86ICgpID0+IFZOb2RlOyAvLyBmb3IgdGh1bmtzXG4gIGFyZ3M/OiBBcnJheTxhbnk+OyAvLyBmb3IgdGh1bmtzXG4gIFtrZXk6IHN0cmluZ106IGFueTsgLy8gZm9yIGFueSBvdGhlciAzcmQgcGFydHkgbW9kdWxlXG4gIC8vIGVuZCBvZiBtb2R1bGVzXG59XG5cbmV4cG9ydCBmdW5jdGlvbiB2bm9kZShzZWw6IHN0cmluZyxcbiAgICAgICAgICAgICAgIGRhdGE6IGFueSB8IHVuZGVmaW5lZCxcbiAgICAgICAgICAgICAgIGNoaWxkcmVuOiBBcnJheTxWTm9kZSB8IHN0cmluZz4gfCB1bmRlZmluZWQsXG4gICAgICAgICAgICAgICB0ZXh0OiBzdHJpbmcgfCB1bmRlZmluZWQsXG4gICAgICAgICAgICAgICBlbG06IEVsZW1lbnQgfCBUZXh0IHwgdW5kZWZpbmVkKTogVk5vZGUge1xuICBsZXQga2V5ID0gZGF0YSA9PT0gdW5kZWZpbmVkID8gdW5kZWZpbmVkIDogZGF0YS5rZXk7XG4gIHJldHVybiB7c2VsOiBzZWwsIGRhdGE6IGRhdGEsIGNoaWxkcmVuOiBjaGlsZHJlbixcbiAgICAgICAgICB0ZXh0OiB0ZXh0LCBlbG06IGVsbSwga2V5OiBrZXl9O1xufVxuXG5leHBvcnQgZGVmYXVsdCB2bm9kZTtcbiIsIlwidXNlIHN0cmljdFwiO1xuXG5pbXBvcnQge2luaXQgYXMgc25hYmJkb21Jbml0fSBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvc25hYmJkb20nO1xuaW1wb3J0IHtjbGFzc01vZHVsZX0gZnJvbSAnLi9saWIvc25hYmJkb20vc3JjL21vZHVsZXMvY2xhc3MnO1xuaW1wb3J0IHtwcm9wc01vZHVsZX0gZnJvbSAnLi9saWIvc25hYmJkb20vc3JjL21vZHVsZXMvcHJvcHMnO1xuaW1wb3J0IHtzdHlsZU1vZHVsZX0gZnJvbSAnLi9saWIvc25hYmJkb20vc3JjL21vZHVsZXMvc3R5bGUnO1xuaW1wb3J0IHtldmVudExpc3RlbmVyc01vZHVsZX0gZnJvbSAnLi9saWIvc25hYmJkb20vc3JjL21vZHVsZXMvZXZlbnRsaXN0ZW5lcnMnO1xuaW1wb3J0IHtWTm9kZX0gZnJvbSBcIi4vbGliL3NuYWJiZG9tL3NyYy92bm9kZVwiO1xuXG5pbXBvcnQgY291bnRlckxpc3QgZnJvbSAnLi9jb3VudGVyTGlzdCc7XG5cbi8vIEdldCBhIFNuYWJiZG9tIHBhdGNoIGZ1bmN0aW9uIHdpdGggdGhlIG5vcm1hbCBIVE1MIG1vZHVsZXMuXG5jb25zdCBwYXRjaCA9IHNuYWJiZG9tSW5pdChcbiAgICBbXG4gICAgICAgIGNsYXNzTW9kdWxlLFxuICAgICAgICBwcm9wc01vZHVsZSxcbiAgICAgICAgc3R5bGVNb2R1bGUsXG4gICAgICAgIGV2ZW50TGlzdGVuZXJzTW9kdWxlXG4gICAgXVxuKTtcblxuLyoqXG4gKiBSdW5zIG9uZSBjeWNsZSBvZiB0aGUgU25hYmJkb20gZXZlbnQgbG9vcC5cbiAqIEBwYXJhbSBzdGF0ZSB0aGUgbGF0ZXN0IG1vZGVsIHN0YXRlLlxuICogQHBhcmFtIG9sZFZub2RlIHRoZSBwcmlvciB2aXJ0dWFsIERPTSBvciB0aGUgcmVhbCBET00gZmlyc3QgdGltZSB0aHJvdWdoLlxuICogQHBhcmFtIHZpZXcgdGhlIGZ1bmN0aW9uIHRvIGNvbXB1dGUgdGhlIG5ldyB2aXJ0dWFsIERPTSBmcm9tIHRoZSBtb2RlbC5cbiAqIEBwYXJhbSB1cGRhdGUgdGhlIGZ1bmN0aW9uIHRvIGNvbXB1dGUgdGhlIG5ldyBtb2RlbCBzdGF0ZSBmcm9tIHRoZSBvbGQgc3RhdGUgYW5kIGEgcGVuZGluZyBhY3Rpb24uXG4gKi9cbmZ1bmN0aW9uIG1haW4oIHN0YXRlLCBvbGRWbm9kZTogVk5vZGUgfCBFbGVtZW50LCB7IHZpZXcsIHVwZGF0ZSB9ICkge1xuXG4gICAgbGV0IGV2ZW50SGFuZGxlciA9IGFjdGlvbiA9PiB7XG4gICAgICAgIGNvbnN0IG5ld1N0YXRlID0gdXBkYXRlKCBzdGF0ZSwgYWN0aW9uICk7XG4gICAgICAgIG1haW4oIG5ld1N0YXRlLCBuZXdWbm9kZSwgeyB2aWV3LCB1cGRhdGUgfSApO1xuICAgIH07XG5cbiAgICBjb25zdCBuZXdWbm9kZSA9IHZpZXcoIHN0YXRlLCBldmVudEhhbmRsZXIgKTtcblxuICAgIHBhdGNoKCBvbGRWbm9kZSwgbmV3Vm5vZGUgKTtcblxufVxuXG4vKipcbiAqIEluaXRpYWxpemVzIHRoZSBhcHBsaWNhdGlvbiBzdGF0ZS5cbiAqIEByZXR1cm5zIHt7bmV4dElEOiBudW1iZXIsIGNvdW50ZXJzOiBBcnJheX19XG4gKi9cbmZ1bmN0aW9uIGluaXRTdGF0ZSgpIHtcbiAgICByZXR1cm4geyBuZXh0SUQ6IDUsIGNvdW50ZXJzOiBbXSB9O1xufVxuXG4vLyAtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS1cblxuLy8gRmluZCB0aGUgRE9NIG5vZGUgdG8gZHJvcCBvdXIgYXBwIGluLlxubGV0IGRvbU5vZGUgPSBkb2N1bWVudC5nZXRFbGVtZW50QnlJZCggJ2FwcCcgKTtcblxuaWYgKCBkb21Ob2RlID09IG51bGwgKSB7XG4gICAgLy8gQWJhbmRvbiBob3BlIGlmIHRoZSByZWFsIERPTSBub2RlIGlzIG5vdCBmb3VuZC5cbiAgICBjb25zb2xlLmxvZyggXCJDYW5ub3QgZmluZCBhcHBsaWNhdGlvbiBET00gbm9kZS5cIiApO1xufVxuZWxzZSB7XG4gICAgLy8gRmlyZSBvZmYgdGhlIGZpcnN0IGxvb3Agb2YgdGhlIGxpZmVjeWNsZS5cbiAgICBtYWluKCBpbml0U3RhdGUoKSwgZG9tTm9kZSwgY291bnRlckxpc3QgKTtcbn1cbiJdfQ==
