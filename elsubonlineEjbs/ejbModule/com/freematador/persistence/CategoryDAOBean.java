package com.freematador.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Category;

/**
 * Session Bean implementation class CategoryDAOBean
 */
@Stateless
@LocalBean
public class CategoryDAOBean implements CategoryDAO {
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public CategoryDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Category findById(int id) throws PersistenceException {
		try {
			return em.find(Category.class, id);
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando la categoria");
		}
	}

	@Override
	public List<Category> findByNodeHeight(Integer nodeHeight)
			throws PersistenceException {
		try {
			String queryText = "select c from Category c where c.nodeHeight = "+nodeHeight;
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando categorias");
		}
	}

	@Override
	public void insert(Category entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando el Category");
		}
	}
	
	@Override
	public void update(Category entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Category");
		}
	}

	@Override
	public void delete(Category category) throws PersistenceException {
		try {
			Category c = em.find(Category.class, category.getId());
			em.remove(c);
		} catch (Throwable ex) {
			System.out.println("------------------------------------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error removiendo el Category");
		}
	}

	
}
