-- Table: cliente

-- DROP TABLE cliente;

CREATE TABLE cliente
(
  id serial NOT NULL,
  matricula character varying(9) NOT NULL,
  nome character varying(64) NOT NULL,
  telefone character varying(10) NOT NULL,
  CONSTRAINT cliente_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cliente
  OWNER TO postgres;
