package com.freematador.business;

import java.util.List;

import javax.ejb.Local;

import com.freematador.domain.Product;
import com.freematador.domain.Question;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;

@Local
public interface SocialEJB {
	public void askQuestion(Product product, Question question, User user) throws BusinessException;
	public List<Question> getUserQuestions(User user);
	public List<Question> getPendingQuestions(User user);
	public void saveAnswers(List<Question> questions);
}
