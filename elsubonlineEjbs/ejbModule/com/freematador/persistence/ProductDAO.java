package com.freematador.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Product;

@Local
public interface ProductDAO {
	public void insert(Product entity) throws PersistenceException;
	public Product findById(Integer id) throws PersistenceException;
	public List<Product> getProductsByCategory(int categoryId);
	public List<Product> findProductsBySaleTypeEndDate(int saleType, Date endDate);
	public List<Product> findAll() throws PersistenceException;
	public void update(Product entity) throws PersistenceException;
	public List<Product> findByDescription(String searchTerm) throws PersistenceException;
	public List<Product> findHighlighted(boolean highlighted);
	public List<Product> findByUserStatus(int id, boolean status) throws PersistenceException;
}
