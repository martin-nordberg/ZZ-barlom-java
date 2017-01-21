module Barlom.Domain.Repository exposing (Entities, VertexType, constructVertexType, emptyRepository)

import Barlom.Infrastructure.Uuids exposing (Uuid)
import Dict exposing (Dict)


type alias Entities =
    { vertexTypesByUuid : Dict String VertexType
    , vertexTypesList : List VertexType
    }


type alias VertexType =
    { name : String
    , summary : String
    , uuid : String {- TODO: UUID -}
    }


constructVertexType : String -> String -> String -> VertexType
constructVertexType uuid name summary =
    { uuid = uuid
    , summary = summary
    , name = name
    }


emptyRepository =
    { vertexTypesByUuid = Dict.empty, vertexTypesList = [] }
