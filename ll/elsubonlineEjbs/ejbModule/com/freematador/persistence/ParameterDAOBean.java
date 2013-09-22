package com.freematador.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Parameter;
import com.freematador.domain.User;

/**
 * Session Bean implementation class ParameterDAOBean
 */
@Stateless
@LocalBean
public class ParameterDAOBean implements ParameterDAO {

	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private javax.persistence.EntityManager em;

    /**
     * Default constructor. 
     */
    public ParameterDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Parameter> findAll() throws PersistenceException {
		try {
			String queryText = "select p from Parameter p";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando todos los Parameters");
		}
	}

	@Override
	public Parameter findById(Integer id) throws PersistenceException {
		try {
			return em.find(Parameter.class, id);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Parameter");
		}
	}
	
	@Override
	public void update(Parameter entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Parameter");
		}
	}

	@Override
	public Parameter findByName(String name) throws PersistenceException {
		try {
			String queryText = "select p from Parameter p where p.name = '"+name+"'";
			Query q = em.createQuery(queryText);

			return (Parameter)q.getSingleResult();
			
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error buscando el Parameter");
		}
	}

}
