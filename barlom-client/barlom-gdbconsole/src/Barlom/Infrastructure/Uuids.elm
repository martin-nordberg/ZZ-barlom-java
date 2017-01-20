-- Opaque wrapper for string-based UUIDs.
--
-- Cloned from https://github.com/danyx23/elm-uuid but without UUID generation.


module Barlom.Infrastructure.Uuids exposing (Uuid, fromString, isValidUuid, toString)

import Regex


-- UUID type. Represents a 128 bit UUUID.


type Uuid
    = Uuid String



-- Creates a string representation from a Uuid in the canonical 8-4-4-4-12 form, e.g.
-- "63B9AAA2-6AAF-473E-B37E-22EB66E66B76".


toString : Uuid -> String
toString (Uuid internalString) =
    internalString



-- Creates a Uuid from a String in the canonical form (e.g. "63B9AAA2-6AAF-473E-B37E-22EB66E66B76").
-- Note that this module only supports canonical Uuids, Versions 1-5 and will refuse to parse other Uuid variants.


fromString : String -> Maybe Uuid
fromString text =
    if isValidUuid text then
        Just <| Uuid <| String.toLower text
    else
        Nothing



-- Checks whether the given string is a valid UUID in the canonical representation
-- xxxxxxxx-xxxx-Axxx-Yxxx-xxxxxxxxxxxx where A is the version number between [1-5] and Y is in the range [8-B].


isValidUuid : String -> Bool
isValidUuid uuidAsString =
    Regex.contains uuidRegex uuidAsString



-- Regular expression for matching a UUID string.


uuidRegex : Regex.Regex
uuidRegex =
    Regex.regex "^[0-9A-Fa-f]{8,8}-[0-9A-Fa-f]{4,4}-[1-5][0-9A-Fa-f]{3,3}-[8-9A-Ba-b][0-9A-Fa-f]{3,3}-[0-9A-Fa-f]{12,12}$"
