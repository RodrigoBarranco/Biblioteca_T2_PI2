/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RODRIGO
 */
@Entity
@Table(name = "emprestimo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emprestimo.findAll", query = "SELECT e FROM Emprestimo e"),
    @NamedQuery(name = "Emprestimo.findById", query = "SELECT e FROM Emprestimo e WHERE e.id = :id"),
    @NamedQuery(name = "Emprestimo.findByDataretirada", query = "SELECT e FROM Emprestimo e WHERE e.dataretirada = :dataretirada"),
    @NamedQuery(name = "Emprestimo.findByDataprevista", query = "SELECT e FROM Emprestimo e WHERE e.dataprevista = :dataprevista"),
    @NamedQuery(name = "Emprestimo.findByDataentrega", query = "SELECT e FROM Emprestimo e WHERE e.dataentrega = :dataentrega"),
    @NamedQuery(name = "Emprestimo.findByDataentregaNull", query = "SELECT e FROM Emprestimo e WHERE e.dataentrega IS NULL"),
    @NamedQuery(name = "Emprestimo.findByCliente", query = "SELECT e FROM Emprestimo e INNER JOIN Livro l ON l.Id = e.livro WHERE l.situacao = 1 AND e.cliente = :codcliente AND e.dataentrega IS NULL")
})

public class Emprestimo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataretirada")
    @Temporal(TemporalType.DATE)
    private Date dataretirada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataprevista")
    @Temporal(TemporalType.DATE)
    private Date dataprevista;
    @Column(name = "dataentrega")
    @Temporal(TemporalType.DATE)
    private Date dataentrega;
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "livro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Livro livro;

    public Emprestimo() {
    }

    public Emprestimo(Integer id) {
        this.id = id;
    }

    public Emprestimo(Integer id, Date dataretirada, Date dataprevista) {
        this.id = id;
        this.dataretirada = dataretirada;
        this.dataprevista = dataprevista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataretirada() {
        return dataretirada;
    }

    public void setDataretirada(Date dataretirada) {
        this.dataretirada = dataretirada;
    }

    public Date getDataprevista() {
        return dataprevista;
    }

    public void setDataprevista(Date dataprevista) {
        this.dataprevista = dataprevista;
    }

    public Date getDataentrega() {
        return dataentrega;
    }

    public void setDataentrega(Date dataentrega) {
        this.dataentrega = dataentrega;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emprestimo)) {
            return false;
        }
        Emprestimo other = (Emprestimo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jpa.entities.Emprestimo[ id=" + id + " ]";
    }
    
    public Emprestimo getClone() { 
        try 
        { 
            return (Emprestimo) super.clone();         
        } 
        catch (CloneNotSupportedException e) 
        { 
            System.out.println (" Cloning not allowed. " ); 
            return this; 
        } 
    }


    
}
