package com.freematador.persistence;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Operation;

@Local
public interface OperationDAO {
	List<Operation> findAll() throws PersistenceException;
	void insert(Operation entity) throws PersistenceException;
	List<Operation> findByBuyerId(Integer id);
	List<Operation> findBySellerId(Integer id);
	List<Operation> findBySuccessfulStatus(boolean b);
	List<Operation> findByFinishedStatus(int id, boolean b);
	Operation findById(int id);
	void update(Operation o);
	List<Operation> findByDateRange(Date start, Date finish);
	List<Operation> findByBuyerDateRange(Date start, Date end, int userId);
	List<Operation> findByUserDateRange(Date start, Date end, int userId);
	List<Operation> findBySellerDateRange(Date start, Date end, int userId);

}
