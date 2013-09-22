package com.freematador.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.freematador.domain.Role;
import com.freematador.domain.User;
import com.freematador.exceptions.BusinessException;
import com.freematador.persistence.UserDAO;
import com.freematador.utils.RandomString;

public @Stateless class SecurityEJBBean implements SecurityEJB {

	@EJB
	private UserDAO userDAO;
	@EJB
	private MailerEJB mailerEJB;

	
	public void createUser(User u) {
		userDAO.insert(u);
		mailerEJB.mailNewUser(u);
		System.out.println("Se ha creado el usuario "+u.getName());
	}
	
	public boolean loginUser(User loggedUser) throws BusinessException {
		boolean validated = false;
		
		if(loggedUser==null) {
			throw new BusinessException("User cannot be null");
		}
		
		User storedUser = userDAO.findByEmail(loggedUser.getEmail());
		
		if(loggedUser.getPassword().equals(storedUser.getPassword()) && storedUser.isActive()) {
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

	@Override
	public void activateUser(int userId) {
		User user = userDAO.findById(userId);
		user.setActive(true);
		userDAO.update(user);
	}

	@Override
	public void updateUserProfile(User user) throws BusinessException {
		User existingUser = userDAO.findById(user.getId());
		existingUser.setEmail(user.getEmail());
		existingUser.setName(user.getName());
		existingUser.setPassword(user.getPassword());
		existingUser.setSurname(user.getSurname());
		existingUser.setTelephone(user.getTelephone());
		userDAO.update(existingUser);
	}

	@Override
	public void recoverPassword(String email) throws BusinessException {
		User user = userDAO.findByEmail(email);
		RandomString randomStringGenerator = new RandomString(10);
		String randomPassword = randomStringGenerator.nextString();
		user.setPassword(randomPassword);
		userDAO.update(user);
		mailerEJB.mailRecoveryUser(user, randomPassword);
	}

	@Override
	public boolean isAdmin(User user) throws BusinessException {
		User refreshedUser = userDAO.findById(user.getId());
		List<Role> userRoles = refreshedUser.getRoles();
		boolean adminRoleFound = false;
		for(int i = 0; i<userRoles.size() && !adminRoleFound; i++) {
			Role aRole = (Role)userRoles.get(i);
			if(aRole.isAdmin()) {
				adminRoleFound = true;
			}
		}
		return adminRoleFound;
	}
}
