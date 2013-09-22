package com.freematador.persistence;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.freematador.domain.Payment;

/**
 * Session Bean implementation class PaymentDAOBean
 */
@Stateless
@LocalBean
public class PaymentDAOBean implements PaymentDAO {
	
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private javax.persistence.EntityManager em;

    /**
     * Default constructor. 
     */
    public PaymentDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void insert(Payment entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando el Payment");
		}
	}

    
}
