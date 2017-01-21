module Barlom.Presentation.Main.App exposing (..)

import Html exposing (Html, br, button, div, input, label, main_, program, text)
import Html.Attributes exposing (checked, class, for, id, type_)
import Html.Events exposing (onClick)
import Navigation exposing (Location)
import Barlom.Presentation.Navigation.TopNavBar as TopNavBar
import Barlom.Presentation.Routing.Routes as Routes exposing (Route)
import Barlom.Domain.Repository as Repository exposing (Entities, VertexType)
import Barlom.Persistence.VertexTypeLoader as VertexTypeLoader
import Barlom.Presentation.Browsing.VertexType.Form as VertexTypeForm
import Barlom.Presentation.Browsing.VertexType.Grid as VertexTypeGrid
import Barlom.Presentation.Browsing.VertexType.Types exposing (FocusedVertexType)


-- MODEL


type alias UiState =
    { currentRoute : Route
    , focusedVertexType : FocusedVertexType
    }


type alias AppModel =
    { uiState : UiState
    , entities : Entities
    }


initialModel : Route -> AppModel
initialModel route =
    { uiState =
        { currentRoute = route
        , focusedVertexType = Nothing
        }
    , entities = Repository.emptyRepository
    }



-- MESSAGES


type Msg
    = OnLocationChange Location
    | TopNavBarMsg TopNavBar.Msg
    | VertexTypeFormMsg VertexTypeForm.Msg
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
                    ( newRoute, topNavCmd ) =
                        TopNavBar.update subMsg model.uiState.currentRoute
                in
                    ( { model | uiState = { oldUiState | currentRoute = newRoute } }, Cmd.map TopNavBarMsg topNavCmd )

            VertexTypeFormMsg subMsg ->
                ( model, Cmd.none )

            VertexTypeGridMsg subMsg ->
                let
                    ( newfocusedVertexType, vtGridCmd ) =
                        VertexTypeGrid.update subMsg
                in
                    ( { model | uiState = { oldUiState | focusedVertexType = newfocusedVertexType } }, Cmd.none )

            VertexTypeLoaderMsg subMsg ->
                let
                    ( newEntities, loadCmd ) =
                        VertexTypeLoader.update subMsg model.entities
                in
                    ( { model | entities = newEntities }, Cmd.map VertexTypeLoaderMsg loadCmd )



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
                        [ input [ (type_ "checkbox"), (id "vt-accordion"), (checked True {- TODO: make variable in UI state -}) ]
                            []
                        , label [ (class "c-card__item"), (for "vt-accordion") ]
                            [ text "Vertex Types" ]
                        , div [ class "c-card__item" ]
                            [ Html.map VertexTypeGridMsg (VertexTypeGrid.view model.entities model.uiState.focusedVertexType) ]
                        ]
                    ]
                , div [ class "o-grid__cell o-grid__cell--width-70" ]
                    [ Html.map VertexTypeFormMsg (VertexTypeForm.view model.uiState.focusedVertexType) ]
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
