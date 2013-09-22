package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.mailer.Mail;

@Local
public interface MailDAO {
	void insert(Mail entity) throws PersistenceException;
	List<Mail> getByStatus(boolean sent);
	void update(Mail entity) throws PersistenceException;
}
