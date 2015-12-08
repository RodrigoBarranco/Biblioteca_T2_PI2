/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.jpa.entities.Livro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author RODRIGO
 */
@Stateless
@Path("com.jpa.entities.livro")
public class LivroFacadeREST extends AbstractFacade<Livro> {
    @PersistenceContext(unitName = "Biblioteca_T2PU")
    private EntityManager em;

    public LivroFacadeREST() {
        super(Livro.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Livro find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Livro> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
