module Barlom.Presentation.Main.App exposing (..)

import Html exposing (Html, br, button, div, main_, program, text)
import Html.Attributes exposing (class)
import Html.Events exposing (onClick)
import Navigation exposing (Location)
import Counter
import Barlom.Presentation.Navigation.TopNavBar
import Barlom.Presentation.Routing.Routes as Routes exposing (Route)


-- MODEL


type alias AppModel =
    { currentRoute : Route
    , counterModel : Counter.Model
    }


initialModel : Route -> AppModel
initialModel route =
    { currentRoute = route
    , counterModel = Counter.initialModel
    }



-- MESSAGES


type Msg
    = CounterMsg Counter.Msg
    | OnLocationChange Location
    | TopNavBarMsg Barlom.Presentation.Navigation.TopNavBar.Msg



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

        OnLocationChange location ->
            let
                newRoute =
                    Routes.parseLocation location
            in
                ( { model | currentRoute = newRoute }, Cmd.none )

        TopNavBarMsg subMsg ->
            let
                ( updatedRoute, topNavCmd ) =
                    Barlom.Presentation.Navigation.TopNavBar.update subMsg model.currentRoute
            in
                ( { model | currentRoute = updatedRoute }, Cmd.map TopNavBarMsg topNavCmd )



-- SUBSCRIPTIONS


subscriptions : AppModel -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view : AppModel -> Html Msg
view model =
    main_ [ class "o-grid o-grid--no-gutter o-panel" ]
        [ div [ class "o-grid__cell--width-100 o-panel-container" ]
            [ Html.map TopNavBarMsg (Barlom.Presentation.Navigation.TopNavBar.view model.currentRoute)
            , div [ class "o-grid o-panel o-panel--nav-top" ]
                [ div [ class "o-grid__cell o-grid__cell--width-30" ]
                    [ text "Left Panel"
                    , br [] []
                    , -- Counter sample code
                      div []
                        [ Html.map CounterMsg (Counter.view model.counterModel)
                        ]
                    ]
                , div [ class "o-grid__cell o-grid__cell--width-70" ]
                    [ text "Right Panel"
                    ]
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
        ( initialModel currentRoute, Cmd.none )


main : Program Never AppModel Msg
main =
    Navigation.program OnLocationChange
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }
