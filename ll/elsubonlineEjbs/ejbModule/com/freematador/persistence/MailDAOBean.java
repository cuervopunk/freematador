package com.freematador.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.mailer.Mail;

/**
 * Session Bean implementation class MailDAOBean
 */
@Stateless
@LocalBean
public class MailDAOBean implements MailDAO {
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public MailDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void insert(Mail entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando el Mail");
		}
	}

	@Override
	public List<Mail> getByStatus(boolean sent) {
		try {
			String queryText = "select m from Mail m where m.sent=false";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando todos los Mails sin enviar");
		}
	}

	@Override
	public void update(Mail entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Mail");
		}
	}

}
