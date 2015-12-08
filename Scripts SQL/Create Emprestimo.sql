-- Table: emprestimo

-- DROP TABLE emprestimo;

CREATE TABLE emprestimo
(
  id serial NOT NULL,
  cliente integer NOT NULL,
  livro integer NOT NULL,
  dataretirada date NOT NULL,
  dataprevista date NOT NULL,
  dataentrega date,
  CONSTRAINT emprestimo_pk PRIMARY KEY (id),
  CONSTRAINT emprestimo_cliente_fkey FOREIGN KEY (cliente)
      REFERENCES cliente (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT emprestimo_livro_fkey FOREIGN KEY (livro)
      REFERENCES livro (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_emprestimo_cliente FOREIGN KEY (cliente)
      REFERENCES cliente (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_emprestimo_livro FOREIGN KEY (livro)
      REFERENCES livro (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE emprestimo
  OWNER TO postgres;
