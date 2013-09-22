package com.freematador.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.freematador.domain.Product;
import com.freematador.domain.Question;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;
import com.freematador.persistence.ProductDAO;
import com.freematador.persistence.QuestionDAO;

/**
 * Session Bean implementation class SocialEJBBean
 */
@Stateless
@LocalBean
public class SocialEJBBean implements SocialEJB {
	@EJB
	private QuestionDAO questionDAO;
	@EJB
	private ProductDAO productDAO;
    /**
     * Default constructor. 
     */
    public SocialEJBBean() {
    }

	public void askQuestion(Product product, Question question, User user) throws BusinessException {
		question.setProduct(product);
		question.setRecipient(product.getUser());
		question.setSender(user);
		questionDAO.insert(question);
		Product refreshedProduct = productDAO.findById(product.getId());
		refreshedProduct.getQuestions().add(question);
		productDAO.update(refreshedProduct);
	}
	
	public List<Question> getPendingQuestions(User user) {
		boolean answered = false;
		return questionDAO.findByAnswered(user.getId(), answered);
	}
	
	public List<Question> getUserQuestions(User user) {
		return questionDAO.findByRecipientUserId(user.getId());
	}
	
	public void saveAnswers(List<Question> questions) {
		for(Question q : questions) {
			Question aQuestion = questionDAO.findById(q.getId());
			aQuestion.setAnswered(true);
			aQuestion.setAnswerText(q.getAnswerText());
			aQuestion.setProduct(q.getProduct());
			aQuestion.setQuestionText(q.getQuestionText());
			aQuestion.setRecipient(q.getRecipient());
			aQuestion.setSender(q.getSender());
			questionDAO.update(aQuestion);
		}
	}

}
