
SET SEARCH_PATH = barlomgdbmetamodel;


----------------
-- VertexType --
----------------

-- Create / Update
DROP FUNCTION IF EXISTS UpsertVertexType( newName UUID, newName NAME, newSummary VARCHAR );
CREATE FUNCTION UpsertVertexType( newUuid UUID, newName NAME, newSummary VARCHAR )
   RETURNS BIGINT
AS $$
    INSERT INTO CHANGE ( type, elementUuid, dateTime, details )
                VALUES ( 'UpsertVertexType', newUuid, current_timestamp,
                         ('{ "uuid":"' || newUuid || '", "name":"' || newName || '", "summary":"' || newSummary || '"}')::jsonb );

    WITH Affected AS (
        INSERT INTO VertexType
                    ( uuid, name, summary )
             VALUES ( newUuid, newName, newSummary )
        ON CONFLICT ( uuid ) DO
             UPDATE
                SET name = EXCLUDED.name,
                    summary = EXCLUDED.summary
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
            ( uuid UUID, name NAME, summary VARCHAR );

-- Query by UUID
CREATE FUNCTION FindVertexTypeByName( queryName NAME )
	RETURNS SETOF VertexTypeRecord
AS $$
	SELECT uuid, name, summary
	  FROM VertexType
	 WHERE name = queryName;
$$
LANGUAGE 'sql';

-- Query by Name
CREATE FUNCTION FindVertexTypeByUuid( queryUuid UUID )
	RETURNS SETOF VertexTypeRecord
AS $$
	SELECT uuid, name, summary
	  FROM VertexType
	 WHERE uuid = queryUuid;
$$
LANGUAGE 'sql';

-- Query All
CREATE FUNCTION FindVertexTypesAll()
	RETURNS SETOF VertexTypeRecord
AS $$
	SELECT uuid, name, summary
	  FROM VertexType
	 ORDER BY name;
$$
LANGUAGE 'sql';



-- Sample Data for early testing
SELECT * FROM UpsertVertexType ( '2688a1dc-ab49-7fb6-7595-a28faba63c4f', 'VertexType1', 'The first vertex type' );
SELECT * FROM UpsertVertexType ( 'af576616-b593-4436-de54-f6f0ebbfbd30', 'VertexType2', 'The second vertex type' );
SELECT * FROM UpsertVertexType ( 'd199be0f-52ec-8a56-8ef2-a3abbe6cde65', 'VertexType3', 'Number three' );
SELECT * FROM UpsertVertexType ( '05562007-9be3-cf85-ab21-fc291f21e86c', 'VertexType4', 'The fourth vertex type' );
SELECT * FROM UpsertVertexType ( '506c214f-cee0-e946-7501-629ffa4b25e3', 'VertexType5', 'Another one' );
SELECT * FROM UpsertVertexType ( '463817bf-4194-8717-3ad7-cd75b00b4dec', 'VertexType6', 'The last' );

