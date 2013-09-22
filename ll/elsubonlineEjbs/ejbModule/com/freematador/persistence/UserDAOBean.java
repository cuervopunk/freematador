package com.freematador.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.User;

/**
 * Session Bean implementation class UserDAOBean
 */
@Stateless
public class UserDAOBean implements UserDAO {
	
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private javax.persistence.EntityManager em;

	@Override
	public void delete(User User) throws PersistenceException {
		try {
			User u = em.find(User.class, User.getId());
			em.remove(u);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error removiendo el User");
		}
	}

	@Override
	public void delete(Integer id) throws PersistenceException {
		try {
			User u = em.find(User.class, id);
			em.remove(u);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error removiendo el User");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() throws PersistenceException {
		try {
			String queryText = "select u from User u";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando todos los Users");
		}
	}

	@Override
	public User findById(Integer id) throws PersistenceException {
		try {
			return em.find(User.class, id);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el User");
		}
	}

	@Override
	public User findByEmail(String email) throws PersistenceException {
		try {
			String queryText = "select u from User u where u.email = '"+email+"'";
			Query q = em.createQuery(queryText);

			return (User)q.getSingleResult();
			
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error buscando el User");
		}
	}
	
	@Override
	public void insert(User entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando el User");
		}
	}

	@Override
	public void update(User entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el User");
		}
	}
    
}
