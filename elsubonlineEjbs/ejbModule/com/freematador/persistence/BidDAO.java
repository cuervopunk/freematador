package com.freematador.persistence;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Bid;

@Local
public interface BidDAO {
	void insert(Bid entity) throws PersistenceException;

}
