--
-- (C) Copyright 2015 Martin E. Nordberg III
-- Apache 2.0 License
--

--------------------------------------
-- Simple graph model for genealogy --
--------------------------------------

-- Package test1
INSERT INTO GRESTLER_PACKAGE
            ( ID, PARENT_PACKAGE_ID, NAME )
     VALUES ( 'e4c4a700-a294-11e4-b20d-08002751500b', '00000000-7a26-11e4-a545-08002741a702', 'test1' );


-- Vertex type Person
INSERT INTO GRESTLER_VERTEX_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME, SUPER_TYPE_ID )
     VALUES ( 'e4c4a701-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b',
              'Person', '00000010-7a26-11e4-a545-08002741a702' );


-- Edge Type has-father
INSERT INTO GRESTLER_EDGE_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME, SUPER_TYPE_ID, TAIL_VERTEX_TYPE_ID, HEAD_VERTEX_TYPE_ID )
     VALUES ( 'e4c4a702-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b',
              'has-father', '00000020-7a26-11e4-a545-08002741a702',
              'e4c4a701-a294-11e4-b20d-08002751500b', 'e4c4a701-a294-11e4-b20d-08002751500b' );

-- Edge Type has-mother
INSERT INTO GRESTLER_EDGE_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME, SUPER_TYPE_ID, TAIL_VERTEX_TYPE_ID, HEAD_VERTEX_TYPE_ID )
     VALUES ( 'e4c4a703-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b',
              'has-mother', '00000020-7a26-11e4-a545-08002741a702',
              'e4c4a701-a294-11e4-b20d-08002751500b', 'e4c4a701-a294-11e4-b20d-08002751500b' );
