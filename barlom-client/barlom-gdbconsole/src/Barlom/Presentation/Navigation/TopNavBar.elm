module Barlom.Presentation.Navigation.TopNavBar exposing (..)

import Html exposing (Html, div, nav, span, text)
import Html.Attributes exposing (class, classList)
import Html.Events exposing (onClick)


-- MODEL


type Item
    = Browse
    | Diagrams


type alias Model =
    { activeItem : Item }


initialModel : Model
initialModel =
    { activeItem = Browse
    }



-- MESSAGES


type Msg
    = ActivateBrowse
    | ActivateDiagrams
    | Exit



-- VIEW


view : Model -> Html Msg
view model =
    nav [ class "c-nav c-nav--inline c-nav--high" ]
        [ div [ class "c-nav__content" ] [ text "Barlom-GDB Console" ]
        , viewNavItem model "Browse" Browse ActivateBrowse
        , viewNavItem model "Diagrams" Diagrams ActivateDiagrams
        , span
            [ class "c-nav__item c-nav__item--right"
            , onClick Exit
            ]
            [ text "Exit" ]
        ]


viewNavItem : Model -> String -> Item -> Msg -> Html Msg
viewNavItem model title item msg =
    span
        [ classList [ ( "c-nav__item", True ), ( "c-nav__item--active", model.activeItem == item ) ]
        , onClick msg
        ]
        [ text title ]



-- UPDATE


update : Msg -> Model -> ( Model, Cmd Msg )
update message model =
    case message of
        ActivateBrowse ->
            ( { model | activeItem = Browse }, Cmd.none )

        ActivateDiagrams ->
            ( { model | activeItem = Diagrams }, Cmd.none )

        Exit ->
            ( model, Cmd.none )
