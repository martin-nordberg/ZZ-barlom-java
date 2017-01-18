#!/usr/bin/env bash
./elm-format ../src/ --yes
elm-make ../src/Barlom/Presentation/Main/App.elm --output ../webapp/scripts/app.js
