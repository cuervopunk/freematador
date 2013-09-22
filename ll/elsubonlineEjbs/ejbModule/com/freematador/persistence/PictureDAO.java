package com.freematador.persistence;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Picture;

@Local
public interface PictureDAO {
	public void insert(Picture entity) throws PersistenceException;
	public void delete(Integer id) throws PersistenceException;
}
