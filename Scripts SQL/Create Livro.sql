-- Table: livro

-- DROP TABLE livro;

CREATE TABLE livro
(
  id serial NOT NULL,
  isbn character varying(13) NOT NULL,
  nome character varying(64) NOT NULL,
  autores character varying(512) NOT NULL,
  editora character varying(64) NOT NULL,
  ano integer NOT NULL,
  situacao integer,
  CONSTRAINT livro_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE livro
  OWNER TO postgres;
