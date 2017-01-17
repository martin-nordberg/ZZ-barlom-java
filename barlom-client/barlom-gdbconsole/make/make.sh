#!/usr/bin/env bash
./elm-format ../src/ --yes
elm-make ../src/App.elm --output ../webapp/scripts/app.js
