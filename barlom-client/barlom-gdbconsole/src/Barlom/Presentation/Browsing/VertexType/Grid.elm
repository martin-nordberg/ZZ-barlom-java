module Barlom.Presentation.Browsing.VertexType.Grid exposing (Msg, update, view)

import Barlom.Domain.Repository exposing (Entities, VertexType)
import Barlom.Presentation.Browsing.VertexType.Types exposing (FocusedVertexType)
import Html exposing (Html, button, div, table, tbody, td, text, th, thead, tr)
import Html.Attributes exposing (class, classList)
import Html.Events exposing (onClick)


--------------
-- MESSAGES --
--------------
--
-- Message for focusing a vertex type.


type Msg
    = FocusVertexType (Maybe VertexType)



----------
-- VIEW --
----------
--
-- Generates mark up for a grid of vertex types.


view : Entities -> FocusedVertexType -> Html Msg
view entities focusedVertexType =
    table [ class "c-table c-table--clickable c-table--condensed" ]
        [ tbody [] (List.map (viewVertexTypeRow focusedVertexType) entities.vertexTypesList) ]



-- Generates mark up for one vertex type row. Includes click handler to focus that vertex type.


viewVertexTypeRow : FocusedVertexType -> VertexType -> Html Msg
viewVertexTypeRow focusedVertexType vertexType =
    let
        isfocused =
            case focusedVertexType of
                Just selVertexType ->
                    selVertexType == vertexType

                Nothing ->
                    False
    in
        tr [ class "c-table__row" ]
            [ td
                [ classList [ ( "c-table__cell", True ), ( "c-text--loud", isfocused ) ]
                , (onClick (FocusVertexType (Just vertexType)))
                ]
                [ text vertexType.name
                ]
            ]



------------
-- UPDATE --
------------
--
-- Updates the focused vertex type.


update : Msg -> ( FocusedVertexType, Cmd Msg )
update message =
    case message of
        FocusVertexType vertexType ->
            ( vertexType, Cmd.none )
