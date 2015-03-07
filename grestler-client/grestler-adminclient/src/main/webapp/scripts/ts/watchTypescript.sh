#!/bin/sh

tsc --module AMD --outDir ../js-gen --sourcemap --target ES5 --watch @tsfiles.txt
