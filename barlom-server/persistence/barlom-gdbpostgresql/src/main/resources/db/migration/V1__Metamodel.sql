
SET SEARCH_PATH = barlomgdbmetamodel;


CREATE TYPE ChangeType AS ENUM (
    'UpsertVertexType',
    'DeleteVertexType'
);

CREATE TABLE Change
(
    id SERIAL NOT NULL,
    type ChangeType NOT NULL,
    elementUuid UUID NOT NULL,
    dateTime TIMESTAMPTZ NOT NULL,
    details JSONB NOT NULL,

    CONSTRAINT PK_Change PRIMARY KEY (id)
);

CREATE TABLE VertexType
(
    id SERIAL NOT NULL,
    uuid UUID NOT NULL,
    name NAME NOT NULL,
    summary VARCHAR(100),

    CONSTRAINT PK_VertexType PRIMARY KEY (id),
    CONSTRAINT UQ_VertexType_uuid UNIQUE (uuid),
    CONSTRAINT UQ_VertexType_name UNIQUE (name)
);



