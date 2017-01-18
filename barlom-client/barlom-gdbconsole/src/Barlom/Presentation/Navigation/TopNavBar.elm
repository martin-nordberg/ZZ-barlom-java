module Barlom.Presentation.Navigation.TopNavBar exposing (..)

import Html exposing (Html, div, nav, span, text)
import Html.Attributes exposing (class, classList)
import Html.Events exposing (onClick)
import Navigation
import Barlom.Presentation.Routing.Routes as Routes exposing (Route)


-- MESSAGES


type Msg
    = ActivateBrowse
    | ActivateDiagrams
    | Exit



-- VIEW


view : Route -> Html Msg
view currentRoute =
    nav [ class "c-nav c-nav--inline c-nav--high" ]
        [ div [ class "c-nav__content" ] [ text "Barlom-GDB Console" ]
        , viewNavItem currentRoute "Browse" Routes.BrowseRoute ActivateBrowse
        , viewNavItem currentRoute "Diagrams" Routes.DiagramsRoute ActivateDiagrams
        , span
            [ class "c-nav__item c-nav__item--right"
            , onClick Exit
            ]
            [ text "Exit" ]
        ]


viewNavItem : Route -> String -> Route -> Msg -> Html Msg
viewNavItem currentRoute title route msg =
    span
        [ classList [ ( "c-nav__item", True ), ( "c-nav__item--active", currentRoute == route ) ]
        , onClick msg
        ]
        [ text title ]



-- UPDATE


update : Msg -> Route -> ( Route, Cmd Msg )
update message route =
    case message of
        ActivateBrowse ->
            ( route, Navigation.newUrl "#browse" )

        ActivateDiagrams ->
            ( route, Navigation.newUrl "#diagrams" )

        Exit ->
            ( route, Navigation.modifyUrl "exit" )
