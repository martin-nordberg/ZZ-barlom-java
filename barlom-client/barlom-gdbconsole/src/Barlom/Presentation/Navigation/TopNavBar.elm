module Barlom.Presentation.Navigation.TopNavBar exposing (..)

import Html exposing (Html, div, nav, span, text)
import Html.Attributes exposing (class, classList)
import Html.Events exposing (onClick)
import Navigation
import Barlom.Presentation.Routing.Routes as Routes exposing (Route)


--------------
-- MESSAGES --
--------------
-- Messages that trigger routing changes.


type Msg
    = ActivateBrowse
    | ActivateDiagrams
    | Exit



----------
-- VIEW --
----------
-- Generates markup for the top nav bar.


view : Route -> Html Msg
view currentRoute =
    nav [ class "c-nav c-nav--inline c-nav--high" ]
        [ div [ class "c-nav__content" ] [ text "Barlom-GDB Console" ]
        , viewNavItem currentRoute "Browse" Routes.BrowseRoute ActivateBrowse
        , viewNavItem currentRoute "Diagrams" Routes.DiagramsRoute ActivateDiagrams
        , viewExitNavItem
        ]



-- The markup for the Exit link on the right side of the top nav bar.


viewExitNavItem : Html Msg
viewExitNavItem =
    span
        [ class "c-nav__item c-nav__item--right"
        , onClick Exit
        ]
        [ text "Exit" ]



-- Generates markup for a single top nav item with given text, route, and on-click message.


viewNavItem : Route -> String -> Route -> Msg -> Html Msg
viewNavItem currentRoute title route msg =
    span
        [ classList [ ( "c-nav__item", True ), ( "c-nav__item--active", currentRoute == route ) ]
        , onClick msg
        ]
        [ text title ]



------------
-- UPDATE --
------------
-- Triggers navigation via routing for given nav item message.


update : Msg -> Route -> ( Route, Cmd Msg )
update message route =
    case message of
        ActivateBrowse ->
            ( route, Navigation.newUrl "#browse" )

        ActivateDiagrams ->
            ( route, Navigation.newUrl "#diagrams" )

        Exit ->
            ( route, Navigation.newUrl "exit" )
