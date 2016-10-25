#!/bin/sh

tsc --module AMD --outDir ../js-gen --sourcemap --target ES6 --watch allmodules.ts
