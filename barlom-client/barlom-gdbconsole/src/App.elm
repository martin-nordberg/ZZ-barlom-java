
module App exposing(..)

import Html exposing (Html, br, button, div, main_, program, text)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)

import Counter
import Navigation.TopNavBar


-- MODEL

type alias AppModel =
    {
        activeItem : Navigation.TopNavBar.Model,
        counterModel : Counter.Model
    }


initialModel : AppModel
initialModel =
    {
        activeItem = Navigation.TopNavBar.initialModel,
        counterModel = Counter.initialModel
    }


init : ( AppModel, Cmd Msg )
init =
    ( initialModel, Cmd.none )


-- MESSAGES

type Msg =
    CounterMsg Counter.Msg
    | TopNavBarMsg Navigation.TopNavBar.Msg


-- UPDATE

update : Msg -> AppModel -> ( AppModel, Cmd Msg )
update message model =
    case message of
        CounterMsg subMsg ->
            let
                ( updatedCounterModel, counterCmd ) =
                    Counter.update subMsg model.counterModel
            in
                ( { model | counterModel = updatedCounterModel }, Cmd.map CounterMsg counterCmd )
        TopNavBarMsg subMsg ->
            let
                ( updatedTopNavModel, topNavCmd ) =
                    Navigation.TopNavBar.update subMsg model.activeItem
            in
                ( { model | activeItem = updatedTopNavModel }, Cmd.map TopNavBarMsg topNavCmd )


-- SUBSCRIPTIONS

subscriptions : AppModel -> Sub Msg
subscriptions model =
    Sub.none


-- VIEW

view : AppModel -> Html Msg
view model =

  main_ [ class "o-grid o-grid--no-gutter o-panel" ] [

    div [ class "o-grid__cell--width-100 o-panel-container" ] [

      Html.map TopNavBarMsg (Navigation.TopNavBar.view model.activeItem),

      div [ class "o-grid o-panel o-panel--nav-top" ] [

        div [ class "o-grid__cell o-grid__cell--width-30" ] [
          text "Left Panel",
          br [] [],

          -- Counter sample code
          div [] [
            Html.map CounterMsg (Counter.view model.counterModel)
          ]
        ],

        div [ class "o-grid__cell o-grid__cell--width-70" ] [
          text "Right Panel"
        ]

      ]

    ]

  ]


-- MAIN PROGRAM

main : Program Never AppModel Msg
main =
    program
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }


