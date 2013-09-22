package com.freematador.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Fee;

/**
 * Session Bean implementation class FeeDAOBean
 */
@Stateless
@LocalBean
public class FeeDAOBean implements FeeDAO {
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	EntityManager em;

    /**
     * Default constructor. 
     */
    public FeeDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Fee> findAll() throws PersistenceException {
		try {
			String queryText = "select f from Fee f";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando todos los Fees");
		}
	}

	@Override
	public void insert(Fee entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando el Fee");
		}
	}

}
