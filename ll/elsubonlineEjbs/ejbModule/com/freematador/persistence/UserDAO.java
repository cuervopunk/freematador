package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.User;

@Local
public interface UserDAO {

	void delete(User User) throws PersistenceException;

	void delete(Integer id) throws PersistenceException;

	List<User> findAll() throws PersistenceException;

	void insert(User entity) throws PersistenceException;

	User findById(Integer id) throws PersistenceException;

	void update(User entity) throws PersistenceException;

	User findByEmail(String email) throws PersistenceException;

}
