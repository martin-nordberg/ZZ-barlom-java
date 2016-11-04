
SET SEARCH_PATH = barlomgdbmetamodel;


----------------
-- VertexType --
----------------

-- Create / Update
DROP FUNCTION IF EXISTS UpsertVertexType( newName UUID, newName NAME );
CREATE FUNCTION UpsertVertexType( newUuid UUID, newName NAME )
   RETURNS BIGINT
AS $$
   WITH Affected AS (
       INSERT INTO VertexType
                   ( uuid, name )
            VALUES ( newUuid, newName )
       ON CONFLICT ( uuid ) DO
            UPDATE
               SET name = EXCLUDED.name
       RETURNING 1
   )
   SELECT COUNT(*) FROM Affected;
$$
LANGUAGE 'sql';

-- Delete
DROP FUNCTION IF EXISTS DeleteVertexType( oldUuid UUID );
CREATE FUNCTION DeleteVertexType( oldUuid UUID )
    RETURNS BIGINT
AS $$
    WITH Affected AS (
    	DELETE
      	  FROM VertexType
     	 WHERE uuid = oldUuid
        RETURNING 1
    )
    SELECT COUNT(*) FROM Affected;
$$
LANGUAGE 'sql';

-- Record Type
DROP TYPE IF EXISTS VertexTypeRecord CASCADE;
CREATE TYPE VertexTypeRecord AS
            ( uuid UUID, name NAME );

-- Query by UUID
CREATE FUNCTION FindVertexTypeByName( queryName NAME )
	RETURNS SETOF VertexTypeRecord
AS $$
	SELECT uuid, name
	  FROM VertexType
	 WHERE name = queryName;
$$
LANGUAGE 'sql';

-- Query by Name
CREATE FUNCTION FindVertexTypeByUuid( queryUuid UUID )
	RETURNS SETOF VertexTypeRecord
AS $$
	SELECT uuid, name
	  FROM VertexType
	 WHERE uuid = queryUuid;
$$
LANGUAGE 'sql';

-- Query All
CREATE FUNCTION FindVertexTypesAll()
	RETURNS SETOF VertexTypeRecord
AS $$
	SELECT uuid, name
	  FROM VertexType;
$$
LANGUAGE 'sql';

