package com.jpa.entities;

import com.jpa.entities.Emprestimo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-03T23:28:12")
@StaticMetamodel(Livro.class)
public class Livro_ { 

    public static volatile SingularAttribute<Livro, Integer> situacao;
    public static volatile SingularAttribute<Livro, Integer> ano;
    public static volatile ListAttribute<Livro, Emprestimo> emprestimoList;
    public static volatile SingularAttribute<Livro, String> isbn;
    public static volatile SingularAttribute<Livro, String> nome;
    public static volatile SingularAttribute<Livro, Integer> id;
    public static volatile SingularAttribute<Livro, String> autores;
    public static volatile SingularAttribute<Livro, String> editora;

}