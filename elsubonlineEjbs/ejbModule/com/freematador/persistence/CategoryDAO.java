package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Category;

@Local
public interface CategoryDAO {
	public Category findById(int id) throws PersistenceException;
	public List<Category> findByNodeHeight(Integer nodeHeight) throws PersistenceException;
	void insert(Category entity) throws PersistenceException;
	void update(Category entity) throws PersistenceException;
	void delete(Category category) throws PersistenceException;

}
