module Barlom.Presentation.Main.App exposing (..)

import Html exposing (Html, br, button, div, input, label, main_, program, text)
import Html.Attributes exposing (class, for, id, type_)
import Html.Events exposing (onClick)
import Navigation exposing (Location)
import Barlom.Presentation.Navigation.TopNavBar as TopNavBar
import Barlom.Presentation.Routing.Routes as Routes exposing (Route)
import Barlom.Domain.Repository as Repository exposing (Entities)
import Barlom.Persistence.VertexTypeLoader as VertexTypeLoader
import Barlom.Presentation.Browsing.VertexTypeGrid as VertexTypeGrid


-- MODEL


type alias UiState =
    { currentRoute : Route }


type alias AppModel =
    { uiState : UiState
    , entities : Entities
    }


initialModel : Route -> AppModel
initialModel route =
    { uiState =
        { currentRoute = route
        }
    , entities = Repository.emptyRepository
    }



-- MESSAGES


type Msg
    = OnLocationChange Location
    | TopNavBarMsg TopNavBar.Msg
    | VertexTypeGridMsg VertexTypeGrid.Msg
    | VertexTypeLoaderMsg VertexTypeLoader.Msg



-- UPDATE


update : Msg -> AppModel -> ( AppModel, Cmd Msg )
update message model =
    let
        oldUiState =
            model.uiState
    in
        case message of
            OnLocationChange location ->
                let
                    newRoute =
                        Routes.parseLocation location
                in
                    ( { model | uiState = { oldUiState | currentRoute = newRoute } }, Cmd.none )

            TopNavBarMsg subMsg ->
                let
                    ( updatedRoute, topNavCmd ) =
                        TopNavBar.update subMsg model.uiState.currentRoute
                in
                    ( { model | uiState = { oldUiState | currentRoute = updatedRoute } }, Cmd.map TopNavBarMsg topNavCmd )

            VertexTypeGridMsg subMsg ->
                ( model, Cmd.none )

            VertexTypeLoaderMsg subMsg ->
                let
                    ( updatedEntities, loadCmd ) =
                        VertexTypeLoader.update subMsg model.entities
                in
                    ( { model | entities = updatedEntities }, Cmd.map VertexTypeLoaderMsg loadCmd )



-- SUBSCRIPTIONS


subscriptions : AppModel -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view : AppModel -> Html Msg
view model =
    main_ [ class "o-grid o-grid--no-gutter o-panel" ]
        [ div [ class "o-grid__cell--width-100 o-panel-container" ]
            [ Html.map TopNavBarMsg (TopNavBar.view model.uiState.currentRoute)
            , div [ class "o-grid o-panel o-panel--nav-top" ]
                [ div [ class "o-grid__cell o-grid__cell--width-30" ]
                    [ div [ class "c-card c-card--accordion u-high" ]
                        [ input [ (type_ "checkbox"), (id "vt-accordion") ]
                            []
                        , label [ (class "c-card__item"), (for "vt-accordion") ]
                            [ text "Vertex Types" ]
                        , div [ class "c-card__item" ]
                            [ Html.map VertexTypeGridMsg (VertexTypeGrid.view model.entities) ]
                        ]
                    ]
                , div [ class "o-grid__cell o-grid__cell--width-70" ]
                    [ text "Right Panel" ]
                ]
            ]
        ]



-- MAIN PROGRAM


init : Location -> ( AppModel, Cmd Msg )
init location =
    let
        currentRoute =
            Routes.parseLocation location
    in
        ( initialModel currentRoute, Cmd.map VertexTypeLoaderMsg VertexTypeLoader.fetchVertexTypes )


main : Program Never AppModel Msg
main =
    Navigation.program OnLocationChange
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }
