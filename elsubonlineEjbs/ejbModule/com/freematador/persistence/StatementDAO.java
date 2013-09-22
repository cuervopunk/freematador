package com.freematador.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Statement;

@Local
public interface StatementDAO {

	void insert(Statement theStatement) throws PersistenceException;

	public List<Statement> findFromDueDate(int id, Date date) throws PersistenceException;

	List<Statement> findByUserNullPayment(Integer id);

	List<Statement> findByUser(Integer id);

	Statement findById(Integer statementId);

	void update(Statement entity) throws PersistenceException;
	
	void truncate() throws PersistenceException;

}

