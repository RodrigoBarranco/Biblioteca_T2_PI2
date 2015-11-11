package com.jpa.entities;

import com.jpa.entities.Cliente;
import com.jpa.entities.Livro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-11T15:42:02")
@StaticMetamodel(Emprestimo.class)
public class Emprestimo_ { 

    public static volatile SingularAttribute<Emprestimo, Date> dataprevista;
    public static volatile SingularAttribute<Emprestimo, Cliente> cliente;
    public static volatile SingularAttribute<Emprestimo, Date> dataretirada;
    public static volatile SingularAttribute<Emprestimo, Livro> livro;
    public static volatile SingularAttribute<Emprestimo, Integer> id;
    public static volatile SingularAttribute<Emprestimo, Date> dataentrega;

}