module Barlom.Persistence.VertexTypeLoader exposing (Msg, fetchVertexTypes, update)

import Http
import Json.Decode as Decode exposing (field)
import Barlom.Domain.Repository as Repository exposing (Entities, VertexType, constructVertexType)
import Barlom.Infrastructure.Uuids exposing (fromString)


--------------
-- MESSAGES --
--------------
--
-- Message resulting from an AJAX retrieval of all vertex types.


type Msg
    = OnFetchAll (Result Http.Error (List VertexType))



--------------
-- COMMANDS --
--------------
--
-- Retrieves all vertex types by AJAX.


fetchVertexTypes : Cmd Msg
fetchVertexTypes =
    Http.get "/barlomgdbconsolecontent/vertextypes" dataDecoder
        |> Http.send OnFetchAll



-- Decodes the entire JSON payload, which has a single "data" field containing an array of vertex types.


dataDecoder : Decode.Decoder (List VertexType)
dataDecoder =
    Decode.field "data" vertexTypeListDecoder



-- Decodes a JSON array of vertex types.


vertexTypeListDecoder : Decode.Decoder (List VertexType)
vertexTypeListDecoder =
    Decode.list vertexTypeDecoder



-- Decodes a single vertex type.


vertexTypeDecoder : Decode.Decoder VertexType
vertexTypeDecoder =
    Decode.map2 constructVertexType
        (field "uuid" Decode.string)
        (field "name" Decode.string)



------------
-- UPDATE --
------------
--
-- Updates the list of entities from the loaded list.


update : Msg -> Entities -> ( Entities, Cmd Msg )
update message entities =
    case message of
        OnFetchAll (Ok newVertexTypes) ->
            ( { entities | vertexTypesList = newVertexTypes }, Cmd.none )

        OnFetchAll (Err error) ->
            ( entities, Cmd.none )
