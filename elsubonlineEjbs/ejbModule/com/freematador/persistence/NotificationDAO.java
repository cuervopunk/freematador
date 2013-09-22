package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Notification;

@Local
public interface NotificationDAO {
	public List<Notification> findAll() throws PersistenceException;
	public void insert(Notification entity) throws PersistenceException;
	public void delete(Integer id) throws PersistenceException;
	public List<Notification> findByUserId(int id) throws PersistenceException;
	public List<Notification> findByProductId(int id) throws PersistenceException;
	
}
