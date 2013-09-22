package com.freematador.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.freematador.domain.Question;

/**
 * Session Bean implementation class QuestionDAOBean
 */
@Stateless
@LocalBean
public class QuestionDAOBean implements QuestionDAO {
	
	@PersistenceContext(unitName="ElSubOnlineUNIT" )
	private javax.persistence.EntityManager em;

    /**
     * Default constructor. 
     */
    public QuestionDAOBean() {
        // TODO Auto-generated constructor stub
    }

	
	@Override
	public void insert(Question entity) throws PersistenceException {
		try {
			em.persist(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error creando la Question");
		}
	}


	@Override
	public List<Question> findByRecipientUserId(int id) throws PersistenceException {
		try {
			String queryText = "select q from Question q where q.recipient.id = '"+id+"'";
			Query q = em.createQuery(queryText);

			return (List<Question>)q.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando la Question");
		}
	}


	@Override
	public List<Question> findByRecipientAnswer(int id, String answer)
			throws PersistenceException {
		try {
			String queryText = "select q from Question q where q.recipient.id = :id and q.answerText = :answer";
			Query q = em.createQuery(queryText);
			q.setParameter("id", id);
			q.setParameter("answer", answer);

			return (List<Question>)q.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando la Question");
		}
	}


	@Override
	public List<Question> findByAnswered(int id, boolean answered) {
		try {
			String queryText = "select q from Question q where q.recipient.id = :id and q.answered = :answered";
			Query q = em.createQuery(queryText);
			q.setParameter("id", id);
			q.setParameter("answered", answered);

			return (List<Question>)q.getResultList();
		} catch (Throwable ex) {
			System.out.println("-----------"+ex.getCause());
			throw new PersistenceException("Ha ocurrido un error buscando la Question");
		}
	}

	@Override
	public Question findById(int id) {
		try {
			return em.find(Question.class, id);
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando la Question");
		}
	}


	@Override
	public void update(Question entity) throws PersistenceException {
		try {
			em.merge(entity);
			em.flush();
		} catch (Throwable ex) {
			throw new PersistenceException("Ha ocurrido un error actualizando la Question");
		}
	}

	    
}
