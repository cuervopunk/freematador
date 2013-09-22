package com.freematador.persistence;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import com.freematador.domain.Question;

@Local
public interface QuestionDAO {
	public void insert(Question entity) throws PersistenceException;
	public List<Question> findByRecipientUserId(int id) throws PersistenceException;
	public List<Question> findByRecipientAnswer(int id, String answer) throws PersistenceException;
	public List<Question> findByAnswered(int id, boolean answered);
	public Question findById(int id);
	public void update(Question entity) throws PersistenceException;

}
