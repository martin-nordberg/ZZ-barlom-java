

CREATE TABLE VertexType
(
    id SERIAL NOT NULL,
    uuid UUID NOT NULL,
    name NAME NOT NULL,

    CONSTRAINT PK_VertexType PRIMARY KEY (id),
    CONSTRAINT UQ_VertexType_uuid UNIQUE (uuid),
    CONSTRAINT UQ_VertexType_name UNIQUE (name)
);


