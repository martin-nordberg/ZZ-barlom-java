
SET SEARCH_PATH = barlomgdbmetamodel;


----------------
-- VertexType --
----------------

-- Create / Update
DROP FUNCTION IF EXISTS UpsertVertexType( newName UUID, newName NAME );
CREATE FUNCTION UpsertVertexType( newUuid UUID, newName NAME )
   RETURNS BIGINT
AS $$
    INSERT INTO CHANGE ( type, elementUuid, dateTime, details )
                VALUES ( 'UpsertVertexType', newUuid, current_timestamp,
                         ('{ "uuid":"' || newUuid || '", "name":"' || newName || '"}')::jsonb );

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
    INSERT INTO CHANGE ( type, elementUuid, dateTime, details )
                VALUES ( 'DeleteVertexType', oldUuid, current_timestamp,
                         ('{ "uuid":"' || oldUuid || '"}')::jsonb );

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



-- Sample Data for early testing
SELECT * FROM UpsertVertexType ( '2688a1dc-ab49-7fb6-7595-a28faba63c4f', 'VertexType1' );
SELECT * FROM UpsertVertexType ( 'af576616-b593-4436-de54-f6f0ebbfbd30', 'VertexType2' );
SELECT * FROM UpsertVertexType ( 'd199be0f-52ec-8a56-8ef2-a3abbe6cde65', 'VertexType3' );
SELECT * FROM UpsertVertexType ( '05562007-9be3-cf85-ab21-fc291f21e86c', 'VertexType4' );
SELECT * FROM UpsertVertexType ( '506c214f-cee0-e946-7501-629ffa4b25e3', 'VertexType5' );
SELECT * FROM UpsertVertexType ( '463817bf-4194-8717-3ad7-cd75b00b4dec', 'VertexType6' );

