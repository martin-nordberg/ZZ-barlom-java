
--SET SEARCH_PATH = barlomgdbmetamodel;


----------------
-- VertexType --
----------------

DROP FUNCTION IF EXISTS DeleteVertexType( uuid UUID );
CREATE FUNCTION DeleteVertexType( uuid UUID )
    RETURNS INTEGER
AS $$
    DELETE
      FROM VertexType
     WHERE uuid = uuid;

    SELECT 1;
$$
LANGUAGE 'sql';

DROP FUNCTION IF EXISTS FindVertexTypeByName( name NAME );
CREATE FUNCTION FindVertexTypeByName( name NAME )
	RETURNS SETOF RECORD
AS $$
	SELECT uuid, name
	  FROM VertexType
	 WHERE name = name;
$$
LANGUAGE 'sql';

DROP FUNCTION IF EXISTS FindVertexTypeByUuid( uuid UUID );
CREATE FUNCTION FindVertexTypeByUuid( uuid UUID )
	RETURNS SETOF RECORD
AS $$
	SELECT uuid, name
	  FROM VertexType
	 WHERE uuid = uuid;
$$
LANGUAGE 'sql';

DROP FUNCTION IF EXISTS FindVertexTypesAll();
CREATE FUNCTION FindVertexTypesAll()
	RETURNS SETOF RECORD
AS $$
	SELECT uuid, name
	  FROM VertexType;
$$
LANGUAGE 'sql';

DROP FUNCTION IF EXISTS UpsertVertexType( uuid UUID, name NAME );
CREATE FUNCTION UpsertVertexType( uuid UUID, name NAME )
   RETURNS INTEGER
AS $$
   INSERT INTO VertexType
               ( uuid, name )
        VALUES ( uuid, name )
   ON CONFLICT ( uuid ) DO
        UPDATE
           SET name = EXCLUDED.name;

   SELECT 1;
$$
LANGUAGE 'sql';
