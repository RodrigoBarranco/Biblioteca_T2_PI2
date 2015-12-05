/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.sessions;

import com.jpa.entities.Cliente;
import com.jpa.entities.Emprestimo;
import com.jpa.entities.Livro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author RODRIGO
 */
@Stateless
public class EmprestimoFacade extends AbstractFacade<Emprestimo> {
    @PersistenceContext(unitName = "Biblioteca_T2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmprestimoFacade() {
        super(Emprestimo.class);
    }
    
    public void devolver(Emprestimo entity, Livro l) {
        getEntityManager().merge(entity);
        getEntityManager().merge(l);
    }
    
    public void emprestar(Emprestimo e, Livro l) {
        getEntityManager().persist(e);
        getEntityManager().merge(l);
    }
    
    public List<Emprestimo> findAllDevolver(Emprestimo e) {
        return getEntityManager().createNamedQuery("Emprestimo.findByDataentregaNull").getResultList();
    }
    
    public List<Emprestimo> findAllByCliente(Cliente c) {
        return getEntityManager().createNamedQuery("Emprestimo.findByCliente").setParameter("codcliente", c).getResultList();
    }
}
