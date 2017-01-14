
module App exposing(..)

import Html exposing (Html, br, button, div, main_, text)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)



-- MODEL


type alias Model = Int


model : Model
model =
  0



-- UPDATE


type Msg
  = Increment
  | Decrement


update : Msg -> Model -> Model
update msg model =
  case msg of
    Increment ->
      model + 1

    Decrement ->
      model - 1



-- VIEW


view : Model -> Html Msg
view model =

  main_ [ class "o-grid o-grid--no-gutter o-panel" ] [

    div [ class "o-grid__cell--width-100 o-panel-container" ] [

      div [ class "o-grid o-panel o-panel--nav-top" ] [

        div [ class "o-grid__cell o-grid__cell--width-30" ] [
          text "Left Panel",
          br [] [],

          -- Counter sample code
          div [] [
            button [ onClick Decrement ] [ text "-" ],
            div [] [ text (toString model) ],
            button [ onClick Increment ] [ text "+" ]
          ]
        ],

        div [ class "o-grid__cell o-grid__cell--width-70" ] [
          text "Right Panel"
        ]

      ]

    ]

  ]



-- MAIN PROGRAM

main =
  Html.beginnerProgram
    { model = model
    , view = view
    , update = update
    }


