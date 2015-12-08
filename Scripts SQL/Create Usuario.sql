-- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id serial NOT NULL,
  username character varying(64) NOT NULL,
  password character varying(64) NOT NULL,
  admin integer,
  CONSTRAINT usuario_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO postgres;
