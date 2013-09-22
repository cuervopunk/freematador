package com.freematador.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Operation;

/**
 * Session Bean implementation class OperationDAOBean
 */
@Stateless
@LocalBean
public class OperationDAOBean implements OperationDAO {
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public OperationDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Operation> findAll() throws PersistenceException {
		try {
			String queryText = "select o from Operation o";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error recuperando todas las Operations");
		}
	}

	@Override
	public void insert(Operation entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error creando la Operation");
		}
	}

	@Override
	public List<Operation> findByBuyerId(Integer id) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.buyer.id = :id ");
			query.setParameter("id", id);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Operation> findBySellerId(Integer id) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.seller.id = :id ");
			query.setParameter("id", id);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Operation> findBySuccessfulStatus(boolean b) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.successful = :successful ");
			query.setParameter("successful", b);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Operation> findByFinishedStatus(int id, boolean b) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.finished = :finished and o.seller.id = :userid");
			query.setParameter("finished", b);
			query.setParameter("userid", id);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public Operation findById(int id) {
		try {
			return em.find(Operation.class, id);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando la Operation");
		}
	}

	@Override
	public void update(Operation o) {
		try {
			em.merge(o);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando la Operation");
		}

	}

	@Override
	public List<Operation> findByDateRange(Date start, Date end) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.date between :start and :end ");
			query.setParameter("start", start);
			query.setParameter("end", end);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Operation> findByUserDateRange(Date start, Date end, int userId) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.date between :start and :end " +
					" and (o.buyer.id = :buyerid or o.seller.id = :sellerid)");
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.setParameter("buyerid", userId);
			query.setParameter("sellerid", userId);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}
	
	@Override
	public List<Operation> findBySellerDateRange(Date start, Date end, int userId) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.date between :start and :end " +
					" and o.seller.id = :id");
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.setParameter("id", userId);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}

	@Override
	public List<Operation> findByBuyerDateRange(Date start, Date end, int userId) {
		try {
			Query query = em.createQuery("select o from Operation o " +
					" where o.date between :start and :end " +
					" and o.buyer.id = :id");
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.setParameter("id", userId);
			return (List<Operation>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("--------------------------------------> " + ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando Operations");
		}
	}



}
