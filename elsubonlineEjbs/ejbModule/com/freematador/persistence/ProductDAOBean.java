package com.freematador.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Product;

/**
 * Session Bean implementation class ProductDAOBean
 */
@Stateless
@LocalBean
public class ProductDAOBean implements ProductDAO {
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public ProductDAOBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findAll() throws PersistenceException {
		try {
			String queryText = "select p from Product p";
			Query q = em.createQuery(queryText);
			
			return q.getResultList();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error recuperando todos los Products");
		}
	}
    
	@Override
	public List<Product> getProductsByCategory(int categoryId) {
		try {
			System.out.println("ProductDAOBean - categoryId:"+categoryId);
			Query query = em.createQuery("select p from Product p where p.category.id = :id");
			query.setParameter("id", categoryId);
			return (List<Product>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error cargando los productos de una categoria");
		}
	}

	@Override
	public Product findById(Integer id) throws PersistenceException {
		try {
			return em.find(Product.class, id);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Product");
		}
	}    
    
	@Override
	public void insert(Product entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			System.out.println(ex.getCause());
			throw new PersistenceException("Ha ocurrido un error creando el Product");
		}
	}

	@Override
	public List<Product> findProductsBySaleTypeEndDate(int saleType, Date endDate) {
		try {
			System.out.println("ProductDAOBean - saleType:"+saleType);
			Query query = em.createQuery("select p from Product p" +
					" where p.saleType = :saleType " +
					" and p.endingDate < :endDate " +
					" and p.active=1");
			query.setParameter("saleType", saleType);
			query.setParameter("endDate", endDate);
			return (List<Product>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error cargando los productos de un tipo de venta");
		}
	}

	@Override
	public List<Product> findByDescription(String searchTerm) throws PersistenceException {
		try {
			searchTerm = "%" + searchTerm.toLowerCase() + "%";
			Query query = em.createQuery("select p from Product p" +
					" where lower(p.shortDescription) like :searchTermShort " +
					" or lower(p.longDescription) like :searchTermLong");
			query.setParameter("searchTermShort", searchTerm);
			query.setParameter("searchTermLong", searchTerm);
			return (List<Product>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando productos");
		}
	}

	@Override
	public void update(Product entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando el Product");
		}
	}

	@Override
	public List<Product> findHighlighted(boolean highlighted) {
		try {
			Query query = em.createQuery("select p from Product p" +
					" where p.highlight = :highlighted " +
					" and p.active=1");
			query.setParameter("highlighted", highlighted);
			return (List<Product>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando productos destacados");
		}
	}
	

	@Override
	public List<Product> findByUserStatus(int id, boolean status) throws PersistenceException {
		try {
			Query query = em.createQuery("select p from Product p where p.user.id = :id and p.active = :status");
			query.setParameter("id", id);
			query.setParameter("status", status);
			return (List<Product>) query.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error cargando los productos de una categoria");
		}
	}
}