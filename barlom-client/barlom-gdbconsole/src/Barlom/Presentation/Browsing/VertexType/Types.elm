module Barlom.Presentation.Browsing.VertexType.Types exposing (FocusedVertexType)

import Barlom.Domain.Repository exposing (VertexType)


-- TYPES --
-----------
--
-- Optional focused vertex type.


type alias FocusedVertexType =
    Maybe VertexType
