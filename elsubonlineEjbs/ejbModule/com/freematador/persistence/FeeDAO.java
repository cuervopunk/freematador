package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Fee;

@Local
public interface FeeDAO {
	List<Fee> findAll() throws PersistenceException;

	void insert(Fee entity) throws PersistenceException;

}
