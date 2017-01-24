module Barlom.Presentation.Routing.Routes exposing (..)

import Navigation exposing (Location)
import UrlParser exposing (Parser, (</>), s, int, string, map, oneOf, parseHash, top)


-- The defined routes for the whole application.


type Route
    = BrowseRoute
    | DiagramsRoute
    | NotFoundRoute



-- Converts a hash tag string into a route.


routeMatchers : Parser (Route -> a) a
routeMatchers =
    oneOf
        [ map BrowseRoute top
        , map BrowseRoute (s "browse")
        , map DiagramsRoute (s "diagrams")
        ]



-- Parses the route out of a URL.


parseLocation : Location -> Route
parseLocation location =
    case (parseHash routeMatchers location) of
        Just route ->
            route

        Nothing ->
            NotFoundRoute
