package com.freematador.business;

import javax.ejb.Local;

import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;

@Local
public interface SecurityEJB {
	public void createUser(User u);
	public boolean loginUser(User loggedUser) throws BusinessException;
	public User getUserProfile(String email);
}
