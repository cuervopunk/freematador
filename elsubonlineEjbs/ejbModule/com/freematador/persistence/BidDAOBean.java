package com.freematador.persistence;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.freematador.domain.Bid;

/**
 * Session Bean implementation class BidDAOBean
 */
@Stateless
@LocalBean
public class BidDAOBean implements BidDAO {
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	EntityManager em;

    /**
     * Default constructor. 
     */
    public BidDAOBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public void insert(Bid entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando el Bid");
		}
	}

}
