SET SEARCH_PATH = "barlom-gdb-metamodel";

DROP TABLE "VertexType";

CREATE TABLE

CREATE OR REPLACE FUNCTION FindVertexTypesAll() 
	RETURNS SETOF "barlom-gdb-metamodel"."VertexType" 
AS $$
	SELECT * FROM "barlom-gdb-metamodel"."VertexType";
$$ 
LANGUAGE 'sql';


SELECT * FROM FindVertexTypesAll() ;
