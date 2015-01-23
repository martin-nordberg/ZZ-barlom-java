--
-- (C) Copyright 2015 Martin E. Nordberg III
-- Apache 2.0 License
--

----------------------------------------------------------------
-- Simple genealogy graph model for metamodel loading testing --
----------------------------------------------------------------

-- Package "test1"
INSERT INTO GRESTLER_PACKAGE
            ( ID, PARENT_PACKAGE_ID, NAME )
     VALUES ( 'e4c4a700-a294-11e4-b20d-08002751500b', '00000000-7a26-11e4-a545-08002741a702', 'test1' );


-- Attribute type "Person Name"
INSERT INTO GRESTLER_ATTRIBUTE_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME )
     VALUES ( 'e4c4a704-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b', 'Person Name' );
INSERT INTO GRESTLER_STRING_ATTRIBUTE_TYPE
            ( ID, MAX_LENGTH, REGEX_PATTERN )
     VALUES ( 'e4c4a704-a294-11e4-b20d-08002751500b', 40, NULL );

-- Attribute type "Birth Date"
INSERT INTO GRESTLER_ATTRIBUTE_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME )
     VALUES ( 'e4c4a707-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b', 'Birth Date' );
INSERT INTO GRESTLER_DATETIME_ATTRIBUTE_TYPE
            ( ID, MIN_VALUE, MAX_VALUE )
     VALUES ( 'e4c4a707-a294-11e4-b20d-08002751500b',
              PARSEDATETIME('01-01-1600 00:00', 'dd-MM-yyyy hh:mm'),
              PARSEDATETIME('12-31-2015 00:00', 'dd-MM-yyyy hh:mm')  );



-- Vertex type "Person"
INSERT INTO GRESTLER_VERTEX_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME, SUPER_TYPE_ID )
     VALUES ( 'e4c4a701-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b',
              'Person', '00000010-7a26-11e4-a545-08002741a702' );

INSERT INTO GRESTLER_VERTEX_ATTRIBUTE_DECL
            ( ID, PARENT_VERTEX_TYPE_ID, NAME, ATTRIBUTE_TYPE_ID, IS_REQUIRED )
     VALUES ( 'e4c4a705-a294-11e4-b20d-08002751500b', 'e4c4a701-a294-11e4-b20d-08002751500b',
              'Given Name', 'e4c4a704-a294-11e4-b20d-08002751500b', FALSE );
INSERT INTO GRESTLER_VERTEX_ATTRIBUTE_DECL
            ( ID, PARENT_VERTEX_TYPE_ID, NAME, ATTRIBUTE_TYPE_ID, IS_REQUIRED )
     VALUES ( 'e4c4a706-a294-11e4-b20d-08002751500b', 'e4c4a701-a294-11e4-b20d-08002751500b',
              'Surname', 'e4c4a704-a294-11e4-b20d-08002751500b', TRUE );


-- Edge Type "Has Father"
INSERT INTO GRESTLER_EDGE_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME, SUPER_TYPE_ID, TAIL_VERTEX_TYPE_ID, HEAD_VERTEX_TYPE_ID )
     VALUES ( 'e4c4a702-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b',
              'Has Father', '00000020-7a26-11e4-a545-08002741a702',
              'e4c4a701-a294-11e4-b20d-08002751500b', 'e4c4a701-a294-11e4-b20d-08002751500b' );


-- Edge Type "Has Mother"
INSERT INTO GRESTLER_EDGE_TYPE
            ( ID, PARENT_PACKAGE_ID, NAME, SUPER_TYPE_ID, TAIL_VERTEX_TYPE_ID, HEAD_VERTEX_TYPE_ID )
     VALUES ( 'e4c4a703-a294-11e4-b20d-08002751500b', 'e4c4a700-a294-11e4-b20d-08002751500b',
              'Has Mother', '00000020-7a26-11e4-a545-08002741a702',
              'e4c4a701-a294-11e4-b20d-08002751500b', 'e4c4a701-a294-11e4-b20d-08002751500b' );
