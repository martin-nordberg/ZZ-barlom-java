#!/usr/bin/env bash


while [ 1 ]; do

    # Reformat all the Elm code.
    ./elm-format ../src/ --yes

    # Rebuild the entire application.
    elm-make ../src/Barlom/Presentation/Main/App.elm --output ../webapp/scripts/app.js

    # Repeat until q key pressed.
    echo "Press any key to rebuild, or q to quit."
    read key

    if [ "$key" == "q" ]; then break; fi

done