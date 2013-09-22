package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Parameter;

@Local
public interface ParameterDAO {

	List<Parameter> findAll() throws PersistenceException;

	void update(Parameter entity) throws PersistenceException;

	Parameter findById(Integer id) throws PersistenceException;

	Parameter findByName(String name) throws PersistenceException;

}
