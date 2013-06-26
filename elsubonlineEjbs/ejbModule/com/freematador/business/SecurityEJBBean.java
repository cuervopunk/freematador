package com.freematador.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;
import com.freematador.persistence.UserDAO;

public @Stateless class SecurityEJBBean implements SecurityEJB {

	@EJB
	private UserDAO userDAO;
	
	public void createUser(User u) {
		userDAO.insert(u);
		System.out.println("Se ha creado el usuario "+u.getName());
	}
	
	public boolean loginUser(User loggedUser) throws BusinessException {
		boolean validated = false;
		
		if(loggedUser==null) {
			throw new BusinessException("User cannot be null");
		}
		
		User storedUser = userDAO.findByEmail(loggedUser.getEmail());
		
		if(loggedUser.getPassword().equals(storedUser.getPassword())) {
			validated=true;
			System.out.println("Valida password");
		}else{
			System.out.println("No valida password");
		}
		
		return validated;
	}

	@Override
	public User getUserProfile(String email) {
		User user = userDAO.findByEmail(email);
		return user;
	}
}
