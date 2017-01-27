(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
"use strict";
const h_1 = require("./lib/snabbdom/src/h");
exports.actionInitialize = { kind: 'initialize' };
// model : Number
function view(count, handler) {
    return h_1.default('div', [
        h_1.default('button', {
            on: { click: handler.bind(null, { kind: 'increment' }) }
        }, '+'),
        h_1.default('button', {
            on: { click: handler.bind(null, { kind: 'decrement' }) }
        }, '-'),
        h_1.default('div', `Count : ${count}`),
    ]);
}
exports.view = view;
function update(count, action) {
    switch (action.kind) {
        case 'increment':
            return count + 1;
        case 'decrement':
            return count - 1;
        case 'initialize':
            return 0;
    }
}
exports.update = update;

},{"./lib/snabbdom/src/h":3}],2:[function(require,module,exports){
"use strict";
const h_1 = require("./lib/snabbdom/src/h");
const counter_1 = require("./counter");
// VIEW
/**
 * Generates the mark up for the list of counters.
 * @param model the state of the counters.
 * @param handler event handling.
 */
function view(model, handler) {
    return h_1.default('div', [
        h_1.default('button', {
            on: { click: handler.bind(null, { kind: 'add' }) }
        }, 'Add'),
        h_1.default('button', {
            on: { click: handler.bind(null, { kind: 'reset' }) }
        }, 'Reset'),
        h_1.default('hr'),
        h_1.default('div.counter-list', model.counters.map(item => counterItemView(item, handler)))
    ]);
}
exports.view = view;
/**
 * Generates the mark up for one counter plus a remove button.
 * @param item one entry in the counters array.
 * @param handler the master event handler
 */
function counterItemView(item, handler) {
    return h_1.default('div.counter-item', { key: item.id }, [
        h_1.default('button.remove', {
            on: { click: handler.bind(null, { kind: 'remove', id: item.id }) }
        }, 'Remove'),
        counter_1.view(item.counter, a => handler({ kind: 'update', id: item.id, counterAction: a })),
        h_1.default('hr')
    ]);
}
// UPDATE
function update(model, action) {
    switch (action.kind) {
        case 'add':
            return addCounter(model);
        case 'reset':
            return resetCounters(model);
        case 'remove':
            const removeAction = action;
            return removeCounter(model, removeAction.id);
        case 'update':
            const updateAction = action;
            return updateCounterRow(model, updateAction.id, updateAction.counterAction);
    }
}
exports.update = update;
function addCounter(model) {
    const newCounter = { id: model.nextID, counter: counter_1.update(0, counter_1.actionInitialize) };
    return {
        counters: model.counters.concat(newCounter),
        nextID: model.nextID + 1
    };
}
function resetCounters(model) {
    return {
        nextID: model.nextID,
        counters: model.counters.map((item) => ({
            id: item.id,
            counter: counter_1.update(item.counter, counter_1.actionInitialize)
        }))
    };
}
function removeCounter(model, id) {
    return {
        nextID: model.nextID,
        counters: model.counters.filter(item => item.id !== id)
    };
}
function updateCounterRow(model, id, action) {
    return {
        nextID: model.nextID,
        counters: model.counters.map(item => item.id !== id ?
            item
            : {
                id: item.id,
                counter: counter_1.update(item.counter, action)
            })
    };
}

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
function main(state, oldVnode, view, update) {
    let eventHandler = (action) => {
        const newState = update(state, action);
        main(newState, newVnode, view, update);
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
    main(initState(), domNode, counterList_1.view, counterList_1.update);
}

},{"./counterList":2,"./lib/snabbdom/src/modules/class":6,"./lib/snabbdom/src/modules/eventlisteners":7,"./lib/snabbdom/src/modules/props":8,"./lib/snabbdom/src/modules/style":9,"./lib/snabbdom/src/snabbdom":10}]},{},[13])
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIm5vZGVfbW9kdWxlcy9icm93c2VyLXBhY2svX3ByZWx1ZGUuanMiLCJ3ZWJhcHAvc2NyaXB0cy9jb3VudGVyLnRzIiwid2ViYXBwL3NjcmlwdHMvY291bnRlckxpc3QudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL2gudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL2h0bWxkb21hcGkudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL2lzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL2NsYXNzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL2V2ZW50bGlzdGVuZXJzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL3Byb3BzLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9tb2R1bGVzL3N0eWxlLnRzIiwid2ViYXBwL3NjcmlwdHMvbGliL3NuYWJiZG9tL3NyYy9zbmFiYmRvbS50cyIsIndlYmFwcC9zY3JpcHRzL2xpYi9zbmFiYmRvbS9zcmMvdGh1bmsudHMiLCJ3ZWJhcHAvc2NyaXB0cy9saWIvc25hYmJkb20vc3JjL3Zub2RlLnRzIiwid2ViYXBwL3NjcmlwdHMvbWFpbi50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQ0FBLFlBQVksQ0FBQztBQUdiLDRDQUFxQztBQVF4QixRQUFBLGdCQUFnQixHQUF1QixFQUFFLElBQUksRUFBRSxZQUFZLEVBQUUsQ0FBQztBQUszRSxpQkFBaUI7QUFDakIsY0FBc0IsS0FBYyxFQUFFLE9BQXVCO0lBQ3pELE1BQU0sQ0FBQyxXQUFDLENBQ0osS0FBSyxFQUFFO1FBQ0gsV0FBQyxDQUNHLFFBQVEsRUFBRTtZQUNOLEVBQUUsRUFBRSxFQUFFLEtBQUssRUFBRSxPQUFPLENBQUMsSUFBSSxDQUFFLElBQUksRUFBRSxFQUFFLElB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QUFFLENBQU8sRUFBRSxDQUFPO0lBQzFDLElBQUksSUFBSSxHQUFjLEVBQUUsRUFBRSxRQUFhLEVBQUUsSUFBUyxFQUFFLENBQVMsQ0FBQztJQUM5RCxFQUFFLENBQUMsQ0FBQyxDQUFDLEtBQUssU0FBUyxDQUFDLENBQUMsQ0FBQztRQUNwQixJQUFJLEdBQUcsQ0FBQyxDQUFDO1FBQ1QsRUFBRSxDQUFDLENBQUMsRUFBRSxDQUFDLEtBQUssQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUM7WUFBQyxRQUFRLEdBQUcsQ0FBQyxDQUFDO1FBQUMsQ0FBQztRQUNsQyxJQUFJLENBQUMsRUFBRSxDQUFDLENBQUMsRUFBRSxDQUFDLFNBQVMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUM7WUFBQyxJQUFJLEdBQUcsQ0FBQyxDQUFDO1FBQUMsQ0FBQztRQUN2QyxJQUFJLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxJQUFJLENBQUMsQ0FBQyxHQUFHLENBQUMsQ0FBQyxDQUFDO1lBQUMsUUFBUSxHQUFHLENBQUMsQ0FBQyxDQUFDLENBQUM7UUFBQyxDQUFDO0lBQzFDLENBQUM7SUFBQyxJQUFJLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxLQUFLLFNBQVMsQ0FBQyxDQUFDLENBQUM7UUFDM0IsRUFBRSxDQUFDLENBQUMsRUFBRSxDQUFDLEtBQUssQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUM7WUFBQyxRQUFRLEdBQUcsQ0FBQyxDQUFDO1FBQUMsQ0FBQztRQUNsQyxJQUFJLENBQUMsRUFBRSxDQUFDLENBQUMsRUFBRSxDQUFDLFNBQVMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUM7WUFBQyxJQUFJLEdBQUcsQ0FBQyxDQUFDO1FBQUMsQ0FBQztRQUN2QyxJQUFJLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxJQUFJLENBQUMsQ0FBQyxHQUFHLENBQUMsQ0FBQyxDQUFDO1lBQUMsUUFBUSxHQUFHLENBQUMsQ0FBQyxDQUFDLENBQUM7UUFBQyxDQUFDO1FBQ3hDLElBQUksQ0FBQyxD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R0FBRyxHQUFHLENBQUMsSUFBSSxFQUFFLElBQUksR0FBRyxHQUFHLENBQUMsSUFBSSxDQUFDO0lBQzFDLEVBQUUsQ0FBQyxDQUFDLEdBQUcsQ0FBQyxFQUFFLEtBQUssR0FBRyxDQUFDLEVBQUUsSUFBSyxPQUFlLENBQUMsTUFBTSxLQUFNLElBQVksQ0FBQyxNQUFNLENBQUMsQ0FBQyxDQUFDO1FBQzFFLFdBQVcsQ0FBRSxHQUFHLENBQUMsRUFBVSxDQUFDLEtBQUssQ0FBQyxTQUFTLEVBQUUsSUFBSSxDQUFDLEVBQUUsS0FBSyxDQUFDLENBQUM7SUFDN0QsQ0FBQztJQUNELEdBQUcsQ0FBQyxDQUFDLENBQUMsR0FBRyxDQUFDLEVBQUUsQ0FBQyxHQUFJLElBQVksQ0FBQyxNQUFNLEVBQUUsRUFBRSxDQUFDLEVBQUUsQ0FBQztRQUMxQyxFQUFFLENBQUMsQ0FBRSxPQUFlLENBQUMsQ0FBQyxDQUFDLEtBQU0sSUFBWSxDQUFDLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQztZQUM3QyxXQUFXLENBQUUsR0FBRyxDQUFDLEVBQVUsQ0FBQyxLQUFLLENBQUMsU0FBUyxFQUFFLElBQUksQ0FBQyxFQUFFLEtBQUssQ0FBQyxDQUFDO1lBQzNELE1BQU0sQ0FBQztRQUNULENBQUM7SUFDSCxDQUFDO0lBQ0QsV0FBVyxDQUFDLFFBQVEsRUFBRSxLQUFLLENBQUMsQ0FBQztBQUMvQixDQUFDO0FBRVksUUFBQSxLQUFLLEdBQUcsZUFBZSxHQUFXLEVBQUUsR0FBUyxFQUFFLEVBQVEsRUFBRSxJQUFVO0lBQzlFLEVBQUUsQ0FBQyxDQUFDLElBQUksS0FBSyxTQUFTLENBQUMsQ0FBQyxDQUFDO1FBQ3ZCLElBQUksR0FBRyxFQUFFLENBQUM7UUFDVixFQUFFLEdBQUcsR0FBRyxDQUFDO1FBQ1QsR0FBRyxHQUFHLFNBQVMsQ0FBQztJQUNsQixDQUFDO0lBQ0QsTUFBTSxDQUFDLEtBQUMsQ0FBQyxHQUFHLEVBQUU7UUFDWixHQUFHLEVBQUUsR0FBRztRQUNSLElBQUksRUFBRSxFQUFDLElBQUksRUFBRSxJQUFJLEVBQUUsUUFBUSxFQUFFLFFBQVEsRUFBQztRQUN0QyxFQUFFLEVBQUUsRUFBRTtRQUNOLElBQUksRUFBRSxJQUFJO0tBQ1gsQ0FBQyxDQUFDO0FBQ0wsQ0FBWSxDQUFDOztBQUViLGtCQUFlLGFBQUssQ0FBQzs7OztBQzlCckIsZUFBc0IsR0FBVyxFQUNsQixJQUFxQixFQUNyQixRQUEyQyxFQUMzQyxJQUF3QixFQUN4QixHQUErQjtJQUM1QyxJQUFJLEdBQUcsR0FBRyxJQUFJLEtBQUssU0FBUyxHQUFHLFNBQVMsR0FBRyxJQUFJLENBQUMsR0FBRyxDQUFDO0lBQ3BELE1BQU0sQ0FBQyxFQUFDLEdBQUcsRUFBRSxHQUFHLEVBQUUsSUFBSSxFQUFFLElBQUksRUFBRSxRQUFRLEVBQUUsUUFBUTtRQUN4QyxJQUFJLEVBQUUsSUFBSSxFQUFFLEdBQUcsRUFBRSxHQUFHLEVBQUUsR0FBRyxFQUFFLEdBQUcsRUFBQyxDQUFDO0FBQzFDLENBQUM7QUFSRCxzQkFRQzs7QUFFRCxrQkFBZSxLQUFLLENBQUM7OztBQzFDckIsWUFBWSxDQUFDO0FBRWIsMERBQWlFO0FBQ2pFLDREQUE2RDtBQUM3RCw0REFBNkQ7QUFDN0QsNERBQTZEO0FBQzdELDhFQUErRTtBQUcvRSwrQ0FBc0w7QUFFdEwsOERBQThEO0FBQzlELE1BQU0sS0FBSyxHQUFHLGVBQVksQ0FDdEI7SUFDSSxtQkFBVztJQUNYLG1CQUFXO0lBQ1gsbUJBQVc7SUFDWCxxQ0FBb0I7Q0FDdkIsQ0FDSixDQUFDO0FBT0Y7Ozs7OztHQU1HO0FBQ0gsY0FBZSxLQUF1QixFQUFFLFFBQTBCLEVBQUUsSUFBbUIsRUFBRSxNQUF1QjtJQUU1RyxJQUFJLFlBQVksR0FBRyxDQUFFLE1BQTBCO1FBQzNDLE1BQU0sUUFBUSxHQUFHLE1BQU0sQ0FBRSxLQUFLLEVBQUUsTUFBTSxDQUFFLENBQUM7UUFDekMsSUFBSSxDQUFFLFFBQVEsRUFBRSxRQUFRLEVBQUUsSUFBSSxFQUFFLE1BQU0sQ0FBRSxDQUFDO0lBQzdDLENBQUMsQ0FBQztJQUVGLE1BQU0sUUFBUSxHQUFHLElBQUksQ0FBRSxLQUFLLEVBQUUsWUFBWSxDQUFFLENBQUM7SUFFN0MsS0FBSyxDQUFFLFFBQVEsRUFBRSxRQUFRLENBQUUsQ0FBQztBQUVoQyxDQUFDO0FBRUQ7OztHQUdHO0FBQ0g7SUFDSSxNQUFNLENBQUMsRUFBRSxNQUFNLEVBQUUsQ0FBQyxFQUFFLFFBQVEsRUFBRSxFQUFFLEVBQUUsQ0FBQztBQUN2QyxDQUFDO0FBRUQsOEVBQThFO0FBRTlFLHdDQUF3QztBQUN4QyxJQUFJLE9BQU8sR0FBRyxRQUFRLENBQUMsY0FBYyxDQUFFLEtBQUssQ0FBRSxDQUFDO0FBRS9DLEVBQUUsQ0FBQyxDQUFFLE9BQU8sSUFBSSxJQUFLLENBQUMsQ0FBQyxDQUFDO0lBQ3BCLGtEQUFrRDtJQUNsRCxPQUFPLENBQUMsR0FBRyxDQUFFLG1DQUFtQyxDQUFFLENBQUM7QUFDdkQsQ0FBQztBQUNELElBQUksQ0FBQyxDQUFDO0lBQ0YsNENBQTRDO0lBQzVDLElBQUksQ0FBRSxTQUFTLEVBQUUsRUFBRSxPQUFPLEVBQUUsa0JBQWUsRUFBRSxvQkFBaUIsQ0FBRSxDQUFDO0FBQ3JFLENBQUMiLCJmaWxlIjoiZ2VuZXJhdGVkLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXNDb250ZW50IjpbIihmdW5jdGlvbiBlKHQsbixyKXtmdW5jdGlvbiBzKG8sdSl7aWYoIW5bb10pe2lmKCF0W29dKXt2YXIgYT10eXBlb2YgcmVxdWlyZT09XCJmdW5jdGlvblwiJiZyZXF1aXJlO2lmKCF1JiZhKXJldHVybiBhKG8sITApO2lmKGkpcmV0dXJuIGkobywhMCk7dmFyIGY9bmV3IEVycm9yKFwiQ2Fubm90IGZpbmQgbW9kdWxlICdcIitvK1wiJ1wiKTt0aHJvdyBmLmNvZGU9XCJNT0RVTEVfTk9UX0ZPVU5EXCIsZn12YXIgbD1uW29dPXtleHBvcnRzOnt9fTt0W29dWzBdLmNhbGwobC5leHBvcnRzLGZ1bmN0aW9uKGUpe3ZhciBuPXRbb11bMV1bZV07cmV0dXJuIHMobj9uOmUpfSxsLGwuZXhwb3J0cyxlLHQsbixyKX1yZXR1cm4gbltvXS5leHBvcnRzfXZhciBpPXR5cGVvZiByZXF1aXJlPT1cImZ1bmN0aW9uXCImJnJlcXVpcmU7Zm9yKHZhciBvPTA7bzxyLmxlbmd0aDtvKyspcyhyW29dKTtyZXR1cm4gc30pIiwiXCJ1c2Ugc3RyaWN0XCI7XG5cbmltcG9ydCB7Vk5vZGV9IGZyb20gJy4vbGliL3NuYWJiZG9tL3NyYy92bm9kZSc7XG5pbXBvcnQgaCBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvaCc7XG5cbmV4cG9ydCBpbnRlcmZhY2UgQWN0aW9uX0luY3JlbWVudCB7IGtpbmQgOiAnaW5jcmVtZW50JyB9XG5leHBvcnQgaW50ZXJmYWNlIEFjdGlvbl9EZWNyZW1lbnQgeyBraW5kIDogJ2RlY3JlbWVudCcgfVxuZXhwb3J0IGludGVyZmFjZSBBY3Rpb25fSW5pdGlhbGl6ZSB7IGtpbmQgOiAnaW5pdGlhbGl6ZScgfVxuXG5leHBvcnQgdHlwZSBBY3Rpb24gPSBBY3Rpb25fSW5jcmVtZW50IHwgQWN0aW9uX0RlY3JlbWVudCB8IEFjdGlvbl9Jbml0aWFsaXplO1xuXG5leHBvcnQgY29uc3QgYWN0aW9uSW5pdGlhbGl6ZSA6IEFjdGlvbl9Jbml0aWFsaXplID0geyBraW5kOiAnaW5pdGlhbGl6ZScgfTtcblxuZXhwb3J0IHR5cGUgQWN0aW9uSGFuZGxlciA9IChhY3Rpb246QWN0aW9uKSA9PiB2b2lkO1xuXG5cbi8vIG1vZGVsIDogTnVtYmVyXG5leHBvcnQgZnVuY3Rpb24gdmlldyggY291bnQgOiBudW1iZXIsIGhhbmRsZXIgOiBBY3Rpb25IYW5kbGVyICkgOiBWTm9kZSB7XG4gICAgcmV0dXJuIGgoXG4gICAgICAgICdkaXYnLCBbXG4gICAgICAgICAgICBoKFxuICAgICAgICAgICAgICAgICdidXR0b24nLCB7XG4gICAgICAgICAgICAgICAgICAgIG9uOiB7IGNsaWNrOiBoYW5kbGVyLmJpbmQoIG51bGwsIHsga2luZDogJ2luY3JlbWVudCcgfSApIH1cbiAgICAgICAgICAgICAgICB9LCAnKydcbiAgICAgICAgICAgICksXG4gICAgICAgICAgICBoKFxuICAgICAgICAgICAgICAgICdidXR0b24nLCB7XG4gICAgICAgICAgICAgICAgICAgIG9uOiB7IGNsaWNrOiBoYW5kbGVyLmJpbmQoIG51bGwsIHsga2luZDogJ2RlY3JlbWVudCcgfSApIH1cbiAgICAgICAgICAgICAgICB9LCAnLSdcbiAgICAgICAgICAgICksXG4gICAgICAgICAgICBoKCAnZGl2JywgYENvdW50IDogJHtjb3VudH1gICksXG4gICAgICAgIF1cbiAgICApO1xufVxuXG5cbmV4cG9ydCBmdW5jdGlvbiB1cGRhdGUoIGNvdW50IDogbnVtYmVyLCBhY3Rpb24gOiBBY3Rpb24gKSA6IG51bWJlciB7XG4gICAgc3dpdGNoICggYWN0aW9uLmtpbmQgKSB7XG4gICAgICAgIGNhc2UgJ2luY3JlbWVudCc6XG4gICAgICAgICAgICByZXR1cm4gY291bnQgKyAxO1xuICAgICAgICBjYXNlICdkZWNyZW1lbnQnOlxuICAgICAgICAgICAgcmV0dXJuIGNvdW50IC0gMTtcbiAgICAgICAgY2FzZSAnaW5pdGlhbGl6ZSc6XG4gICAgICAgICAgICByZXR1cm4gMDtcbiAgICB9XG59XG5cbiIsIlwidXNlIHN0cmljdFwiO1xuXG5pbXBvcnQgaCBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvaCc7XG5pbXBvcnQge1ZOb2RlfSBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvdm5vZGUnO1xuaW1wb3J0IHtcbiAgICB2aWV3IGFzIHZpZXdDb3VudGVyLFxuICAgIHVwZGF0ZSBhcyB1cGRhdGVDb3VudGVyLFxuICAgIGFjdGlvbkluaXRpYWxpemUgYXMgYWN0aW9uSW5pdGlhbGl6ZUNvdW50ZXIsXG4gICAgQWN0aW9uIGFzIENvdW50ZXJBY3Rpb25cbn0gZnJvbSAnLi9jb3VudGVyJztcblxuLy8gQUNUSU9OU1xuXG5leHBvcnQgaW50ZXJmYWNlIEFjdGlvbl9BZGQgeyBraW5kIDogJ2FkZCcgfVxuZXhwb3J0IGludGVyZmFjZSBBY3Rpb25fVXBkYXRlIHsga2luZCA6ICd1cGRhdGUnLCBpZCA6IG51bWJlciwgY291bnRlckFjdGlvbiA6IENvdW50ZXJBY3Rpb24gfVxuZXhwb3J0IGludGVyZmFjZSBBY3Rpb25fUmVtb3ZlIHsga2luZCA6ICdyZW1vdmUnLCBpZCA6IG51bWJlciB9XG5leHBvcnQgaW50ZXJmYWNlIEFjdGlvbl9SZXNldCB7IGtpbmQgOiAncmVzZXQnIH1cblxuZXhwb3J0IHR5cGUgQWN0aW9uID0gQWN0aW9uX0FkZCB8IEFjdGlvbl9VcGRhdGUgfCBBY3Rpb25fUmVtb3ZlIHwgQWN0aW9uX1Jlc2V0O1xuXG5leHBvcnQgdHlwZSBBY3Rpb25IYW5kbGVyID0gKGFjdGlvbjpBY3Rpb24pID0+IHZvaWQ7XG5cblxuLy8gTU9ERUxcblxuZXhwb3J0IGludGVyZmFjZSBDb3VudGVySXRlbSB7XG4gICAgaWQgOiBudW1iZXIsXG4gICAgY291bnRlciA6IG51bWJlclxufVxuXG5leHBvcnQgaW50ZXJmYWNlIE1vZGVsIHtcbiAgICBuZXh0SUQgOiBudW1iZXIsXG4gICAgY291bnRlcnMgOiBDb3VudGVySXRlbVtdXG59XG5cblxuLy8gVklFV1xuXG4vKipcbiAqIEdlbmVyYXRlcyB0aGUgbWFyayB1cCBmb3IgdGhlIGxpc3Qgb2YgY291bnRlcnMuXG4gKiBAcGFyYW0gbW9kZWwgdGhlIHN0YXRlIG9mIHRoZSBjb3VudGVycy5cbiAqIEBwYXJhbSBoYW5kbGVyIGV2ZW50IGhhbmRsaW5nLlxuICovXG5leHBvcnQgZnVuY3Rpb24gdmlldyggbW9kZWwgOiBNb2RlbCwgaGFuZGxlciA6IEFjdGlvbkhhbmRsZXIgKSA6IFZOb2RlIHtcbiAgICByZXR1cm4gaChcbiAgICAgICAgJ2RpdicsIFtcbiAgICAgICAgICAgIGgoXG4gICAgICAgICAgICAgICAgJ2J1dHRvbicsIHtcbiAgICAgICAgICAgICAgICAgICAgb246IHsgY2xpY2s6IGhhbmRsZXIuYmluZCggbnVsbCwgeyBraW5kOiAnYWRkJyB9ICkgfVxuICAgICAgICAgICAgICAgIH0sICdBZGQnXG4gICAgICAgICAgICApLFxuICAgICAgICAgICAgaChcbiAgICAgICAgICAgICAgICAnYnV0dG9uJywge1xuICAgICAgICAgICAgICAgICAgICBvbjogeyBjbGljazogaGFuZGxlci5iaW5kKCBudWxsLCB7IGtpbmQ6ICdyZXNldCcgfSApIH1cbiAgICAgICAgICAgICAgICB9LCAnUmVzZXQnXG4gICAgICAgICAgICApLFxuICAgICAgICAgICAgaCggJ2hyJyApLFxuICAgICAgICAgICAgaCggJ2Rpdi5jb3VudGVyLWxpc3QnLCBtb2RlbC5jb3VudGVycy5tYXAoIGl0ZW0gPT4gY291bnRlckl0ZW1WaWV3KCBpdGVtLCBoYW5kbGVyICkgKSApXG5cbiAgICAgICAgXVxuICAgICk7XG59XG5cbi8qKlxuICogR2VuZXJhdGVzIHRoZSBtYXJrIHVwIGZvciBvbmUgY291bnRlciBwbHVzIGEgcmVtb3ZlIGJ1dHRvbi5cbiAqIEBwYXJhbSBpdGVtIG9uZSBlbnRyeSBpbiB0aGUgY291bnRlcnMgYXJyYXkuXG4gKiBAcGFyYW0gaGFuZGxlciB0aGUgbWFzdGVyIGV2ZW50IGhhbmRsZXJcbiAqL1xuZnVuY3Rpb24gY291bnRlckl0ZW1WaWV3KCBpdGVtIDpDb3VudGVySXRlbSwgaGFuZGxlciA6IEFjdGlvbkhhbmRsZXIgKSB7XG4gICAgcmV0dXJuIGgoXG4gICAgICAgICdkaXYuY291bnRlci1pdGVtJywgeyBrZXk6IGl0ZW0uaWQgfSwgW1xuICAgICAgICAgICAgaChcbiAgICAgICAgICAgICAgICAnYnV0dG9uLnJlbW92ZScsIHtcbiAgICAgICAgICAgICAgICAgICAgb246IHsgY2xpY2s6IGhhbmRsZXIuYmluZCggbnVsbCwgeyBraW5kOiAncmVtb3ZlJywgaWQ6IGl0ZW0uaWQgfSApIH1cbiAgICAgICAgICAgICAgICB9LCAnUmVtb3ZlJ1xuICAgICAgICAgICAgKSxcbiAgICAgICAgICAgIHZpZXdDb3VudGVyKCBpdGVtLmNvdW50ZXIsIGEgPT4gaGFuZGxlciggeyBraW5kOiAndXBkYXRlJywgaWQ6IGl0ZW0uaWQsIGNvdW50ZXJBY3Rpb246IGEgfSApICksXG4gICAgICAgICAgICBoKCAnaHInIClcbiAgICAgICAgXVxuICAgICk7XG59XG5cbi8vIFVQREFURVxuXG5leHBvcnQgZnVuY3Rpb24gdXBkYXRlKCBtb2RlbCA6IE1vZGVsLCBhY3Rpb24gOiBBY3Rpb24gKSA6IE1vZGVsIHtcblxuICAgIHN3aXRjaCAoIGFjdGlvbi5raW5kICkge1xuICAgICAgICBjYXNlICdhZGQnOlxuICAgICAgICAgICAgcmV0dXJuIGFkZENvdW50ZXIoIG1vZGVsICk7XG4gICAgICAgIGNhc2UgJ3Jlc2V0JzpcbiAgICAgICAgICAgIHJldHVybiByZXNldENvdW50ZXJzKCBtb2RlbCApO1xuICAgICAgICBjYXNlICdyZW1vdmUnOlxuICAgICAgICAgICAgY29uc3QgcmVtb3ZlQWN0aW9uID0gPEFjdGlvbl9SZW1vdmU+YWN0aW9uO1xuICAgICAgICAgICAgcmV0dXJuIHJlbW92ZUNvdW50ZXIoIG1vZGVsLCByZW1vdmVBY3Rpb24uaWQgKTtcbiAgICAgICAgY2FzZSAndXBkYXRlJzpcbiAgICAgICAgICAgIGNvbnN0IHVwZGF0ZUFjdGlvbiA9IDxBY3Rpb25fVXBkYXRlPmFjdGlvbjtcbiAgICAgICAgICAgIHJldHVybiB1cGRhdGVDb3VudGVyUm93KCBtb2RlbCwgdXBkYXRlQWN0aW9uLmlkLCB1cGRhdGVBY3Rpb24uY291bnRlckFjdGlvbiApO1xuICAgIH1cblxufVxuXG5mdW5jdGlvbiBhZGRDb3VudGVyKCBtb2RlbCA6IE1vZGVsICkgOiBNb2RlbCB7XG4gICAgY29uc3QgbmV3Q291bnRlciA6IENvdW50ZXJJdGVtID0geyBpZDogbW9kZWwubmV4dElELCBjb3VudGVyOiB1cGRhdGVDb3VudGVyKCAwLCBhY3Rpb25Jbml0aWFsaXplQ291bnRlciApIH07XG4gICAgcmV0dXJuIHtcbiAgICAgICAgY291bnRlcnM6IG1vZGVsLmNvdW50ZXJzLmNvbmNhdChuZXdDb3VudGVyKSxcbiAgICAgICAgbmV4dElEOiBtb2RlbC5uZXh0SUQgKyAxXG4gICAgfTtcbn1cblxuXG5mdW5jdGlvbiByZXNldENvdW50ZXJzKCBtb2RlbCA6IE1vZGVsICkgOiBNb2RlbCB7XG4gICAgcmV0dXJuIHtcbiAgICAgICAgbmV4dElEOiBtb2RlbC5uZXh0SUQsXG4gICAgICAgIGNvdW50ZXJzOiBtb2RlbC5jb3VudGVycy5tYXAoXG4gICAgICAgICAgICAoIGl0ZW0gOiBDb3VudGVySXRlbSApID0+ICh7XG4gICAgICAgICAgICAgICAgaWQ6IGl0ZW0uaWQsXG4gICAgICAgICAgICAgICAgY291bnRlcjogdXBkYXRlQ291bnRlciggaXRlbS5jb3VudGVyLCBhY3Rpb25Jbml0aWFsaXplQ291bnRlciApXG4gICAgICAgICAgICB9KVxuICAgICAgICApXG4gICAgfTtcbn1cblxuZnVuY3Rpb24gcmVtb3ZlQ291bnRlciggbW9kZWwgOiBNb2RlbCwgaWQgOiBudW1iZXIgKSA6IE1vZGVsIHtcbiAgICByZXR1cm4ge1xuICAgICAgICBuZXh0SUQ6IG1vZGVsLm5leHRJRCxcbiAgICAgICAgY291bnRlcnM6IG1vZGVsLmNvdW50ZXJzLmZpbHRlciggaXRlbSA9PiBpdGVtLmlkICE9PSBpZCApXG4gICAgfTtcbn1cblxuZnVuY3Rpb24gdXBkYXRlQ291bnRlclJvdyggbW9kZWwgOiBNb2RlbCwgaWQgOiBudW1iZXIsIGFjdGlvbiA6IENvdW50ZXJBY3Rpb24gKSA6IE1vZGVsIHtcbiAgICByZXR1cm4ge1xuICAgICAgICBuZXh0SUQ6IG1vZGVsLm5leHRJRCxcbiAgICAgICAgY291bnRlcnM6IG1vZGVsLmNvdW50ZXJzLm1hcChcbiAgICAgICAgICAgIGl0ZW0gPT5cbiAgICAgICAgICAgICAgICBpdGVtLmlkICE9PSBpZCA/XG4gICAgICAgICAgICAgICAgICAgIGl0ZW1cbiAgICAgICAgICAgICAgICAgICAgOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICBpZDogaXRlbS5pZCxcbiAgICAgICAgICAgICAgICAgICAgICAgIGNvdW50ZXI6IHVwZGF0ZUNvdW50ZXIoIGl0ZW0uY291bnRlciwgYWN0aW9uIClcbiAgICAgICAgICAgICAgICAgICAgfVxuICAgICAgICApXG4gICAgfTtcbn1cblxuIiwiaW1wb3J0IHt2bm9kZSwgVk5vZGUsIFZOb2RlRGF0YX0gZnJvbSAnLi92bm9kZSc7XG5pbXBvcnQgKiBhcyBpcyBmcm9tICcuL2lzJztcblxuZnVuY3Rpb24gYWRkTlMoZGF0YTogYW55LCBjaGlsZHJlbjogQXJyYXk8Vk5vZGU+IHwgdW5kZWZpbmVkLCBzZWw6IHN0cmluZyB8IHVuZGVmaW5lZCk6IHZvaWQge1xuICBkYXRhLm5zID0gJ2h0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnJztcbiAgaWYgKHNlbCAhPT0gJ2ZvcmVpZ25PYmplY3QnICYmIGNoaWxkcmVuICE9PSB1bmRlZmluZWQpIHtcbiAgICBmb3IgKGxldCBpID0gMDsgaSA8IGNoaWxkcmVuLmxlbmd0aDsgKytpKSB7XG4gICAgICBsZXQgY2hpbGREYXRhID0gY2hpbGRyZW5baV0uZGF0YTtcbiAgICAgIGlmIChjaGlsZERhdGEgIT09IHVuZGVmaW5lZCkge1xuICAgICAgICBhZGROUyhjaGlsZERhdGEsIChjaGlsZHJlbltpXSBhcyBWTm9kZSkuY2hpbGRyZW4gYXMgQXJyYXk8Vk5vZGU+LCBjaGlsZHJlbltpXS5zZWwpO1xuICAgICAgfVxuICAgIH1cbiAgfVxufVxuXG5leHBvcnQgZnVuY3Rpb24gaChzZWw6IHN0cmluZyk6IFZOb2RlO1xuZXhwb3J0IGZ1bmN0aW9uIGgoc2VsOiBzdHJpbmcsIGRhdGE6IFZOb2RlRGF0YSk6IFZOb2RlO1xuZXhwb3J0IGZ1bmN0aW9uIGgoc2VsOiBzdHJpbmcsIHRleHQ6IHN0cmluZyk6IFZOb2RlO1xuZXhwb3J0IGZ1bmN0aW9uIGgoc2VsOiBzdHJpbmcsIGNoaWxkcmVuOiBBcnJheTxWTm9kZT4pOiBWTm9kZTtcbmV4cG9ydCBmdW5jdGlvbiBoKHNlbDogc3RyaW5nLCBkYXRhOiBWTm9kZURhdGEsIHRleHQ6IHN0cmluZyk6IFZOb2RlO1xuZXhwb3J0IGZ1bmN0aW9uIGgoc2VsOiBzdHJpbmcsIGRhdGE6IFZOb2RlRGF0YSwgY2hpbGRyZW46IEFycmF5PFZOb2RlPik6IFZOb2RlO1xuZXhwb3J0IGZ1bmN0aW9uIGgoc2VsOiBhbnksIGI/OiBhbnksIGM/OiBhbnkpOiBWTm9kZSB7XG4gIHZhciBkYXRhOiBWTm9kZURhdGEgPSB7fSwgY2hpbGRyZW46IGFueSwgdGV4dDogYW55LCBpOiBudW1iZXI7XG4gIGlmIChjICE9PSB1bmRlZmluZWQpIHtcbiAgICBkYXRhID0gYjtcbiAgICBpZiAoaXMuYXJyYXkoYykpIHsgY2hpbGRyZW4gPSBjOyB9XG4gICAgZWxzZSBpZiAoaXMucHJpbWl0aXZlKGMpKSB7IHRleHQgPSBjOyB9XG4gICAgZWxzZSBpZiAoYyAmJiBjLnNlbCkgeyBjaGlsZHJlbiA9IFtjXTsgfVxuICB9IGVsc2UgaWYgKGIgIT09IHVuZGVmaW5lZCkge1xuICAgIGlmIChpcy5hcnJheShiKSkgeyBjaGlsZHJlbiA9IGI7IH1cbiAgICBlbHNlIGlmIChpcy5wcmltaXRpdmUoYikpIHsgdGV4dCA9IGI7IH1cbiAgICBlbHNlIGlmIChiICYmIGIuc2VsKSB7IGNoaWxkcmVuID0gW2JdOyB9XG4gICAgZWxzZSB7IGRhdGEgPSBiOyB9XG4gIH1cbiAgaWYgKGlzLmFycmF5KGNoaWxkcmVuKSkge1xuICAgIGZvciAoaSA9IDA7IGkgPCBjaGlsZHJlbi5sZW5ndGg7ICsraSkge1xuICAgICAgaWYgKGlzLnByaW1pdGl2ZShjaGlsZHJlbltpXSkpIGNoaWxkcmVuW2ldID0gKHZub2RlIGFzIGFueSkodW5kZWZpbmVkLCB1bmRlZmluZWQsIHVuZGVmaW5lZCwgY2hpbGRyZW5baV0pO1xuICAgIH1cbiAgfVxuICBpZiAoXG4gICAgc2VsWzBdID09PSAncycgJiYgc2VsWzFdID09PSAndicgJiYgc2VsWzJdID09PSAnZycgJiZcbiAgICAoc2VsLmxlbmd0aCA9PT0gMyB8fCBzZWxbM10gPT09ICcuJyB8fCBzZWxbM10gPT09ICcjJylcbiAgKSB7XG4gICAgYWRkTlMoZGF0YSwgY2hpbGRyZW4sIHNlbCk7XG4gIH1cbiAgcmV0dXJuIHZub2RlKHNlbCwgZGF0YSwgY2hpbGRyZW4sIHRleHQsIHVuZGVmaW5lZCk7XG59O1xuZXhwb3J0IGRlZmF1bHQgaDtcbiIsImV4cG9ydCBpbnRlcmZhY2UgRE9NQVBJIHtcbiAgY3JlYXRlRWxlbWVudDogKHRhZ05hbWU6IGFueSkgPT4gSFRNTEVsZW1lbnQ7XG4gIGNyZWF0ZUVsZW1lbnROUzogKG5hbWVzcGFjZVVSSTogc3RyaW5nLCBxdWFsaWZpZWROYW1lOiBzdHJpbmcpID0+IEVsZW1lbnQ7XG4gIGNyZWF0ZVRleHROb2RlOiAodGV4dDogc3RyaW5nKSA9PiBUZXh0O1xuICBpbnNlcnRCZWZvcmU6IChwYXJlbnROb2RlOiBOb2RlLCBuZXdOb2RlOiBOb2RlLCByZWZlcmVuY2VOb2RlOiBOb2RlIHwgbnVsbCkgPT4gdm9pZDtcbiAgcmVtb3ZlQ2hpbGQ6IChub2RlOiBOb2RlLCBjaGlsZDogTm9kZSkgPT4gdm9pZDtcbiAgYXBwZW5kQ2hpbGQ6IChub2RlOiBOb2RlLCBjaGlsZDogTm9kZSkgPT4gdm9pZDtcbiAgcGFyZW50Tm9kZTogKG5vZGU6IE5vZGUpID0+IE5vZGU7XG4gIG5leHRTaWJsaW5nOiAobm9kZTogTm9kZSkgPT4gTm9kZTtcbiAgdGFnTmFtZTogKGVsbTogRWxlbWVudCkgPT4gc3RyaW5nO1xuICBzZXRUZXh0Q29udGVudDogKG5vZGU6IE5vZGUsIHRleHQ6IHN0cmluZyB8IG51bGwpID0+IHZvaWQ7XG59XG5cbmZ1bmN0aW9uIGNyZWF0ZUVsZW1lbnQodGFnTmFtZTogYW55KTogSFRNTEVsZW1lbnQge1xuICByZXR1cm4gZG9jdW1lbnQuY3JlYXRlRWxlbWVudCh0YWdOYW1lKTtcbn1cblxuZnVuY3Rpb24gY3JlYXRlRWxlbWVudE5TKG5hbWVzcGFjZVVSSTogc3RyaW5nLCBxdWFsaWZpZWROYW1lOiBzdHJpbmcpOiBFbGVtZW50IHtcbiAgcmV0dXJuIGRvY3VtZW50LmNyZWF0ZUVsZW1lbnROUyhuYW1lc3BhY2VVUkksIHF1YWxpZmllZE5hbWUpO1xufVxuXG5mdW5jdGlvbiBjcmVhdGVUZXh0Tm9kZSh0ZXh0OiBzdHJpbmcpOiBUZXh0IHtcbiAgcmV0dXJuIGRvY3VtZW50LmNyZWF0ZVRleHROb2RlKHRleHQpO1xufVxuXG5mdW5jdGlvbiBpbnNlcnRCZWZvcmUocGFyZW50Tm9kZTogTm9kZSwgbmV3Tm9kZTogTm9kZSwgcmVmZXJlbmNlTm9kZTogTm9kZSB8IG51bGwpOiB2b2lkIHtcbiAgcGFyZW50Tm9kZS5pbnNlcnRCZWZvcmUobmV3Tm9kZSwgcmVmZXJlbmNlTm9kZSk7XG59XG5cbmZ1bmN0aW9uIHJlbW92ZUNoaWxkKG5vZGU6IE5vZGUsIGNoaWxkOiBOb2RlKTogdm9pZCB7XG4gIG5vZGUucmVtb3ZlQ2hpbGQoY2hpbGQpO1xufVxuXG5mdW5jdGlvbiBhcHBlbmRDaGlsZChub2RlOiBOb2RlLCBjaGlsZDogTm9kZSk6IHZvaWQge1xuICBub2RlLmFwcGVuZENoaWxkKGNoaWxkKTtcbn1cblxuZnVuY3Rpb24gcGFyZW50Tm9kZShub2RlOiBOb2RlKTogTm9kZSB8IG51bGwge1xuICByZXR1cm4gbm9kZS5wYXJlbnROb2RlO1xufVxuXG5mdW5jdGlvbiBuZXh0U2libGluZyhub2RlOiBOb2RlKTogTm9kZSB8IG51bGwge1xuICByZXR1cm4gbm9kZS5uZXh0U2libGluZztcbn1cblxuZnVuY3Rpb24gdGFnTmFtZShlbG06IEVsZW1lbnQpOiBzdHJpbmcge1xuICByZXR1cm4gZWxtLnRhZ05hbWU7XG59XG5cbmZ1bmN0aW9uIHNldFRleHRDb250ZW50KG5vZGU6IE5vZGUsIHRleHQ6IHN0cmluZyB8IG51bGwpOiB2b2lkIHtcbiAgbm9kZS50ZXh0Q29udGVudCA9IHRleHQ7XG59XG5cbmV4cG9ydCBjb25zdCBodG1sRG9tQXBpID0ge1xuICBjcmVhdGVFbGVtZW50LFxuICBjcmVhdGVFbGVtZW50TlMsXG4gIGNyZWF0ZVRleHROb2RlLFxuICBpbnNlcnRCZWZvcmUsXG4gIHJlbW92ZUNoaWxkLFxuICBhcHBlbmRDaGlsZCxcbiAgcGFyZW50Tm9kZSxcbiAgbmV4dFNpYmxpbmcsXG4gIHRhZ05hbWUsXG4gIHNldFRleHRDb250ZW50LFxufSBhcyBET01BUEk7XG5cbmV4cG9ydCBkZWZhdWx0IGh0bWxEb21BcGk7XG4iLCJleHBvcnQgY29uc3QgYXJyYXkgPSBBcnJheS5pc0FycmF5O1xuZXhwb3J0IGZ1bmN0aW9uIHByaW1pdGl2ZShzOiBhbnkpOiBzIGlzIChzdHJpbmcgfCBudW1iZXIpIHtcbiAgcmV0dXJuIHR5cGVvZiBzID09PSAnc3RyaW5nJyB8fCB0eXBlb2YgcyA9PT0gJ251bWJlcic7XG59XG4iLCJpbXBvcnQge1ZOb2RlLCBWTm9kZURhdGF9IGZyb20gJy4uL3Zub2RlJztcbmltcG9ydCB7TW9kdWxlfSBmcm9tICcuL21vZHVsZSc7XG5cbmZ1bmN0aW9uIHVwZGF0ZUNsYXNzKG9sZFZub2RlOiBWTm9kZSwgdm5vZGU6IFZOb2RlKTogdm9pZCB7XG4gIHZhciBjdXI6IGFueSwgbmFtZTogc3RyaW5nLCBlbG06IEVsZW1lbnQgPSB2bm9kZS5lbG0gYXMgRWxlbWVudCxcbiAgICAgIG9sZENsYXNzID0gKG9sZFZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5jbGFzcyxcbiAgICAgIGtsYXNzID0gKHZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5jbGFzcztcblxuICBpZiAoIW9sZENsYXNzICYmICFrbGFzcykgcmV0dXJuO1xuICBpZiAob2xkQ2xhc3MgPT09IGtsYXNzKSByZXR1cm47XG4gIG9sZENsYXNzID0gb2xkQ2xhc3MgfHwge307XG4gIGtsYXNzID0ga2xhc3MgfHwge307XG5cbiAgZm9yIChuYW1lIGluIG9sZENsYXNzKSB7XG4gICAgaWYgKCFrbGFzc1tuYW1lXSkge1xuICAgICAgZWxtLmNsYXNzTGlzdC5yZW1vdmUobmFtZSk7XG4gICAgfVxuICB9XG4gIGZvciAobmFtZSBpbiBrbGFzcykge1xuICAgIGN1ciA9IGtsYXNzW25hbWVdO1xuICAgIGlmIChjdXIgIT09IG9sZENsYXNzW25hbWVdKSB7XG4gICAgICAoZWxtLmNsYXNzTGlzdCBhcyBhbnkpW2N1ciA/ICdhZGQnIDogJ3JlbW92ZSddKG5hbWUpO1xuICAgIH1cbiAgfVxufVxuXG5leHBvcnQgY29uc3QgY2xhc3NNb2R1bGUgPSB7Y3JlYXRlOiB1cGRhdGVDbGFzcywgdXBkYXRlOiB1cGRhdGVDbGFzc30gYXMgTW9kdWxlO1xuZXhwb3J0IGRlZmF1bHQgY2xhc3NNb2R1bGU7XG4iLCJpbXBvcnQge1ZOb2RlLCBWTm9kZURhdGF9IGZyb20gJy4uL3Zub2RlJztcbmltcG9ydCB7TW9kdWxlfSBmcm9tICcuL21vZHVsZSc7XG5cbmZ1bmN0aW9uIGludm9rZUhhbmRsZXIoaGFuZGxlcjogYW55LCB2bm9kZT86IFZOb2RlLCBldmVudD86IEV2ZW50KTogdm9pZCB7XG4gIGlmICh0eXBlb2YgaGFuZGxlciA9PT0gXCJmdW5jdGlvblwiKSB7XG4gICAgLy8gY2FsbCBmdW5jdGlvbiBoYW5kbGVyXG4gICAgaGFuZGxlci5jYWxsKHZub2RlLCBldmVudCwgdm5vZGUpO1xuICB9IGVsc2UgaWYgKHR5cGVvZiBoYW5kbGVyID09PSBcIm9iamVjdFwiKSB7XG4gICAgLy8gY2FsbCBoYW5kbGVyIHdpdGggYXJndW1lbnRzXG4gICAgaWYgKHR5cGVvZiBoYW5kbGVyWzBdID09PSBcImZ1bmN0aW9uXCIpIHtcbiAgICAgIC8vIHNwZWNpYWwgY2FzZSBmb3Igc2luZ2xlIGFyZ3VtZW50IGZvciBwZXJmb3JtYW5jZVxuICAgICAgaWYgKGhhbmRsZXIubGVuZ3RoID09PSAyKSB7XG4gICAgICAgIGhhbmRsZXJbMF0uY2FsbCh2bm9kZSwgaGFuZGxlclsxXSwgZXZlbnQsIHZub2RlKTtcbiAgICAgIH0gZWxzZSB7XG4gICAgICAgIHZhciBhcmdzID0gaGFuZGxlci5zbGljZSgxKTtcbiAgICAgICAgYXJncy5wdXNoKGV2ZW50KTtcbiAgICAgICAgYXJncy5wdXNoKHZub2RlKTtcbiAgICAgICAgaGFuZGxlclswXS5hcHBseSh2bm9kZSwgYXJncyk7XG4gICAgICB9XG4gICAgfSBlbHNlIHtcbiAgICAgIC8vIGNhbGwgbXVsdGlwbGUgaGFuZGxlcnNcbiAgICAgIGZvciAodmFyIGkgPSAwOyBpIDwgaGFuZGxlci5sZW5ndGg7IGkrKykge1xuICAgICAgICBpbnZva2VIYW5kbGVyKGhhbmRsZXJbaV0pO1xuICAgICAgfVxuICAgIH1cbiAgfVxufVxuXG5mdW5jdGlvbiBoYW5kbGVFdmVudChldmVudDogRXZlbnQsIHZub2RlOiBWTm9kZSkge1xuICB2YXIgbmFtZSA9IGV2ZW50LnR5cGUsXG4gICAgICBvbiA9ICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkub247XG5cbiAgLy8gY2FsbCBldmVudCBoYW5kbGVyKHMpIGlmIGV4aXN0c1xuICBpZiAob24gJiYgb25bbmFtZV0pIHtcbiAgICBpbnZva2VIYW5kbGVyKG9uW25hbWVdLCB2bm9kZSwgZXZlbnQpO1xuICB9XG59XG5cbmZ1bmN0aW9uIGNyZWF0ZUxpc3RlbmVyKCkge1xuICByZXR1cm4gZnVuY3Rpb24gaGFuZGxlcihldmVudDogRXZlbnQpIHtcbiAgICBoYW5kbGVFdmVudChldmVudCwgKGhhbmRsZXIgYXMgYW55KS52bm9kZSk7XG4gIH1cbn1cblxuZnVuY3Rpb24gdXBkYXRlRXZlbnRMaXN0ZW5lcnMob2xkVm5vZGU6IFZOb2RlLCB2bm9kZT86IFZOb2RlKTogdm9pZCB7XG4gIHZhciBvbGRPbiA9IChvbGRWbm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkub24sXG4gICAgICBvbGRMaXN0ZW5lciA9IChvbGRWbm9kZSBhcyBhbnkpLmxpc3RlbmVyLFxuICAgICAgb2xkRWxtOiBFbGVtZW50ID0gb2xkVm5vZGUuZWxtIGFzIEVsZW1lbnQsXG4gICAgICBvbiA9IHZub2RlICYmICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkub24sXG4gICAgICBlbG06IEVsZW1lbnQgPSAodm5vZGUgJiYgdm5vZGUuZWxtKSBhcyBFbGVtZW50LFxuICAgICAgbmFtZTogc3RyaW5nO1xuXG4gIC8vIG9wdGltaXphdGlvbiBmb3IgcmV1c2VkIGltbXV0YWJsZSBoYW5kbGVyc1xuICBpZiAob2xkT24gPT09IG9uKSB7XG4gICAgcmV0dXJuO1xuICB9XG5cbiAgLy8gcmVtb3ZlIGV4aXN0aW5nIGxpc3RlbmVycyB3aGljaCBubyBsb25nZXIgdXNlZFxuICBpZiAob2xkT24gJiYgb2xkTGlzdGVuZXIpIHtcbiAgICAvLyBpZiBlbGVtZW50IGNoYW5nZWQgb3IgZGVsZXRlZCB3ZSByZW1vdmUgYWxsIGV4aXN0aW5nIGxpc3RlbmVycyB1bmNvbmRpdGlvbmFsbHlcbiAgICBpZiAoIW9uKSB7XG4gICAgICBmb3IgKG5hbWUgaW4gb2xkT24pIHtcbiAgICAgICAgLy8gcmVtb3ZlIGxpc3RlbmVyIGlmIGVsZW1lbnQgd2FzIGNoYW5nZWQgb3IgZXhpc3RpbmcgbGlzdGVuZXJzIHJlbW92ZWRcbiAgICAgICAgb2xkRWxtLnJlbW92ZUV2ZW50TGlzdGVuZXIobmFtZSwgb2xkTGlzdGVuZXIsIGZhbHNlKTtcbiAgICAgIH1cbiAgICB9IGVsc2Uge1xuICAgICAgZm9yIChuYW1lIGluIG9sZE9uKSB7XG4gICAgICAgIC8vIHJlbW92ZSBsaXN0ZW5lciBpZiBleGlzdGluZyBsaXN0ZW5lciByZW1vdmVkXG4gICAgICAgIGlmICghb25bbmFtZV0pIHtcbiAgICAgICAgICBvbGRFbG0ucmVtb3ZlRXZlbnRMaXN0ZW5lcihuYW1lLCBvbGRMaXN0ZW5lciwgZmFsc2UpO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfVxuICB9XG5cbiAgLy8gYWRkIG5ldyBsaXN0ZW5lcnMgd2hpY2ggaGFzIG5vdCBhbHJlYWR5IGF0dGFjaGVkXG4gIGlmIChvbikge1xuICAgIC8vIHJldXNlIGV4aXN0aW5nIGxpc3RlbmVyIG9yIGNyZWF0ZSBuZXdcbiAgICB2YXIgbGlzdGVuZXIgPSAodm5vZGUgYXMgYW55KS5saXN0ZW5lciA9IChvbGRWbm9kZSBhcyBhbnkpLmxpc3RlbmVyIHx8IGNyZWF0ZUxpc3RlbmVyKCk7XG4gICAgLy8gdXBkYXRlIHZub2RlIGZvciBsaXN0ZW5lclxuICAgIGxpc3RlbmVyLnZub2RlID0gdm5vZGU7XG5cbiAgICAvLyBpZiBlbGVtZW50IGNoYW5nZWQgb3IgYWRkZWQgd2UgYWRkIGFsbCBuZWVkZWQgbGlzdGVuZXJzIHVuY29uZGl0aW9uYWxseVxuICAgIGlmICghb2xkT24pIHtcbiAgICAgIGZvciAobmFtZSBpbiBvbikge1xuICAgICAgICAvLyBhZGQgbGlzdGVuZXIgaWYgZWxlbWVudCB3YXMgY2hhbmdlZCBvciBuZXcgbGlzdGVuZXJzIGFkZGVkXG4gICAgICAgIGVsbS5hZGRFdmVudExpc3RlbmVyKG5hbWUsIGxpc3RlbmVyLCBmYWxzZSk7XG4gICAgICB9XG4gICAgfSBlbHNlIHtcbiAgICAgIGZvciAobmFtZSBpbiBvbikge1xuICAgICAgICAvLyBhZGQgbGlzdGVuZXIgaWYgbmV3IGxpc3RlbmVyIGFkZGVkXG4gICAgICAgIGlmICghb2xkT25bbmFtZV0pIHtcbiAgICAgICAgICBlbG0uYWRkRXZlbnRMaXN0ZW5lcihuYW1lLCBsaXN0ZW5lciwgZmFsc2UpO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfVxuICB9XG59XG5cbmV4cG9ydCBjb25zdCBldmVudExpc3RlbmVyc01vZHVsZSA9IHtcbiAgY3JlYXRlOiB1cGRhdGVFdmVudExpc3RlbmVycyxcbiAgdXBkYXRlOiB1cGRhdGVFdmVudExpc3RlbmVycyxcbiAgZGVzdHJveTogdXBkYXRlRXZlbnRMaXN0ZW5lcnNcbn0gYXMgTW9kdWxlO1xuZXhwb3J0IGRlZmF1bHQgZXZlbnRMaXN0ZW5lcnNNb2R1bGU7IiwiaW1wb3J0IHtWTm9kZSwgVk5vZGVEYXRhfSBmcm9tICcuLi92bm9kZSc7XG5pbXBvcnQge01vZHVsZX0gZnJvbSAnLi9tb2R1bGUnO1xuXG5mdW5jdGlvbiB1cGRhdGVQcm9wcyhvbGRWbm9kZTogVk5vZGUsIHZub2RlOiBWTm9kZSk6IHZvaWQge1xuICB2YXIga2V5OiBzdHJpbmcsIGN1cjogYW55LCBvbGQ6IGFueSwgZWxtID0gdm5vZGUuZWxtLFxuICAgICAgb2xkUHJvcHMgPSAob2xkVm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLnByb3BzLFxuICAgICAgcHJvcHMgPSAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLnByb3BzO1xuXG4gIGlmICghb2xkUHJvcHMgJiYgIXByb3BzKSByZXR1cm47XG4gIGlmIChvbGRQcm9wcyA9PT0gcHJvcHMpIHJldHVybjtcbiAgb2xkUHJvcHMgPSBvbGRQcm9wcyB8fCB7fTtcbiAgcHJvcHMgPSBwcm9wcyB8fCB7fTtcblxuICBmb3IgKGtleSBpbiBvbGRQcm9wcykge1xuICAgIGlmICghcHJvcHNba2V5XSkge1xuICAgICAgZGVsZXRlIChlbG0gYXMgYW55KVtrZXldO1xuICAgIH1cbiAgfVxuICBmb3IgKGtleSBpbiBwcm9wcykge1xuICAgIGN1ciA9IHByb3BzW2tleV07XG4gICAgb2xkID0gb2xkUHJvcHNba2V5XTtcbiAgICBpZiAob2xkICE9PSBjdXIgJiYgKGtleSAhPT0gJ3ZhbHVlJyB8fCAoZWxtIGFzIGFueSlba2V5XSAhPT0gY3VyKSkge1xuICAgICAgKGVsbSBhcyBhbnkpW2tleV0gPSBjdXI7XG4gICAgfVxuICB9XG59XG5cbmV4cG9ydCBjb25zdCBwcm9wc01vZHVsZSA9IHtjcmVhdGU6IHVwZGF0ZVByb3BzLCB1cGRhdGU6IHVwZGF0ZVByb3BzfSBhcyBNb2R1bGU7XG5leHBvcnQgZGVmYXVsdCBwcm9wc01vZHVsZTsiLCJpbXBvcnQge1ZOb2RlLCBWTm9kZURhdGF9IGZyb20gJy4uL3Zub2RlJztcbmltcG9ydCB7TW9kdWxlfSBmcm9tICcuL21vZHVsZSc7XG5cbnZhciByYWYgPSAodHlwZW9mIHdpbmRvdyAhPT0gJ3VuZGVmaW5lZCcgJiYgd2luZG93LnJlcXVlc3RBbmltYXRpb25GcmFtZSkgfHwgc2V0VGltZW91dDtcbnZhciBuZXh0RnJhbWUgPSBmdW5jdGlvbihmbjogYW55KSB7IHJhZihmdW5jdGlvbigpIHsgcmFmKGZuKTsgfSk7IH07XG5cbmZ1bmN0aW9uIHNldE5leHRGcmFtZShvYmo6IGFueSwgcHJvcDogc3RyaW5nLCB2YWw6IGFueSk6IHZvaWQge1xuICBuZXh0RnJhbWUoZnVuY3Rpb24oKSB7IG9ialtwcm9wXSA9IHZhbDsgfSk7XG59XG5cbmZ1bmN0aW9uIHVwZGF0ZVN0eWxlKG9sZFZub2RlOiBWTm9kZSwgdm5vZGU6IFZOb2RlKTogdm9pZCB7XG4gIHZhciBjdXI6IGFueSwgbmFtZTogc3RyaW5nLCBlbG0gPSB2bm9kZS5lbG0sXG4gICAgICBvbGRTdHlsZSA9IChvbGRWbm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuc3R5bGUsXG4gICAgICBzdHlsZSA9ICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuc3R5bGU7XG5cbiAgaWYgKCFvbGRTdHlsZSAmJiAhc3R5bGUpIHJldHVybjtcbiAgaWYgKG9sZFN0eWxlID09PSBzdHlsZSkgcmV0dXJuO1xuICBvbGRTdHlsZSA9IG9sZFN0eWxlIHx8IHt9O1xuICBzdHlsZSA9IHN0eWxlIHx8IHt9O1xuICB2YXIgb2xkSGFzRGVsID0gJ2RlbGF5ZWQnIGluIG9sZFN0eWxlO1xuXG4gIGZvciAobmFtZSBpbiBvbGRTdHlsZSkge1xuICAgIGlmICghc3R5bGVbbmFtZV0pIHtcbiAgICAgIGlmIChuYW1lLnN0YXJ0c1dpdGgoJy0tJykpIHtcbiAgICAgICAgKGVsbSBhcyBhbnkpLnN0eWxlLnJlbW92ZVByb3BlcnR5KG5hbWUpO1xuICAgICAgfSBlbHNlIHtcbiAgICAgICAgKGVsbSBhcyBhbnkpLnN0eWxlW25hbWVdID0gJyc7XG4gICAgICB9XG4gICAgfVxuICB9XG4gIGZvciAobmFtZSBpbiBzdHlsZSkge1xuICAgIGN1ciA9IHN0eWxlW25hbWVdO1xuICAgIGlmIChuYW1lID09PSAnZGVsYXllZCcpIHtcbiAgICAgIGZvciAobmFtZSBpbiBzdHlsZS5kZWxheWVkKSB7XG4gICAgICAgIGN1ciA9IHN0eWxlLmRlbGF5ZWRbbmFtZV07XG4gICAgICAgIGlmICghb2xkSGFzRGVsIHx8IGN1ciAhPT0gb2xkU3R5bGUuZGVsYXllZFtuYW1lXSkge1xuICAgICAgICAgIHNldE5leHRGcmFtZSgoZWxtIGFzIGFueSkuc3R5bGUsIG5hbWUsIGN1cik7XG4gICAgICAgIH1cbiAgICAgIH1cbiAgICB9IGVsc2UgaWYgKG5hbWUgIT09ICdyZW1vdmUnICYmIGN1ciAhPT0gb2xkU3R5bGVbbmFtZV0pIHtcbiAgICAgIGlmIChuYW1lLnN0YXJ0c1dpdGgoJy0tJykpIHtcbiAgICAgICAgKGVsbSBhcyBhbnkpLnN0eWxlLnNldFByb3BlcnR5KG5hbWUsIGN1cik7XG4gICAgICB9IGVsc2Uge1xuICAgICAgICAoZWxtIGFzIGFueSkuc3R5bGVbbmFtZV0gPSBjdXI7XG4gICAgICB9XG4gICAgfVxuICB9XG59XG5cbmZ1bmN0aW9uIGFwcGx5RGVzdHJveVN0eWxlKHZub2RlOiBWTm9kZSk6IHZvaWQge1xuICB2YXIgc3R5bGU6IGFueSwgbmFtZTogc3RyaW5nLCBlbG0gPSB2bm9kZS5lbG0sIHMgPSAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLnN0eWxlO1xuICBpZiAoIXMgfHwgIShzdHlsZSA9IHMuZGVzdHJveSkpIHJldHVybjtcbiAgZm9yIChuYW1lIGluIHN0eWxlKSB7XG4gICAgKGVsbSBhcyBhbnkpLnN0eWxlW25hbWVdID0gc3R5bGVbbmFtZV07XG4gIH1cbn1cblxuZnVuY3Rpb24gYXBwbHlSZW1vdmVTdHlsZSh2bm9kZTogVk5vZGUsIHJtOiAoKSA9PiB2b2lkKTogdm9pZCB7XG4gIHZhciBzID0gKHZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5zdHlsZTtcbiAgaWYgKCFzIHx8ICFzLnJlbW92ZSkge1xuICAgIHJtKCk7XG4gICAgcmV0dXJuO1xuICB9XG4gIHZhciBuYW1lOiBzdHJpbmcsIGVsbSA9IHZub2RlLmVsbSwgaSA9IDAsIGNvbXBTdHlsZTogQ1NTU3R5bGVEZWNsYXJhdGlvbixcbiAgICAgIHN0eWxlID0gcy5yZW1vdmUsIGFtb3VudCA9IDAsIGFwcGxpZWQ6IEFycmF5PHN0cmluZz4gPSBbXTtcbiAgZm9yIChuYW1lIGluIHN0eWxlKSB7XG4gICAgYXBwbGllZC5wdXNoKG5hbWUpO1xuICAgIChlbG0gYXMgYW55KS5zdHlsZVtuYW1lXSA9IHN0eWxlW25hbWVdO1xuICB9XG4gIGNvbXBTdHlsZSA9IGdldENvbXB1dGVkU3R5bGUoZWxtIGFzIEVsZW1lbnQpO1xuICB2YXIgcHJvcHMgPSAoY29tcFN0eWxlIGFzIGFueSlbJ3RyYW5zaXRpb24tcHJvcGVydHknXS5zcGxpdCgnLCAnKTtcbiAgZm9yICg7IGkgPCBwcm9wcy5sZW5ndGg7ICsraSkge1xuICAgIGlmKGFwcGxpZWQuaW5kZXhPZihwcm9wc1tpXSkgIT09IC0xKSBhbW91bnQrKztcbiAgfVxuICAoZWxtIGFzIEVsZW1lbnQpLmFkZEV2ZW50TGlzdGVuZXIoJ3RyYW5zaXRpb25lbmQnLCBmdW5jdGlvbiAoZXY6IFRyYW5zaXRpb25FdmVudCkge1xuICAgIGlmIChldi50YXJnZXQgPT09IGVsbSkgLS1hbW91bnQ7XG4gICAgaWYgKGFtb3VudCA9PT0gMCkgcm0oKTtcbiAgfSk7XG59XG5cbmV4cG9ydCBjb25zdCBzdHlsZU1vZHVsZSA9IHtcbiAgY3JlYXRlOiB1cGRhdGVTdHlsZSxcbiAgdXBkYXRlOiB1cGRhdGVTdHlsZSxcbiAgZGVzdHJveTogYXBwbHlEZXN0cm95U3R5bGUsXG4gIHJlbW92ZTogYXBwbHlSZW1vdmVTdHlsZVxufSBhcyBNb2R1bGU7XG5leHBvcnQgZGVmYXVsdCBzdHlsZU1vZHVsZTtcbiIsIi8qIGdsb2JhbCBtb2R1bGUsIGRvY3VtZW50LCBOb2RlICovXG5pbXBvcnQge01vZHVsZX0gZnJvbSAnLi9tb2R1bGVzL21vZHVsZSc7XG5pbXBvcnQge0hvb2tzfSBmcm9tICcuL2hvb2tzJztcbmltcG9ydCB2bm9kZSwge1ZOb2RlLCBWTm9kZURhdGEsIEtleX0gZnJvbSAnLi92bm9kZSc7XG5pbXBvcnQgKiBhcyBpcyBmcm9tICcuL2lzJztcbmltcG9ydCBodG1sRG9tQXBpLCB7RE9NQVBJfSBmcm9tICcuL2h0bWxkb21hcGknO1xuXG5mdW5jdGlvbiBpc1VuZGVmKHM6IGFueSk6IGJvb2xlYW4geyByZXR1cm4gcyA9PT0gdW5kZWZpbmVkOyB9XG5mdW5jdGlvbiBpc0RlZihzOiBhbnkpOiBib29sZWFuIHsgcmV0dXJuIHMgIT09IHVuZGVmaW5lZDsgfVxuXG50eXBlIFZOb2RlUXVldWUgPSBBcnJheTxWTm9kZT47XG5cbmNvbnN0IGVtcHR5Tm9kZSA9IHZub2RlKCcnLCB7fSwgW10sIHVuZGVmaW5lZCwgdW5kZWZpbmVkKTtcblxuZnVuY3Rpb24gc2FtZVZub2RlKHZub2RlMTogVk5vZGUsIHZub2RlMjogVk5vZGUpOiBib29sZWFuIHtcbiAgcmV0dXJuIHZub2RlMS5rZXkgPT09IHZub2RlMi5rZXkgJiYgdm5vZGUxLnNlbCA9PT0gdm5vZGUyLnNlbDtcbn1cblxuZnVuY3Rpb24gaXNWbm9kZSh2bm9kZTogYW55KTogdm5vZGUgaXMgVk5vZGUge1xuICByZXR1cm4gdm5vZGUuc2VsICE9PSB1bmRlZmluZWQ7XG59XG5cbnR5cGUgS2V5VG9JbmRleE1hcCA9IHtba2V5OiBzdHJpbmddOiBudW1iZXJ9O1xuXG50eXBlIEFycmF5c09mPFQ+ID0ge1xuICBbSyBpbiBrZXlvZiBUXTogKFRbS10pW107XG59XG5cbnR5cGUgTW9kdWxlSG9va3MgPSBBcnJheXNPZjxNb2R1bGU+O1xuXG5mdW5jdGlvbiBjcmVhdGVLZXlUb09sZElkeChjaGlsZHJlbjogQXJyYXk8Vk5vZGU+LCBiZWdpbklkeDogbnVtYmVyLCBlbmRJZHg6IG51bWJlcik6IEtleVRvSW5kZXhNYXAge1xuICBsZXQgaTogbnVtYmVyLCBtYXA6IEtleVRvSW5kZXhNYXAgPSB7fSwga2V5OiBLZXk7XG4gIGZvciAoaSA9IGJlZ2luSWR4OyBpIDw9IGVuZElkeDsgKytpKSB7XG4gICAga2V5ID0gY2hpbGRyZW5baV0ua2V5O1xuICAgIGlmIChrZXkgIT09IHVuZGVmaW5lZCkgbWFwW2tleV0gPSBpO1xuICB9XG4gIHJldHVybiBtYXA7XG59XG5cbmNvbnN0IGhvb2tzOiAoa2V5b2YgTW9kdWxlKVtdID0gWydjcmVhdGUnLCAndXBkYXRlJywgJ3JlbW92ZScsICdkZXN0cm95JywgJ3ByZScsICdwb3N0J107XG5cbmV4cG9ydCB7aH0gZnJvbSAnLi9oJztcbmV4cG9ydCB7dGh1bmt9IGZyb20gJy4vdGh1bmsnO1xuXG5leHBvcnQgZnVuY3Rpb24gaW5pdChtb2R1bGVzOiBBcnJheTxQYXJ0aWFsPE1vZHVsZT4+LCBkb21BcGk/OiBET01BUEkpIHtcbiAgbGV0IGk6IG51bWJlciwgajogbnVtYmVyLCBjYnMgPSAoe30gYXMgTW9kdWxlSG9va3MpO1xuXG4gIGNvbnN0IGFwaTogRE9NQVBJID0gZG9tQXBpICE9PSB1bmRlZmluZWQgPyBkb21BcGkgOiBodG1sRG9tQXBpO1xuXG4gIGZvciAoaSA9IDA7IGkgPCBob29rcy5sZW5ndGg7ICsraSkge1xuICAgIGNic1tob29rc1tpXV0gPSBbXTtcbiAgICBmb3IgKGogPSAwOyBqIDwgbW9kdWxlcy5sZW5ndGg7ICsraikge1xuICAgICAgY29uc3QgaG9vayA9IG1vZHVsZXNbal1baG9va3NbaV1dO1xuICAgICAgaWYgKGhvb2sgIT09IHVuZGVmaW5lZCkge1xuICAgICAgICAoY2JzW2hvb2tzW2ldXSBhcyBBcnJheTxhbnk+KS5wdXNoKGhvb2spO1xuICAgICAgfVxuICAgIH1cbiAgfVxuXG4gIGZ1bmN0aW9uIGVtcHR5Tm9kZUF0KGVsbTogRWxlbWVudCkge1xuICAgIGNvbnN0IGlkID0gZWxtLmlkID8gJyMnICsgZWxtLmlkIDogJyc7XG4gICAgY29uc3QgYyA9IGVsbS5jbGFzc05hbWUgPyAnLicgKyBlbG0uY2xhc3NOYW1lLnNwbGl0KCcgJykuam9pbignLicpIDogJyc7XG4gICAgcmV0dXJuIHZub2RlKGFwaS50YWdOYW1lKGVsbSkudG9Mb3dlckNhc2UoKSArIGlkICsgYywge30sIFtdLCB1bmRlZmluZWQsIGVsbSk7XG4gIH1cblxuICBmdW5jdGlvbiBjcmVhdGVSbUNiKGNoaWxkRWxtOiBOb2RlLCBsaXN0ZW5lcnM6IG51bWJlcikge1xuICAgIHJldHVybiBmdW5jdGlvbiBybUNiKCkge1xuICAgICAgaWYgKC0tbGlzdGVuZXJzID09PSAwKSB7XG4gICAgICAgIGNvbnN0IHBhcmVudCA9IGFwaS5wYXJlbnROb2RlKGNoaWxkRWxtKTtcbiAgICAgICAgYXBpLnJlbW92ZUNoaWxkKHBhcmVudCwgY2hpbGRFbG0pO1xuICAgICAgfVxuICAgIH07XG4gIH1cblxuICBmdW5jdGlvbiBjcmVhdGVFbG0odm5vZGU6IFZOb2RlLCBpbnNlcnRlZFZub2RlUXVldWU6IFZOb2RlUXVldWUpOiBOb2RlIHtcbiAgICBsZXQgaTogYW55LCBkYXRhID0gdm5vZGUuZGF0YTtcbiAgICBpZiAoZGF0YSAhPT0gdW5kZWZpbmVkKSB7XG4gICAgICBpZiAoaXNEZWYoaSA9IGRhdGEuaG9vaykgJiYgaXNEZWYoaSA9IGkuaW5pdCkpIHtcbiAgICAgICAgaSh2bm9kZSk7XG4gICAgICAgIGRhdGEgPSB2bm9kZS5kYXRhO1xuICAgICAgfVxuICAgIH1cbiAgICBsZXQgY2hpbGRyZW4gPSB2bm9kZS5jaGlsZHJlbiwgc2VsID0gdm5vZGUuc2VsO1xuICAgIGlmIChzZWwgIT09IHVuZGVmaW5lZCkge1xuICAgICAgLy8gUGFyc2Ugc2VsZWN0b3JcbiAgICAgIGNvbnN0IGhhc2hJZHggPSBzZWwuaW5kZXhPZignIycpO1xuICAgICAgY29uc3QgZG90SWR4ID0gc2VsLmluZGV4T2YoJy4nLCBoYXNoSWR4KTtcbiAgICAgIGNvbnN0IGhhc2ggPSBoYXNoSWR4ID4gMCA/IGhhc2hJZHggOiBzZWwubGVuZ3RoO1xuICAgICAgY29uc3QgZG90ID0gZG90SWR4ID4gMCA/IGRvdElkeCA6IHNlbC5sZW5ndGg7XG4gICAgICBjb25zdCB0YWcgPSBoYXNoSWR4ICE9PSAtMSB8fCBkb3RJZHggIT09IC0xID8gc2VsLnNsaWNlKDAsIE1hdGgubWluKGhhc2gsIGRvdCkpIDogc2VsO1xuICAgICAgY29uc3QgZWxtID0gdm5vZGUuZWxtID0gaXNEZWYoZGF0YSkgJiYgaXNEZWYoaSA9IChkYXRhIGFzIFZOb2RlRGF0YSkubnMpID8gYXBpLmNyZWF0ZUVsZW1lbnROUyhpLCB0YWcpXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgOiBhcGkuY3JlYXRlRWxlbWVudCh0YWcpO1xuICAgICAgaWYgKGhhc2ggPCBkb3QpIGVsbS5pZCA9IHNlbC5zbGljZShoYXNoICsgMSwgZG90KTtcbiAgICAgIGlmIChkb3RJZHggPiAwKSBlbG0uY2xhc3NOYW1lID0gc2VsLnNsaWNlKGRvdCArIDEpLnJlcGxhY2UoL1xcLi9nLCAnICcpO1xuICAgICAgaWYgKGlzLmFycmF5KGNoaWxkcmVuKSkge1xuICAgICAgICBmb3IgKGkgPSAwOyBpIDwgY2hpbGRyZW4ubGVuZ3RoOyArK2kpIHtcbiAgICAgICAgICBhcGkuYXBwZW5kQ2hpbGQoZWxtLCBjcmVhdGVFbG0oY2hpbGRyZW5baV0gYXMgVk5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSkpO1xuICAgICAgICB9XG4gICAgICB9IGVsc2UgaWYgKGlzLnByaW1pdGl2ZSh2bm9kZS50ZXh0KSkge1xuICAgICAgICBhcGkuYXBwZW5kQ2hpbGQoZWxtLCBhcGkuY3JlYXRlVGV4dE5vZGUodm5vZGUudGV4dCkpO1xuICAgICAgfVxuICAgICAgZm9yIChpID0gMDsgaSA8IGNicy5jcmVhdGUubGVuZ3RoOyArK2kpIGNicy5jcmVhdGVbaV0oZW1wdHlOb2RlLCB2bm9kZSk7XG4gICAgICBpID0gKHZub2RlLmRhdGEgYXMgVk5vZGVEYXRhKS5ob29rOyAvLyBSZXVzZSB2YXJpYWJsZVxuICAgICAgaWYgKGlzRGVmKGkpKSB7XG4gICAgICAgIGlmIChpLmNyZWF0ZSkgaS5jcmVhdGUoZW1wdHlOb2RlLCB2bm9kZSk7XG4gICAgICAgIGlmIChpLmluc2VydCkgaW5zZXJ0ZWRWbm9kZVF1ZXVlLnB1c2godm5vZGUpO1xuICAgICAgfVxuICAgIH0gZWxzZSB7XG4gICAgICB2bm9kZS5lbG0gPSBhcGkuY3JlYXRlVGV4dE5vZGUodm5vZGUudGV4dCBhcyBzdHJpbmcpO1xuICAgIH1cbiAgICByZXR1cm4gdm5vZGUuZWxtO1xuICB9XG5cbiAgZnVuY3Rpb24gYWRkVm5vZGVzKHBhcmVudEVsbTogTm9kZSxcbiAgICAgICAgICAgICAgICAgICAgIGJlZm9yZTogTm9kZSB8IG51bGwsXG4gICAgICAgICAgICAgICAgICAgICB2bm9kZXM6IEFycmF5PFZOb2RlPixcbiAgICAgICAgICAgICAgICAgICAgIHN0YXJ0SWR4OiBudW1iZXIsXG4gICAgICAgICAgICAgICAgICAgICBlbmRJZHg6IG51bWJlcixcbiAgICAgICAgICAgICAgICAgICAgIGluc2VydGVkVm5vZGVRdWV1ZTogVk5vZGVRdWV1ZSkge1xuICAgIGZvciAoOyBzdGFydElkeCA8PSBlbmRJZHg7ICsrc3RhcnRJZHgpIHtcbiAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50RWxtLCBjcmVhdGVFbG0odm5vZGVzW3N0YXJ0SWR4XSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKSwgYmVmb3JlKTtcbiAgICB9XG4gIH1cblxuICBmdW5jdGlvbiBpbnZva2VEZXN0cm95SG9vayh2bm9kZTogVk5vZGUpIHtcbiAgICBsZXQgaTogYW55LCBqOiBudW1iZXIsIGRhdGEgPSB2bm9kZS5kYXRhO1xuICAgIGlmIChkYXRhICE9PSB1bmRlZmluZWQpIHtcbiAgICAgIGlmIChpc0RlZihpID0gZGF0YS5ob29rKSAmJiBpc0RlZihpID0gaS5kZXN0cm95KSkgaSh2bm9kZSk7XG4gICAgICBmb3IgKGkgPSAwOyBpIDwgY2JzLmRlc3Ryb3kubGVuZ3RoOyArK2kpIGNicy5kZXN0cm95W2ldKHZub2RlKTtcbiAgICAgIGlmICh2bm9kZS5jaGlsZHJlbiAhPT0gdW5kZWZpbmVkKSB7XG4gICAgICAgIGZvciAoaiA9IDA7IGogPCB2bm9kZS5jaGlsZHJlbi5sZW5ndGg7ICsraikge1xuICAgICAgICAgIGkgPSB2bm9kZS5jaGlsZHJlbltqXTtcbiAgICAgICAgICBpZiAodHlwZW9mIGkgIT09IFwic3RyaW5nXCIpIHtcbiAgICAgICAgICAgIGludm9rZURlc3Ryb3lIb29rKGkpO1xuICAgICAgICAgIH1cbiAgICAgICAgfVxuICAgICAgfVxuICAgIH1cbiAgfVxuXG4gIGZ1bmN0aW9uIHJlbW92ZVZub2RlcyhwYXJlbnRFbG06IE5vZGUsXG4gICAgICAgICAgICAgICAgICAgICAgICB2bm9kZXM6IEFycmF5PFZOb2RlPixcbiAgICAgICAgICAgICAgICAgICAgICAgIHN0YXJ0SWR4OiBudW1iZXIsXG4gICAgICAgICAgICAgICAgICAgICAgICBlbmRJZHg6IG51bWJlcik6IHZvaWQge1xuICAgIGZvciAoOyBzdGFydElkeCA8PSBlbmRJZHg7ICsrc3RhcnRJZHgpIHtcbiAgICAgIGxldCBpOiBhbnksIGxpc3RlbmVyczogbnVtYmVyLCBybTogKCkgPT4gdm9pZCwgY2ggPSB2bm9kZXNbc3RhcnRJZHhdO1xuICAgICAgaWYgKGlzRGVmKGNoKSkge1xuICAgICAgICBpZiAoaXNEZWYoY2guc2VsKSkge1xuICAgICAgICAgIGludm9rZURlc3Ryb3lIb29rKGNoKTtcbiAgICAgICAgICBsaXN0ZW5lcnMgPSBjYnMucmVtb3ZlLmxlbmd0aCArIDE7XG4gICAgICAgICAgcm0gPSBjcmVhdGVSbUNiKGNoLmVsbSBhcyBOb2RlLCBsaXN0ZW5lcnMpO1xuICAgICAgICAgIGZvciAoaSA9IDA7IGkgPCBjYnMucmVtb3ZlLmxlbmd0aDsgKytpKSBjYnMucmVtb3ZlW2ldKGNoLCBybSk7XG4gICAgICAgICAgaWYgKGlzRGVmKGkgPSBjaC5kYXRhKSAmJiBpc0RlZihpID0gaS5ob29rKSAmJiBpc0RlZihpID0gaS5yZW1vdmUpKSB7XG4gICAgICAgICAgICBpKGNoLCBybSk7XG4gICAgICAgICAgfSBlbHNlIHtcbiAgICAgICAgICAgIHJtKCk7XG4gICAgICAgICAgfVxuICAgICAgICB9IGVsc2UgeyAvLyBUZXh0IG5vZGVcbiAgICAgICAgICBhcGkucmVtb3ZlQ2hpbGQocGFyZW50RWxtLCBjaC5lbG0gYXMgTm9kZSk7XG4gICAgICAgIH1cbiAgICAgIH1cbiAgICB9XG4gIH1cblxuICBmdW5jdGlvbiB1cGRhdGVDaGlsZHJlbihwYXJlbnRFbG06IE5vZGUsXG4gICAgICAgICAgICAgICAgICAgICAgICAgIG9sZENoOiBBcnJheTxWTm9kZT4sXG4gICAgICAgICAgICAgICAgICAgICAgICAgIG5ld0NoOiBBcnJheTxWTm9kZT4sXG4gICAgICAgICAgICAgICAgICAgICAgICAgIGluc2VydGVkVm5vZGVRdWV1ZTogVk5vZGVRdWV1ZSkge1xuICAgIGxldCBvbGRTdGFydElkeCA9IDAsIG5ld1N0YXJ0SWR4ID0gMDtcbiAgICBsZXQgb2xkRW5kSWR4ID0gb2xkQ2gubGVuZ3RoIC0gMTtcbiAgICBsZXQgb2xkU3RhcnRWbm9kZSA9IG9sZENoWzBdO1xuICAgIGxldCBvbGRFbmRWbm9kZSA9IG9sZENoW29sZEVuZElkeF07XG4gICAgbGV0IG5ld0VuZElkeCA9IG5ld0NoLmxlbmd0aCAtIDE7XG4gICAgbGV0IG5ld1N0YXJ0Vm5vZGUgPSBuZXdDaFswXTtcbiAgICBsZXQgbmV3RW5kVm5vZGUgPSBuZXdDaFtuZXdFbmRJZHhdO1xuICAgIGxldCBvbGRLZXlUb0lkeDogYW55O1xuICAgIGxldCBpZHhJbk9sZDogbnVtYmVyO1xuICAgIGxldCBlbG1Ub01vdmU6IFZOb2RlO1xuICAgIGxldCBiZWZvcmU6IGFueTtcblxuICAgIHdoaWxlIChvbGRTdGFydElkeCA8PSBvbGRFbmRJZHggJiYgbmV3U3RhcnRJZHggPD0gbmV3RW5kSWR4KSB7XG4gICAgICBpZiAoaXNVbmRlZihvbGRTdGFydFZub2RlKSkge1xuICAgICAgICBvbGRTdGFydFZub2RlID0gb2xkQ2hbKytvbGRTdGFydElkeF07IC8vIFZub2RlIGhhcyBiZWVuIG1vdmVkIGxlZnRcbiAgICAgIH0gZWxzZSBpZiAoaXNVbmRlZihvbGRFbmRWbm9kZSkpIHtcbiAgICAgICAgb2xkRW5kVm5vZGUgPSBvbGRDaFstLW9sZEVuZElkeF07XG4gICAgICB9IGVsc2UgaWYgKHNhbWVWbm9kZShvbGRTdGFydFZub2RlLCBuZXdTdGFydFZub2RlKSkge1xuICAgICAgICBwYXRjaFZub2RlKG9sZFN0YXJ0Vm5vZGUsIG5ld1N0YXJ0Vm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICAgIG9sZFN0YXJ0Vm5vZGUgPSBvbGRDaFsrK29sZFN0YXJ0SWR4XTtcbiAgICAgICAgbmV3U3RhcnRWbm9kZSA9IG5ld0NoWysrbmV3U3RhcnRJZHhdO1xuICAgICAgfSBlbHNlIGlmIChzYW1lVm5vZGUob2xkRW5kVm5vZGUsIG5ld0VuZFZub2RlKSkge1xuICAgICAgICBwYXRjaFZub2RlKG9sZEVuZFZub2RlLCBuZXdFbmRWbm9kZSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKTtcbiAgICAgICAgb2xkRW5kVm5vZGUgPSBvbGRDaFstLW9sZEVuZElkeF07XG4gICAgICAgIG5ld0VuZFZub2RlID0gbmV3Q2hbLS1uZXdFbmRJZHhdO1xuICAgICAgfSBlbHNlIGlmIChzYW1lVm5vZGUob2xkU3RhcnRWbm9kZSwgbmV3RW5kVm5vZGUpKSB7IC8vIFZub2RlIG1vdmVkIHJpZ2h0XG4gICAgICAgIHBhdGNoVm5vZGUob2xkU3RhcnRWbm9kZSwgbmV3RW5kVm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50RWxtLCBvbGRTdGFydFZub2RlLmVsbSBhcyBOb2RlLCBhcGkubmV4dFNpYmxpbmcob2xkRW5kVm5vZGUuZWxtIGFzIE5vZGUpKTtcbiAgICAgICAgb2xkU3RhcnRWbm9kZSA9IG9sZENoWysrb2xkU3RhcnRJZHhdO1xuICAgICAgICBuZXdFbmRWbm9kZSA9IG5ld0NoWy0tbmV3RW5kSWR4XTtcbiAgICAgIH0gZWxzZSBpZiAoc2FtZVZub2RlKG9sZEVuZFZub2RlLCBuZXdTdGFydFZub2RlKSkgeyAvLyBWbm9kZSBtb3ZlZCBsZWZ0XG4gICAgICAgIHBhdGNoVm5vZGUob2xkRW5kVm5vZGUsIG5ld1N0YXJ0Vm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICAgIGFwaS5pbnNlcnRCZWZvcmUocGFyZW50RWxtLCBvbGRFbmRWbm9kZS5lbG0gYXMgTm9kZSwgb2xkU3RhcnRWbm9kZS5lbG0gYXMgTm9kZSk7XG4gICAgICAgIG9sZEVuZFZub2RlID0gb2xkQ2hbLS1vbGRFbmRJZHhdO1xuICAgICAgICBuZXdTdGFydFZub2RlID0gbmV3Q2hbKytuZXdTdGFydElkeF07XG4gICAgICB9IGVsc2Uge1xuICAgICAgICBpZiAob2xkS2V5VG9JZHggPT09IHVuZGVmaW5lZCkge1xuICAgICAgICAgIG9sZEtleVRvSWR4ID0gY3JlYXRlS2V5VG9PbGRJZHgob2xkQ2gsIG9sZFN0YXJ0SWR4LCBvbGRFbmRJZHgpO1xuICAgICAgICB9XG4gICAgICAgIGlkeEluT2xkID0gb2xkS2V5VG9JZHhbbmV3U3RhcnRWbm9kZS5rZXkgYXMgc3RyaW5nXTtcbiAgICAgICAgaWYgKGlzVW5kZWYoaWR4SW5PbGQpKSB7IC8vIE5ldyBlbGVtZW50XG4gICAgICAgICAgYXBpLmluc2VydEJlZm9yZShwYXJlbnRFbG0sIGNyZWF0ZUVsbShuZXdTdGFydFZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpLCBvbGRTdGFydFZub2RlLmVsbSBhcyBOb2RlKTtcbiAgICAgICAgICBuZXdTdGFydFZub2RlID0gbmV3Q2hbKytuZXdTdGFydElkeF07XG4gICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgZWxtVG9Nb3ZlID0gb2xkQ2hbaWR4SW5PbGRdO1xuICAgICAgICAgIGlmIChlbG1Ub01vdmUuc2VsICE9PSBuZXdTdGFydFZub2RlLnNlbCkge1xuICAgICAgICAgICAgYXBpLmluc2VydEJlZm9yZShwYXJlbnRFbG0sIGNyZWF0ZUVsbShuZXdTdGFydFZub2RlLCBpbnNlcnRlZFZub2RlUXVldWUpLCBvbGRTdGFydFZub2RlLmVsbSBhcyBOb2RlKTtcbiAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgcGF0Y2hWbm9kZShlbG1Ub01vdmUsIG5ld1N0YXJ0Vm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICAgICAgICBvbGRDaFtpZHhJbk9sZF0gPSB1bmRlZmluZWQgYXMgYW55O1xuICAgICAgICAgICAgYXBpLmluc2VydEJlZm9yZShwYXJlbnRFbG0sIChlbG1Ub01vdmUuZWxtIGFzIE5vZGUpLCBvbGRTdGFydFZub2RlLmVsbSBhcyBOb2RlKTtcbiAgICAgICAgICB9XG4gICAgICAgICAgbmV3U3RhcnRWbm9kZSA9IG5ld0NoWysrbmV3U3RhcnRJZHhdO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfVxuICAgIGlmIChvbGRTdGFydElkeCA+IG9sZEVuZElkeCkge1xuICAgICAgYmVmb3JlID0gaXNVbmRlZihuZXdDaFtuZXdFbmRJZHgrMV0pID8gbnVsbCA6IG5ld0NoW25ld0VuZElkeCsxXS5lbG07XG4gICAgICBhZGRWbm9kZXMocGFyZW50RWxtLCBiZWZvcmUsIG5ld0NoLCBuZXdTdGFydElkeCwgbmV3RW5kSWR4LCBpbnNlcnRlZFZub2RlUXVldWUpO1xuICAgIH0gZWxzZSBpZiAobmV3U3RhcnRJZHggPiBuZXdFbmRJZHgpIHtcbiAgICAgIHJlbW92ZVZub2RlcyhwYXJlbnRFbG0sIG9sZENoLCBvbGRTdGFydElkeCwgb2xkRW5kSWR4KTtcbiAgICB9XG4gIH1cblxuICBmdW5jdGlvbiBwYXRjaFZub2RlKG9sZFZub2RlOiBWTm9kZSwgdm5vZGU6IFZOb2RlLCBpbnNlcnRlZFZub2RlUXVldWU6IFZOb2RlUXVldWUpIHtcbiAgICBsZXQgaTogYW55LCBob29rOiBhbnk7XG4gICAgaWYgKGlzRGVmKGkgPSB2bm9kZS5kYXRhKSAmJiBpc0RlZihob29rID0gaS5ob29rKSAmJiBpc0RlZihpID0gaG9vay5wcmVwYXRjaCkpIHtcbiAgICAgIGkob2xkVm5vZGUsIHZub2RlKTtcbiAgICB9XG4gICAgY29uc3QgZWxtID0gdm5vZGUuZWxtID0gKG9sZFZub2RlLmVsbSBhcyBOb2RlKTtcbiAgICBsZXQgb2xkQ2ggPSBvbGRWbm9kZS5jaGlsZHJlbjtcbiAgICBsZXQgY2ggPSB2bm9kZS5jaGlsZHJlbjtcbiAgICBpZiAob2xkVm5vZGUgPT09IHZub2RlKSByZXR1cm47XG4gICAgaWYgKHZub2RlLmRhdGEgIT09IHVuZGVmaW5lZCkge1xuICAgICAgZm9yIChpID0gMDsgaSA8IGNicy51cGRhdGUubGVuZ3RoOyArK2kpIGNicy51cGRhdGVbaV0ob2xkVm5vZGUsIHZub2RlKTtcbiAgICAgIGkgPSB2bm9kZS5kYXRhLmhvb2s7XG4gICAgICBpZiAoaXNEZWYoaSkgJiYgaXNEZWYoaSA9IGkudXBkYXRlKSkgaShvbGRWbm9kZSwgdm5vZGUpO1xuICAgIH1cbiAgICBpZiAoaXNVbmRlZih2bm9kZS50ZXh0KSkge1xuICAgICAgaWYgKGlzRGVmKG9sZENoKSAmJiBpc0RlZihjaCkpIHtcbiAgICAgICAgaWYgKG9sZENoICE9PSBjaCkgdXBkYXRlQ2hpbGRyZW4oZWxtLCBvbGRDaCBhcyBBcnJheTxWTm9kZT4sIGNoIGFzIEFycmF5PFZOb2RlPiwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKTtcbiAgICAgIH0gZWxzZSBpZiAoaXNEZWYoY2gpKSB7XG4gICAgICAgIGlmIChpc0RlZihvbGRWbm9kZS50ZXh0KSkgYXBpLnNldFRleHRDb250ZW50KGVsbSwgJycpO1xuICAgICAgICBhZGRWbm9kZXMoZWxtLCBudWxsLCBjaCBhcyBBcnJheTxWTm9kZT4sIDAsIChjaCBhcyBBcnJheTxWTm9kZT4pLmxlbmd0aCAtIDEsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG4gICAgICB9IGVsc2UgaWYgKGlzRGVmKG9sZENoKSkge1xuICAgICAgICByZW1vdmVWbm9kZXMoZWxtLCBvbGRDaCBhcyBBcnJheTxWTm9kZT4sIDAsIChvbGRDaCBhcyBBcnJheTxWTm9kZT4pLmxlbmd0aCAtIDEpO1xuICAgICAgfSBlbHNlIGlmIChpc0RlZihvbGRWbm9kZS50ZXh0KSkge1xuICAgICAgICBhcGkuc2V0VGV4dENvbnRlbnQoZWxtLCAnJyk7XG4gICAgICB9XG4gICAgfSBlbHNlIGlmIChvbGRWbm9kZS50ZXh0ICE9PSB2bm9kZS50ZXh0KSB7XG4gICAgICBhcGkuc2V0VGV4dENvbnRlbnQoZWxtLCB2bm9kZS50ZXh0IGFzIHN0cmluZyk7XG4gICAgfVxuICAgIGlmIChpc0RlZihob29rKSAmJiBpc0RlZihpID0gaG9vay5wb3N0cGF0Y2gpKSB7XG4gICAgICBpKG9sZFZub2RlLCB2bm9kZSk7XG4gICAgfVxuICB9XG5cbiAgcmV0dXJuIGZ1bmN0aW9uIHBhdGNoKG9sZFZub2RlOiBWTm9kZSB8IEVsZW1lbnQsIHZub2RlOiBWTm9kZSk6IFZOb2RlIHtcbiAgICBsZXQgaTogbnVtYmVyLCBlbG06IE5vZGUsIHBhcmVudDogTm9kZTtcbiAgICBjb25zdCBpbnNlcnRlZFZub2RlUXVldWU6IFZOb2RlUXVldWUgPSBbXTtcbiAgICBmb3IgKGkgPSAwOyBpIDwgY2JzLnByZS5sZW5ndGg7ICsraSkgY2JzLnByZVtpXSgpO1xuXG4gICAgaWYgKCFpc1Zub2RlKG9sZFZub2RlKSkge1xuICAgICAgb2xkVm5vZGUgPSBlbXB0eU5vZGVBdChvbGRWbm9kZSk7XG4gICAgfVxuXG4gICAgaWYgKHNhbWVWbm9kZShvbGRWbm9kZSwgdm5vZGUpKSB7XG4gICAgICBwYXRjaFZub2RlKG9sZFZub2RlLCB2bm9kZSwgaW5zZXJ0ZWRWbm9kZVF1ZXVlKTtcbiAgICB9IGVsc2Uge1xuICAgICAgZWxtID0gb2xkVm5vZGUuZWxtIGFzIE5vZGU7XG4gICAgICBwYXJlbnQgPSBhcGkucGFyZW50Tm9kZShlbG0pO1xuXG4gICAgICBjcmVhdGVFbG0odm5vZGUsIGluc2VydGVkVm5vZGVRdWV1ZSk7XG5cbiAgICAgIGlmIChwYXJlbnQgIT09IG51bGwpIHtcbiAgICAgICAgYXBpLmluc2VydEJlZm9yZShwYXJlbnQsIHZub2RlLmVsbSBhcyBOb2RlLCBhcGkubmV4dFNpYmxpbmcoZWxtKSk7XG4gICAgICAgIHJlbW92ZVZub2RlcyhwYXJlbnQsIFtvbGRWbm9kZV0sIDAsIDApO1xuICAgICAgfVxuICAgIH1cblxuICAgIGZvciAoaSA9IDA7IGkgPCBpbnNlcnRlZFZub2RlUXVldWUubGVuZ3RoOyArK2kpIHtcbiAgICAgICgoKGluc2VydGVkVm5vZGVRdWV1ZVtpXS5kYXRhIGFzIFZOb2RlRGF0YSkuaG9vayBhcyBIb29rcykuaW5zZXJ0IGFzIGFueSkoaW5zZXJ0ZWRWbm9kZVF1ZXVlW2ldKTtcbiAgICB9XG4gICAgZm9yIChpID0gMDsgaSA8IGNicy5wb3N0Lmxlbmd0aDsgKytpKSBjYnMucG9zdFtpXSgpO1xuICAgIHJldHVybiB2bm9kZTtcbiAgfTtcbn1cbiIsImltcG9ydCB7Vk5vZGUsIFZOb2RlRGF0YX0gZnJvbSAnLi92bm9kZSc7XG5pbXBvcnQge2h9IGZyb20gJy4vaCc7XG5cbmV4cG9ydCBpbnRlcmZhY2UgVGh1bmtEYXRhIGV4dGVuZHMgVk5vZGVEYXRhIHtcbiAgZm46ICgpID0+IFZOb2RlO1xuICBhcmdzOiBBcnJheTxhbnk+O1xufVxuXG5leHBvcnQgaW50ZXJmYWNlIFRodW5rIGV4dGVuZHMgVk5vZGUge1xuICBkYXRhOiBUaHVua0RhdGE7XG59XG5cbmV4cG9ydCBpbnRlcmZhY2UgVGh1bmtGbiB7XG4gIChzZWw6IHN0cmluZywgZm46IEZ1bmN0aW9uLCBhcmdzOiBBcnJheTxhbnk+KTogVGh1bms7XG4gIChzZWw6IHN0cmluZywga2V5OiBhbnksIGZuOiBGdW5jdGlvbiwgYXJnczogQXJyYXk8YW55Pik6IFRodW5rO1xufVxuXG5mdW5jdGlvbiBjb3B5VG9UaHVuayh2bm9kZTogVk5vZGUsIHRodW5rOiBWTm9kZSk6IHZvaWQge1xuICB0aHVuay5lbG0gPSB2bm9kZS5lbG07XG4gICh2bm9kZS5kYXRhIGFzIFZOb2RlRGF0YSkuZm4gPSAodGh1bmsuZGF0YSBhcyBWTm9kZURhdGEpLmZuO1xuICAodm5vZGUuZGF0YSBhcyBWTm9kZURhdGEpLmFyZ3MgPSAodGh1bmsuZGF0YSBhcyBWTm9kZURhdGEpLmFyZ3M7XG4gIHRodW5rLmRhdGEgPSB2bm9kZS5kYXRhO1xuICB0aHVuay5jaGlsZHJlbiA9IHZub2RlLmNoaWxkcmVuO1xuICB0aHVuay50ZXh0ID0gdm5vZGUudGV4dDtcbiAgdGh1bmsuZWxtID0gdm5vZGUuZWxtO1xufVxuXG5mdW5jdGlvbiBpbml0KHRodW5rOiBWTm9kZSk6IHZvaWQge1xuICBjb25zdCBjdXIgPSB0aHVuay5kYXRhIGFzIFZOb2RlRGF0YTtcbiAgY29uc3Qgdm5vZGUgPSAoY3VyLmZuIGFzIGFueSkuYXBwbHkodW5kZWZpbmVkLCBjdXIuYXJncyk7XG4gIGNvcHlUb1RodW5rKHZub2RlLCB0aHVuayk7XG59XG5cbmZ1bmN0aW9uIHByZXBhdGNoKG9sZFZub2RlOiBWTm9kZSwgdGh1bms6IFZOb2RlKTogdm9pZCB7XG4gIGxldCBpOiBudW1iZXIsIG9sZCA9IG9sZFZub2RlLmRhdGEgYXMgVk5vZGVEYXRhLCBjdXIgPSB0aHVuay5kYXRhIGFzIFZOb2RlRGF0YTtcbiAgY29uc3Qgb2xkQXJncyA9IG9sZC5hcmdzLCBhcmdzID0gY3VyLmFyZ3M7XG4gIGlmIChvbGQuZm4gIT09IGN1ci5mbiB8fCAob2xkQXJncyBhcyBhbnkpLmxlbmd0aCAhPT0gKGFyZ3MgYXMgYW55KS5sZW5ndGgpIHtcbiAgICBjb3B5VG9UaHVuaygoY3VyLmZuIGFzIGFueSkuYXBwbHkodW5kZWZpbmVkLCBhcmdzKSwgdGh1bmspO1xuICB9XG4gIGZvciAoaSA9IDA7IGkgPCAoYXJncyBhcyBhbnkpLmxlbmd0aDsgKytpKSB7XG4gICAgaWYgKChvbGRBcmdzIGFzIGFueSlbaV0gIT09IChhcmdzIGFzIGFueSlbaV0pIHtcbiAgICAgIGNvcHlUb1RodW5rKChjdXIuZm4gYXMgYW55KS5hcHBseSh1bmRlZmluZWQsIGFyZ3MpLCB0aHVuayk7XG4gICAgICByZXR1cm47XG4gICAgfVxuICB9XG4gIGNvcHlUb1RodW5rKG9sZFZub2RlLCB0aHVuayk7XG59XG5cbmV4cG9ydCBjb25zdCB0aHVuayA9IGZ1bmN0aW9uIHRodW5rKHNlbDogc3RyaW5nLCBrZXk/OiBhbnksIGZuPzogYW55LCBhcmdzPzogYW55KTogVk5vZGUge1xuICBpZiAoYXJncyA9PT0gdW5kZWZpbmVkKSB7XG4gICAgYXJncyA9IGZuO1xuICAgIGZuID0ga2V5O1xuICAgIGtleSA9IHVuZGVmaW5lZDtcbiAgfVxuICByZXR1cm4gaChzZWwsIHtcbiAgICBrZXk6IGtleSxcbiAgICBob29rOiB7aW5pdDogaW5pdCwgcHJlcGF0Y2g6IHByZXBhdGNofSxcbiAgICBmbjogZm4sXG4gICAgYXJnczogYXJnc1xuICB9KTtcbn0gYXMgVGh1bmtGbjtcblxuZXhwb3J0IGRlZmF1bHQgdGh1bms7IiwiaW1wb3J0IHtIb29rc30gZnJvbSAnLi9ob29rcyc7XG5cbmV4cG9ydCB0eXBlIEtleSA9IHN0cmluZyB8IG51bWJlcjtcblxuZXhwb3J0IGludGVyZmFjZSBWTm9kZSB7XG4gIHNlbDogc3RyaW5nIHwgdW5kZWZpbmVkO1xuICBkYXRhOiBWTm9kZURhdGEgfCB1bmRlZmluZWQ7XG4gIGNoaWxkcmVuOiBBcnJheTxWTm9kZSB8IHN0cmluZz4gfCB1bmRlZmluZWQ7XG4gIGVsbTogTm9kZSB8IHVuZGVmaW5lZDtcbiAgdGV4dDogc3RyaW5nIHwgdW5kZWZpbmVkO1xuICBrZXk6IEtleTtcbn1cblxuZXhwb3J0IGludGVyZmFjZSBWTm9kZURhdGEge1xuICAvLyBtb2R1bGVzIC0gdXNlIGFueSBiZWNhdXNlIE9iamVjdCB0eXBlIGlzIHVzZWxlc3NcbiAgcHJvcHM/OiBhbnk7XG4gIGF0dHJzPzogYW55O1xuICBjbGFzcz86IGFueTtcbiAgc3R5bGU/OiBhbnk7XG4gIGRhdGFzZXQ/OiBhbnk7XG4gIG9uPzogYW55O1xuICBoZXJvPzogYW55O1xuICBhdHRhY2hEYXRhPzogYW55O1xuICBob29rPzogSG9va3M7XG4gIGtleT86IEtleTtcbiAgbnM/OiBzdHJpbmc7IC8vIGZvciBTVkdzXG4gIGZuPzogKCkgPT4gVk5vZGU7IC8vIGZvciB0aHVua3NcbiAgYXJncz86IEFycmF5PGFueT47IC8vIGZvciB0aHVua3NcbiAgW2tleTogc3RyaW5nXTogYW55OyAvLyBmb3IgYW55IG90aGVyIDNyZCBwYXJ0eSBtb2R1bGVcbiAgLy8gZW5kIG9mIG1vZHVsZXNcbn1cblxuZXhwb3J0IGZ1bmN0aW9uIHZub2RlKHNlbDogc3RyaW5nLFxuICAgICAgICAgICAgICAgZGF0YTogYW55IHwgdW5kZWZpbmVkLFxuICAgICAgICAgICAgICAgY2hpbGRyZW46IEFycmF5PFZOb2RlIHwgc3RyaW5nPiB8IHVuZGVmaW5lZCxcbiAgICAgICAgICAgICAgIHRleHQ6IHN0cmluZyB8IHVuZGVmaW5lZCxcbiAgICAgICAgICAgICAgIGVsbTogRWxlbWVudCB8IFRleHQgfCB1bmRlZmluZWQpOiBWTm9kZSB7XG4gIGxldCBrZXkgPSBkYXRhID09PSB1bmRlZmluZWQgPyB1bmRlZmluZWQgOiBkYXRhLmtleTtcbiAgcmV0dXJuIHtzZWw6IHNlbCwgZGF0YTogZGF0YSwgY2hpbGRyZW46IGNoaWxkcmVuLFxuICAgICAgICAgIHRleHQ6IHRleHQsIGVsbTogZWxtLCBrZXk6IGtleX07XG59XG5cbmV4cG9ydCBkZWZhdWx0IHZub2RlO1xuIiwiXCJ1c2Ugc3RyaWN0XCI7XG5cbmltcG9ydCB7aW5pdCBhcyBzbmFiYmRvbUluaXR9IGZyb20gJy4vbGliL3NuYWJiZG9tL3NyYy9zbmFiYmRvbSc7XG5pbXBvcnQge2NsYXNzTW9kdWxlfSBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvbW9kdWxlcy9jbGFzcyc7XG5pbXBvcnQge3Byb3BzTW9kdWxlfSBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvbW9kdWxlcy9wcm9wcyc7XG5pbXBvcnQge3N0eWxlTW9kdWxlfSBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvbW9kdWxlcy9zdHlsZSc7XG5pbXBvcnQge2V2ZW50TGlzdGVuZXJzTW9kdWxlfSBmcm9tICcuL2xpYi9zbmFiYmRvbS9zcmMvbW9kdWxlcy9ldmVudGxpc3RlbmVycyc7XG5pbXBvcnQge1ZOb2RlfSBmcm9tIFwiLi9saWIvc25hYmJkb20vc3JjL3Zub2RlXCI7XG5cbmltcG9ydCB7dmlldyBhcyB2aWV3Q291bnRlckxpc3QsIHVwZGF0ZSBhcyB1cGRhdGVDb3VudGVyTGlzdCwgTW9kZWwgYXMgQ291bnRlckxpc3RNb2RlbCwgQWN0aW9uIGFzIENvdW50ZXJMaXN0QWN0aW9uLCBBY3Rpb25IYW5kbGVyIGFzIENvdW50ZXJMaXN0QWN0aW9uSGFuZGxlcn0gZnJvbSAnLi9jb3VudGVyTGlzdCc7XG5cbi8vIEdldCBhIFNuYWJiZG9tIHBhdGNoIGZ1bmN0aW9uIHdpdGggdGhlIG5vcm1hbCBIVE1MIG1vZHVsZXMuXG5jb25zdCBwYXRjaCA9IHNuYWJiZG9tSW5pdChcbiAgICBbXG4gICAgICAgIGNsYXNzTW9kdWxlLFxuICAgICAgICBwcm9wc01vZHVsZSxcbiAgICAgICAgc3R5bGVNb2R1bGUsXG4gICAgICAgIGV2ZW50TGlzdGVuZXJzTW9kdWxlXG4gICAgXVxuKTtcblxuLy8gdmlldyBmdW5jdGlvbiB0eXBlXG50eXBlIFZpZXdGdW5jdGlvbiA9ICggbW9kZWwgOiBDb3VudGVyTGlzdE1vZGVsLCBoYW5kbGVyIDogQ291bnRlckxpc3RBY3Rpb25IYW5kbGVyICkgPT4gVk5vZGU7XG5cbnR5cGUgVXBkYXRlRnVuY3Rpb24gPSAoIG1vZGVsIDogQ291bnRlckxpc3RNb2RlbCwgYWN0aW9uIDogQ291bnRlckxpc3RBY3Rpb24gKSA9PiBDb3VudGVyTGlzdE1vZGVsO1xuXG4vKipcbiAqIFJ1bnMgb25lIGN5Y2xlIG9mIHRoZSBTbmFiYmRvbSBldmVudCBsb29wLlxuICogQHBhcmFtIHN0YXRlIHRoZSBsYXRlc3QgbW9kZWwgc3RhdGUuXG4gKiBAcGFyYW0gb2xkVm5vZGUgdGhlIHByaW9yIHZpcnR1YWwgRE9NIG9yIHRoZSByZWFsIERPTSBmaXJzdCB0aW1lIHRocm91Z2guXG4gKiBAcGFyYW0gdmlldyB0aGUgZnVuY3Rpb24gdG8gY29tcHV0ZSB0aGUgbmV3IHZpcnR1YWwgRE9NIGZyb20gdGhlIG1vZGVsLlxuICogQHBhcmFtIHVwZGF0ZSB0aGUgZnVuY3Rpb24gdG8gY29tcHV0ZSB0aGUgbmV3IG1vZGVsIHN0YXRlIGZyb20gdGhlIG9sZCBzdGF0ZSBhbmQgYSBwZW5kaW5nIGFjdGlvbi5cbiAqL1xuZnVuY3Rpb24gbWFpbiggc3RhdGU6IENvdW50ZXJMaXN0TW9kZWwsIG9sZFZub2RlIDogVk5vZGUgfCBFbGVtZW50LCB2aWV3IDogVmlld0Z1bmN0aW9uLCB1cGRhdGUgOiBVcGRhdGVGdW5jdGlvbiApIDogdm9pZCB7XG5cbiAgICBsZXQgZXZlbnRIYW5kbGVyID0gKCBhY3Rpb24gOiBDb3VudGVyTGlzdEFjdGlvbiApID0+IHtcbiAgICAgICAgY29uc3QgbmV3U3RhdGUgPSB1cGRhdGUoIHN0YXRlLCBhY3Rpb24gKTtcbiAgICAgICAgbWFpbiggbmV3U3RhdGUsIG5ld1Zub2RlLCB2aWV3LCB1cGRhdGUgKTtcbiAgICB9O1xuXG4gICAgY29uc3QgbmV3Vm5vZGUgPSB2aWV3KCBzdGF0ZSwgZXZlbnRIYW5kbGVyICk7XG5cbiAgICBwYXRjaCggb2xkVm5vZGUsIG5ld1Zub2RlICk7XG5cbn1cblxuLyoqXG4gKiBJbml0aWFsaXplcyB0aGUgYXBwbGljYXRpb24gc3RhdGUuXG4gKiBAcmV0dXJucyB7e25leHRJRDogbnVtYmVyLCBjb3VudGVyczogQXJyYXl9fVxuICovXG5mdW5jdGlvbiBpbml0U3RhdGUoKSA6IENvdW50ZXJMaXN0TW9kZWwge1xuICAgIHJldHVybiB7IG5leHRJRDogNSwgY291bnRlcnM6IFtdIH07XG59XG5cbi8vIC0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLVxuXG4vLyBGaW5kIHRoZSBET00gbm9kZSB0byBkcm9wIG91ciBhcHAgaW4uXG5sZXQgZG9tTm9kZSA9IGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCAnYXBwJyApO1xuXG5pZiAoIGRvbU5vZGUgPT0gbnVsbCApIHtcbiAgICAvLyBBYmFuZG9uIGhvcGUgaWYgdGhlIHJlYWwgRE9NIG5vZGUgaXMgbm90IGZvdW5kLlxuICAgIGNvbnNvbGUubG9nKCBcIkNhbm5vdCBmaW5kIGFwcGxpY2F0aW9uIERPTSBub2RlLlwiICk7XG59XG5lbHNlIHtcbiAgICAvLyBGaXJlIG9mZiB0aGUgZmlyc3QgbG9vcCBvZiB0aGUgbGlmZWN5Y2xlLlxuICAgIG1haW4oIGluaXRTdGF0ZSgpLCBkb21Ob2RlLCB2aWV3Q291bnRlckxpc3QsIHVwZGF0ZUNvdW50ZXJMaXN0ICk7XG59XG4iXX0=
