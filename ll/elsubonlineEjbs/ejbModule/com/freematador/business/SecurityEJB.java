package com.freematador.business;

import javax.ejb.Local;

import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;

@Local
public interface SecurityEJB {
	public void createUser(User u);
	public boolean loginUser(User loggedUser) throws BusinessException;
	public User getUserProfile(String email);
	public void activateUser(int userId);
	public void updateUserProfile(User user) throws BusinessException;
	public void recoverPassword(String email) throws BusinessException;
	boolean isAdmin(User user) throws BusinessException;
}
