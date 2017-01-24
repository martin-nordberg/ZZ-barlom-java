module Barlom.Presentation.Browsing.VertexType.Form exposing (Msg, view)

import Barlom.Domain.Repository exposing (Entities, VertexType)
import Barlom.Presentation.Browsing.VertexType.Types exposing (FocusedVertexType)
import Html exposing (Html, br, div, form, input, label, text)
import Html.Attributes exposing (class, classList, for, id, value)


--------------
-- MESSAGES --
--------------
--
-- Message for focusing a vertex type.


type Msg
    = EditVertexType (Maybe VertexType)



----------
-- VIEW --
----------
--
-- Generates mark up for a form to edit a vertex type.


view : FocusedVertexType -> Html Msg
view focusedVertexType =
    let
        ( vtNameInputId, vtSummaryInputId ) =
            ( "vt-name-input", "vt-summary-input" )
    in
        case focusedVertexType of
            Just vertexType ->
                form []
                    [ label [ for vtNameInputId ] [ text "Name:" ]
                    , br [] []
                    , input [ (id vtNameInputId), (value vertexType.name) ] []
                    , br [] []
                    , label [ for vtSummaryInputId ] [ text "Summary:" ]
                    , br [] []
                    , input [ (id vtSummaryInputId), (value vertexType.summary) ] []
                    ]

            Nothing ->
                div [] []



------------
-- UPDATE --
------------
--
-- TODO: save changes.
