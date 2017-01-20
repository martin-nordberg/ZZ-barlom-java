module Barlom.Presentation.Browsing.VertexTypeGrid exposing (Msg, view)

import Barlom.Domain.Repository exposing (Entities, VertexType)
import Html exposing (Html, button, div, table, tbody, td, text, th, thead, tr)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)


-- MESSAGES


type Msg
    = SelectVertexType



-- VIEW


view : Entities -> Html Msg
view entities =
    table [ class "c-table" ]
        [ thead [ class "c-table__head" ]
            [ tr [ class "c-table__row c-table__row--heading" ] [ th [ class "c-table__cell" ] [ text "Name" ] ]
            ]
        , tbody [] (List.map viewVertexTypeRow entities.vertexTypesList)
        ]


viewVertexTypeRow : VertexType -> Html Msg
viewVertexTypeRow vertexType =
    tr [ class "c-table__row" ]
        [ td [ class "c-table__cell" ] [ text vertexType.name ]
        ]



-- UPDATE
--update : Msg -> Model -> ( Model, Cmd Msg )
--update message model =
--    case message of
--        Increase ->
--            ( { model | count = model.count + 1 }, Cmd.none )
