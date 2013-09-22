package com.freematador.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Notification;

/**
 * Session Bean implementation class NotificationDAOBean
 */
@Stateless
@LocalBean
public class NotificationDAOBean implements NotificationDAO {

	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private javax.persistence.EntityManager em;

    /**
     * Default constructor. 
     */
    public NotificationDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Notification> findAll() throws PersistenceException {
		try {
			String queryText = "select n from Notification n";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando todos las Notifications");
		}
	}

	@Override
	public void insert(Notification entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando la Notification");
		}
	}
	
	@Override
	public void delete(Integer id) throws PersistenceException {
		try {
			Notification n = em.find(Notification.class, id);
			em.remove(n);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error removiendo la Notification");
		}
	}

	@Override
	public List<Notification> findByUserId(int id) throws PersistenceException {
		try {
			System.out.println("NotificationDAOBean - id:"+id);
			Query query = em.createQuery("select n from Notification n where n.owner.id = :id");
			query.setParameter("id", id);
			return (List<Notification>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error cargando las notificaciones de un usuario");
		}	
	}

	@Override
	public List<Notification> findByProductId(int id) throws PersistenceException {
		try {
			System.out.println("NotificationDAOBean - id:"+id);
			Query query = em.createQuery("select n from Notification n where n.product.id = :id");
			query.setParameter("id", id);
			return (List<Notification>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error cargando las notificaciones de un producto");
		}	
	}

}
