#!/usr/bin/env bash


while [ 1 ]; do

    browserify webapp/src/main.js -o webapp/scripts/app.js -t babelify --debug

    # Repeat until q key pressed.
    echo "Press any key to rebuild, or q to quit."
    read key

    if [ "$key" == "q" ]; then break; fi

done