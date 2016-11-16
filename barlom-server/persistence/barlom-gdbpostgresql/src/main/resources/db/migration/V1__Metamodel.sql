

CREATE TABLE VertexType
(
    id SERIAL NOT NULL,
    uuid UUID NOT NULL,
    name NAME NOT NULL,

    CONSTRAINT PK_VertexType PRIMARY KEY (id),
    CONSTRAINT UQ_VertexType_uuid UNIQUE (uuid),
    CONSTRAINT UQ_VertexType_name UNIQUE (name)
);



INSERT INTO VertexType
            ( uuid, name )
     VALUES ( md5(random()::text || clock_timestamp()::text)::uuid, 'VertexType1' );

INSERT INTO VertexType
            ( uuid, name )
     VALUES ( md5(random()::text || clock_timestamp()::text)::uuid, 'VertexType2' );

INSERT INTO VertexType
            ( uuid, name )
     VALUES ( md5(random()::text || clock_timestamp()::text)::uuid, 'VertexType3' );

INSERT INTO VertexType
            ( uuid, name )
     VALUES ( md5(random()::text || clock_timestamp()::text)::uuid, 'VertexType4' );

INSERT INTO VertexType
            ( uuid, name )
     VALUES ( md5(random()::text || clock_timestamp()::text)::uuid, 'VertexType5' );

