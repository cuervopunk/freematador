package com.freematador.persistence;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Payment;

@Local
public interface PaymentDAO {
	void insert(Payment entity) throws PersistenceException;

}
