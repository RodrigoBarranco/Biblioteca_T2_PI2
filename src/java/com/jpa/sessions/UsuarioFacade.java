/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.sessions;

import com.jpa.entities.Cliente;
import com.jpa.entities.Emprestimo;
import com.jpa.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author RODRIGO
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "Biblioteca_T2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
   
    public List<Usuario> findByLogin(Usuario u) {
        return getEntityManager().createNamedQuery("Usuario.findByLogin").setParameter("username", u.getUsername()).setParameter("password", u.getPassword()).getResultList();
    }
    
}
