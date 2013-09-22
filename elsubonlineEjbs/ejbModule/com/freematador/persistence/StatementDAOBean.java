package com.freematador.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Statement;
import com.freematador.domain.User;

/**
 * Session Bean implementation class StatementDAOBean
 */
@Stateless
@LocalBean
public class StatementDAOBean implements StatementDAO {

	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public StatementDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void insert(Statement entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			System.out.println("----------------------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error creando el Statement");
		}
	}

	@Override
	public List<Statement> findFromDueDate(int id, Date date)
			throws PersistenceException {
		try {
			Query query = em.createQuery("select s from Statement s " +
					" where s.due > :date " +
					" and s.user.id = :id " +
					" and isNull(s.payment) ");
			query.setParameter("date", date);
			query.setParameter("id", id);
			return (List<Statement>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Statement> findByUserNullPayment(Integer id) {
		try {
			Query query = em.createQuery("select s from Statement s " +
					" where s.user.id = :id " +
					" and s.payment IS NULL ");
			query.setParameter("id", id);
			return (List<Statement>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Statement> findByUser(Integer id) {
		try {
			Query query = em.createQuery("select s from Statement s " +
					" where s.user.id = :id " +
					" and s.payment IS NOT NULL ");
			query.setParameter("id", id);
			return (List<Statement>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public Statement findById(Integer statementId) {
		try {
			return em.find(Statement.class, statementId);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Statement");
		}
	}


	@Override
	public void update(Statement entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Statement");
		}
	}
	
	@Override
	public void truncate() throws PersistenceException {
		try {
			Query q1 = em.createNativeQuery("delete from Statement_Fee");
			Query q2 = em.createNativeQuery("delete from Statement");
			q1.executeUpdate();
			q2.executeUpdate();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error eliminando Statements");
		}
	}

}
