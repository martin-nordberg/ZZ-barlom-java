module Barlom.Domain.Repository exposing (Entities, VertexType, constructVertexType, emptyRepository)

import Barlom.Infrastructure.Uuids exposing (Uuid)
import Dict exposing (Dict)


type alias Entities =
    { vertexTypesByUuid : Dict String VertexType
    , vertexTypesList : List VertexType
    }


type alias VertexType =
    { name : String
    , uuid :
        String
        -- TODO: Uuid
    }


constructVertexType : String -> String -> VertexType
constructVertexType uuid name =
    { uuid = uuid
    , name = name
    }


emptyRepository =
    { vertexTypesByUuid = Dict.empty, vertexTypesList = [] }
