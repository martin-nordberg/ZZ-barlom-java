module Barlom.Presentation.Routing.Routes exposing (..)

import Navigation exposing (Location)
import UrlParser exposing (Parser, (</>), s, int, string, map, oneOf, parseHash, top)


type Route
    = BrowseRoute
    | DiagramsRoute
    | NotFoundRoute


routeMatchers : Parser (Route -> a) a
routeMatchers =
    oneOf
        [ map BrowseRoute top
        , map BrowseRoute (s "browse")
        , map DiagramsRoute (s "diagrams")
        ]


parseLocation : Location -> Route
parseLocation location =
    case (parseHash routeMatchers location) of
        Just route ->
            route

        Nothing ->
            NotFoundRoute
